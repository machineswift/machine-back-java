package com.machine.server.camunda.config;


import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.ext.Provider;
import org.springframework.http.MediaType;

@Provider
public class EncodingResponseFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        String path = requestContext.getUriInfo().getPath();
        if (path == null) {
            return;
        }

        if (path.endsWith("rendered-form")) {
            String contentType = responseContext.getHeaderString(HttpHeaders.CONTENT_TYPE);
            if (contentType != null && contentType.contains(MediaType.APPLICATION_XHTML_XML.toString())
                    && !contentType.toLowerCase().contains("charset")) {
                responseContext.getHeaders().putSingle(HttpHeaders.CONTENT_TYPE,
                        contentType + "; charset=UTF-8");
            }
        } else if (path.endsWith("error-details")) {
            String contentType = responseContext.getHeaderString(HttpHeaders.CONTENT_TYPE);
            if (contentType != null && contentType.contains(MediaType.TEXT_PLAIN.toString())
                    && !contentType.toLowerCase().contains("charset")) {
                responseContext.getHeaders().putSingle(HttpHeaders.CONTENT_TYPE,
                        contentType + "; charset=UTF-8");
            }
        }
    }
}