package com.ftrainer.ftrainer.security;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import java.io.IOException;

public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String redirectURL = determineRedirectURL(userDetails);

        response.sendRedirect(redirectURL);
    }

    private String determineRedirectURL(UserDetails userDetails) {
        if (userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ADMIN"))) {
            return "/admin";
        } else if (userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("CLIENT"))) {
            return "/client";
        }else if (userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("TRAINER"))) {
            return "/trainer";
        } else {
            return "/";
        }
    }
}
