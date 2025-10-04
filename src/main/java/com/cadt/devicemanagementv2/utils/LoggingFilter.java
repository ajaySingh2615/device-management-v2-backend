package com.cadt.devicemanagementv2.utils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LoggingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(LoggingFilter.class);
    private static final String REQ_ID = "requestId";

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
                                    FilterChain chain) throws ServletException, IOException {

        String rid = req.getHeader("X-Request-Id");
        if (rid == null || rid.isBlank()) rid = UUID.randomUUID().toString();
        MDC.put(REQ_ID, rid);
        long start = System.nanoTime();
        try {
            res.setHeader("X-Request-Id", rid);
            chain.doFilter(req, res);
        } finally {
            long tookMs = (System.nanoTime() - start) / 1_000_000;
            log.info("rid={} {} {}?{} -> {} {}ms UA='{}'",
                    rid,
                    req.getMethod(),
                    req.getRequestURI(),
                    req.getQueryString() == null ? "" : req.getQueryString(),
                    res.getStatus(),
                    tookMs,
                    req.getHeader("User-Agent"));
            MDC.remove(REQ_ID);
        }

    }
}
