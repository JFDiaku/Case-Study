package com.capstone.CaseStudy.config;

import com.capstone.CaseStudy.database.dao.UserDAO;
import com.capstone.CaseStudy.database.entity.User;
import com.capstone.CaseStudy.security.AuthenticatedUserService;
import com.capstone.CaseStudy.security.UserLogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    UserLogService userLogService;


    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        if (authentication != null && authentication.getName() != null) {
            // Log the logout time
            String username = authentication.getName();
            LocalDateTime logoutTime = LocalDateTime.now();

            // Save to the database
            userLogService.LogUserLogout(username, logoutTime);
        }
        // Redirect to login page or any other desired location
        response.sendRedirect("/login");
    }
}
