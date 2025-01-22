package com.capstone.CaseStudy.security;

import com.capstone.CaseStudy.database.dao.UserDAO;
import com.capstone.CaseStudy.database.dao.UserLogDAO;
import com.capstone.CaseStudy.database.entity.User;
import com.capstone.CaseStudy.database.entity.UserLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Slf4j
@Component
public class UserLogService {
    @Autowired
    UserLogDAO userLogDAO;

    @Autowired
    UserDAO userDAO;

    public void LogUserLogout(String username, LocalDateTime logoutTime){
        UserLog userLog = new UserLog();
        User user = userDAO.findByEmail(username);
        userLog.setUser(user);
        userLog.setLogoutTime(logoutTime);

        userLogDAO.save(userLog);
    }
}
