package com.web.security;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import sun.misc.MessageUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@Getter
@Setter
public class CustomLoginFailureHandler implements AuthenticationFailureHandler {
    String loginId;
    String loginPw;
    String failureUrl;
    String errormsg;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        loginId= request.getParameter("username");
        loginPw= request.getParameter("password");
        failureUrl="/users/login_failed";
        //errormsg;

        if(exception instanceof BadCredentialsException) {
            errormsg = "비밀번호가 일치하지 않습니다.";
        } else if(exception instanceof InternalAuthenticationServiceException) {
            errormsg = "존재하지 않는 아이디입니다.";
        }else{
            errormsg="알 수 없는 에러입니다.";
        }

        request.setAttribute("username",loginId);
        request.setAttribute("password",loginPw);
        request.setAttribute("msg",errormsg);

        request.getRequestDispatcher(failureUrl).forward(request,response);

    }

}
