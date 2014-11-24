package com.memoapp;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    Filter corsFilter() {
        return new Filter() {
            public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
                                                        throws IOException, ServletException {
                HttpServletResponse response = (HttpServletResponse) res;
                response.setHeader("Access-Control-Allow-Origin", "*");
                response.setHeader("Access-Control-Allow-Methods",
                                    "POST,PUT,GET,DELETE,OPTIONS");
                response.setHeader("Access-Control-Max-Age", "3000");
                response.setHeader("Access-Control-Allow-Headers",
                                     "Origin,"
                                   + "Accept,"
                                   + "X-Requested-With,"
                                   + "Content-Type,"
                                   + "Access-Control-Request-Method,"
                                   + "Access-Control-Request-Headers");
                chain.doFilter(req, res);
            }

            public void init(FilterConfig filterConfig) {
            }

            public void destroy() {
            }
        };
    }
}
