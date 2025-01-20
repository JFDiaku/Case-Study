package com.capstone.CaseStudy.database.dao;

import com.capstone.CaseStudy.database.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.Set;

public interface MessageDAO extends JpaRepository<User, Long> {
    @Query("Select distinct m.sender , m.receiver from Message m where m.sender = :user or m.receiver = :user ")
    Set<User> findActiveChats(User user);




}
