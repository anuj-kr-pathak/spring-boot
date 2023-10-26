package com.anuj.test.springsecuritybasic.filter;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.StringUtils;

import javax.servlet.Filter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class RequestValidationBeforeFilter implements Filter {

    public static final String AUTHENTICATION_SCHEME_BASIC="Basic";
    private Charset credentialsCharSet = StandardCharsets.UTF_8;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String header = request.getHeader(AUTHORIZATION);

        if(header!=null){
            header = header.trim();
            if(StringUtils.startsWithIgnoreCase(header,AUTHENTICATION_SCHEME_BASIC)){
                byte [] base64Token = header.substring(6).getBytes(StandardCharsets.UTF_8);
                byte[] decoded ;
                try{

                    decoded = Base64.getDecoder().decode(base64Token);
                    String token  = new String(decoded,credentialsCharSet);
                    int delim = token.indexOf(":");
                    if(delim==-1){
                        throw new BadCredentialsException("Invalid basic authentication token");
                    }
                    String email = token.substring(0,delim);
                    if(email.toLowerCase().contains("test1")){
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        return;
                    }
                }catch (IllegalArgumentException e){
                    throw new BadCredentialsException("Failed to decode basic authentication token ");
                }
            }
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
