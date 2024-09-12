package com.kuyco.main_api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kuyco.main_api.common.ResponseWrapper;
import com.kuyco.main_api.constant.ErrorType;
import com.kuyco.main_api.constant.InternalStatusCode;
import com.kuyco.main_api.exception.UnauthorizedException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        // Return a custom error message and status code
        ResponseWrapper<Void> responseWrapper = new ResponseWrapper<>();
        responseWrapper.buildUnauthorizedException(new UnauthorizedException(authException.getMessage()));

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(responseWrapper));
    }
}
