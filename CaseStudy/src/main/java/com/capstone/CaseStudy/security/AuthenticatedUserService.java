package com.capstone.CaseStudy.security;


import com.capstone.CaseStudy.database.dao.UserDAO;
import com.capstone.CaseStudy.database.dao.UserLogDAO;
import com.capstone.CaseStudy.database.entity.User;
import com.capstone.CaseStudy.database.entity.UserLog;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collection;

@Slf4j
@Component
public class AuthenticatedUserService {

    @Autowired
    private UserDAO userDao;

    @Autowired
    private AuthenticationManager authenticationManager;

    // this function will return the username of the logged in user
    public String getCurrentUsername() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context != null && context.getAuthentication() != null) {
            if (context.getAuthentication() instanceof AnonymousAuthenticationToken) {
                // not logged in so return null
                return null;
            } else {
                final org.springframework.security.core.userdetails.User principal =
                        (org.springframework.security.core.userdetails.User) context.getAuthentication().getPrincipal();
                return principal.getUsername();
            }
        } else {
            return null;
        }
    }


    // This will be used by almost everyone in the project to get the user_id for the loged in user
    // you can simplty autowire in the authenticated user service and call this function simialr to DAO call
    // this function returns null if the user is not looged in and will return a User entity if they are
    public User loadCurrentUser() {
        String username = getCurrentUsername();
        if (username != null) {
            User user = userDao.findByEmail(username);
            if(user == null) {
                user = userDao.findByEmail(username.toLowerCase());
            }
            return user;
        }
        return null;
    }

    // this function will manually log a user in after creating a new user
    public void changeLoggedInUsername(HttpSession session, String username, String unencryptedPassword) {
        // reset security principal to be the new user information
        Authentication request = new UsernamePasswordAuthenticationToken(username, unencryptedPassword);
        Authentication result = authenticationManager.authenticate(request);
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(result);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, sc);
    }

    // you may not have a use for this in your project but it is nice to have in this class for feature
    // completeness .. maybe some day
    public boolean isUserInRole(String role) {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context != null && context.getAuthentication() != null) {
            Collection<? extends GrantedAuthority> authorities = context.getAuthentication().getAuthorities();
            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority().equals(role)) {
                    return true;
                }
            }
        }

        return false;
    }

    // kinda the same function as the tag libraries .. maybe there is no use for it
    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return false;
        }

        return (authentication != null && authentication.isAuthenticated());
    }


}
