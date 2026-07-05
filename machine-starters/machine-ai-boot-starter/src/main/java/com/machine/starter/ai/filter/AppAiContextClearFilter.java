
package com.machine.starter.ai.filter;

import cn.hutool.core.util.StrUtil;
import com.machine.sdk.base.context.AppAiContextHolder;
import com.machine.sdk.base.envm.ai.AiModelNameTypeEnum;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AppAiContextClearFilter extends GenericFilterBean {

    public AppAiContextClearFilter() {
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String model_type = httpRequest.getParameter("model_type");
            if (StrUtil.isNotBlank(model_type)) {
                AiModelNameTypeEnum modelType = AiModelNameTypeEnum.valueOf(model_type);
                AppAiContextHolder.getContext().setModelType(modelType);

            }
            chain.doFilter(request, response);
        } finally {
            AppAiContextHolder.getContext().clear();
        }
    }
}
