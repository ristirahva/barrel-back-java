package org.km.logging;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AppHandlerInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(AppHandlerInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("Запрос : {} {} с {}", request.getMethod(), request.getRequestURI(), request.getRemoteAddr());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("Ответ: {}", response.getStatus());
    }
}
