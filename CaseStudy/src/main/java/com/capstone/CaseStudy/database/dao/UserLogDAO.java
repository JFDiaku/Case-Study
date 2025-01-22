package com.capstone.CaseStudy.database.dao;

import com.capstone.CaseStudy.database.entity.User;
import com.capstone.CaseStudy.database.entity.UserLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface UserLogDAO extends JpaRepository<UserLog, Long> {

    @Query(value = "select ul.logoutTime from UserLog ul where ul.logoutTime = (SELECT MAX(ul.logoutTime) FROM UserLog ul)")
    LocalDateTime findLastLogout(User user);
}
