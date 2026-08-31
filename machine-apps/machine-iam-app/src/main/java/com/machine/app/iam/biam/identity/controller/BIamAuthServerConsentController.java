package com.machine.app.iam.biam.identity.controller;

import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import com.machine.starter.security.config.SecurityConstant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 授权服务器授权确认页（AUTHORIZATION_CODE 授权码模式）
 * <p>
 * 资源拥有者登录后，授权服务器将携带 scope/client_id/state 重定向到此页，
 * 用户勾选同意后表单提交到授权端点完成授权码签发。
 */
@Controller
public class BIamAuthServerConsentController {

    private final RegisteredClientRepository registeredClientRepository;

    private final OAuth2AuthorizationConsentService authorizationConsentService;

    public BIamAuthServerConsentController(RegisteredClientRepository registeredClientRepository,
                                           OAuth2AuthorizationConsentService authorizationConsentService) {
        this.registeredClientRepository = registeredClientRepository;
        this.authorizationConsentService = authorizationConsentService;
    }

    @GetMapping(SecurityConstant.AUTH_SERVER_CONSENT_PAGE)
    public String consent(Principal principal, Model model,
            @RequestParam(OAuth2ParameterNames.CLIENT_ID) String clientId,
            @RequestParam(OAuth2ParameterNames.SCOPE) String scope,
            @RequestParam(OAuth2ParameterNames.STATE) String state) {

        // 移除已授权的 scope，区分待授权与已授权
        Set<String> scopesToApprove = new HashSet<>();
        Set<String> previouslyApprovedScopes = new HashSet<>();
        RegisteredClient registeredClient = this.registeredClientRepository.findByClientId(clientId);
        OAuth2AuthorizationConsent currentAuthorizationConsent = this.authorizationConsentService
                .findById(registeredClient.getId(), principal.getName());
        Set<String> authorizedScopes;
        if (currentAuthorizationConsent != null) {
            authorizedScopes = currentAuthorizationConsent.getScopes();
        } else {
            authorizedScopes = Collections.emptySet();
        }
        for (String requestedScope : StringUtils.delimitedListToStringArray(scope, " ")) {
            if (OidcScopes.OPENID.equals(requestedScope)) {
                continue;
            }
            if (authorizedScopes.contains(requestedScope)) {
                previouslyApprovedScopes.add(requestedScope);
            } else {
                scopesToApprove.add(requestedScope);
            }
        }

        model.addAttribute("clientId", clientId);
        model.addAttribute("state", state);
        model.addAttribute("scopes", withDescription(scopesToApprove));
        model.addAttribute("previouslyApprovedScopes", withDescription(previouslyApprovedScopes));
        model.addAttribute("principalName", principal.getName());
        return "iam/oauth2/consent";
    }

    private static Set<ScopeWithDescription> withDescription(Set<String> scopes) {
        Set<ScopeWithDescription> scopeWithDescriptions = new HashSet<>();
        for (String scope : scopes) {
            scopeWithDescriptions.add(new ScopeWithDescription(scope));
        }
        return scopeWithDescriptions;
    }

    public static class ScopeWithDescription {

        private static final String DEFAULT_DESCRIPTION = "该权限作用范围未知，请谨慎授权";

        private static final Map<String, String> scopeDescriptions = new HashMap<>();

        static {
            scopeDescriptions.put(OidcScopes.OPENID, "用于确认您的身份标识");
            scopeDescriptions.put(OidcScopes.PROFILE, "读取您的基本资料信息");
            scopeDescriptions.put("email", "读取您的邮箱地址");
            scopeDescriptions.put("address", "读取您的地址信息");
            scopeDescriptions.put("phone", "读取您的手机号码");
        }

        public final String scope;

        public final String description;

        ScopeWithDescription(String scope) {
            this.scope = scope;
            this.description = scopeDescriptions.getOrDefault(scope, DEFAULT_DESCRIPTION);
        }
    }

}
