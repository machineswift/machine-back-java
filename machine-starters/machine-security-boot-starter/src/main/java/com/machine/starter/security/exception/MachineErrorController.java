package com.machine.starter.security.exception;

import cn.hutool.core.util.ClassUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
public class MachineErrorController extends BasicErrorController {

    public MachineErrorController(ServerProperties serverProperties) {
        super(new DefaultErrorAttributes(), serverProperties.getError());
    }

    @Override
    @SneakyThrows
    @RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) throws AuthenticationException {
        HttpStatus status = getStatus(request);
        if (status == HttpStatus.NO_CONTENT) {
            return new ResponseEntity<>(status);
        }
        Map<String, Object> body = getErrorAttributes(request, getErrorAttributeOptions(request, MediaType.ALL));

        String message = body.get("message").toString();
        String className = body.get("exception").toString();
        if (containAccessDeniedException(className)) {
            Class<?> clazz = Class.forName(className);
            Constructor<?> constructor = clazz.getConstructor(String.class);
            throw (AuthenticationException) constructor.newInstance(message);
        }

        if (containAuthenticationException(className)) {
            Class<?> clazz = Class.forName(className);
            Constructor<?> constructor = clazz.getConstructor(String.class);
            throw (AccessDeniedException) constructor.newInstance(message);
        }

        throw new Exception(body.get("message").toString());
    }

    private boolean containAccessDeniedException(String className) {
        if (accessDeniedExceptionSet.isEmpty()) {
            synchronized (MachineErrorController.class) {
                if (accessDeniedExceptionSet.isEmpty()) {
                    Class<?> superClass = AuthenticationException.class;
                    Set<Class<?>> subclasses = findSubClassesRecursively("org.springframework.security", superClass);
                    subclasses.addAll(findSubClassesRecursively("com.machine.starter.security.exception", superClass));
                    subclasses.addAll(findSubClassesRecursively("com.machine.sdk.common.exception.iam.authentication", superClass));
                    accessDeniedExceptionSet.addAll(subclasses.stream().map(Class::getName).toList());
                }
            }
        }
        return accessDeniedExceptionSet.contains(className);
    }

    private boolean containAuthenticationException(String className) {
        if (authenticationExceptionSet.isEmpty()) {
            synchronized (MachineErrorController.class) {
                if (authenticationExceptionSet.isEmpty()) {
                    Class<?> superClass = AccessDeniedException.class;
                    Set<Class<?>> subclasses = findSubClassesRecursively("org.springframework.security", superClass);
                    subclasses.addAll(findSubClassesRecursively("com.machine.starter.security.exception", superClass));
                    subclasses.addAll(findSubClassesRecursively("com.machine.sdk.common.exception.iam.access", superClass));
                    authenticationExceptionSet.addAll(subclasses.stream().map(Class::getName).toList());
                }
            }
        }
        return authenticationExceptionSet.contains(className);
    }

    private static Set<Class<?>> findSubClassesRecursively(String packageName,
                                                           Class<?> superClass) {
        Set<Class<?>> subclasses = new HashSet<>();
        Set<Class<?>> foundClasses = ClassUtil.scanPackageBySuper(packageName, superClass);
        for (Class<?> clazz : foundClasses) {
            subclasses.add(clazz);
            subclasses.addAll(findSubClassesRecursively(packageName, clazz));
        }
        return subclasses;
    }

    private static final Set<String> accessDeniedExceptionSet = new HashSet<>();
    private static final Set<String> authenticationExceptionSet = new HashSet<>();
}
