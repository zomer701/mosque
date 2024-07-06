package com.live.mosque.filter;

import com.live.mosque.crypto.CryptoUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.ErrorResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Order(1)
@Slf4j
public class RequestAuthFilter extends OncePerRequestFilter {

    @Value("${key}")
    private String authKey;

    @Resource
    private CryptoUtil cryptoUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorization  = cryptoUtil.decrypt(request.getHeader("authorization"));
        if(authKey.equals(authorization)) {
            filterChain.doFilter(request, response);
        }else{
            throw new RuntimeException("un authorization");
        }
    }
}
