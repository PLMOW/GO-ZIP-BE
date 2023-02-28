package com.gozip.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gozip.dto.StateDto;
import com.gozip.exception.CustomException;
import com.gozip.exception.ErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request,response);
        } catch (CustomException e) {
            customExceptionHandler(response, e.getErrorCode());
        }
    }

    private void customExceptionHandler(HttpServletResponse response, ErrorCode errorCode) {
        log.info(errorCode.getMsg());
        response.setStatus(errorCode.getHttpStatus().value());
        response.setContentType("application/json; charset=UTF=8");
        response.setCharacterEncoding("UTF-8");
        try {
            String json = new ObjectMapper().writeValueAsString(new StateDto(errorCode.getMsg(), errorCode.getHttpStatus().value()));
            response.getWriter().write(json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
