package de.muenchen.oss.refarch.backend.configuration.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class RequestLoggingFilter extends OncePerRequestFilter {

    private static final Logger LOG = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        LOG.info(
                "BACKEND REQUEST: method={} uri={} query={}",
                request.getMethod(),
                request.getRequestURI(),
                request.getQueryString());

        LOG.info("=== HEADERS ===");

        Collections.list(request.getHeaderNames())
                .forEach(headerName -> LOG.info(
                        "{}: {}",
                        headerName,
                        request.getHeader(headerName)));

        LOG.info("=== COOKIES ===");

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                LOG.info(
                        "{}={}",
                        cookie.getName(),
                        cookie.getValue());
            }
        }

        filterChain.doFilter(request, response);
    }
}
