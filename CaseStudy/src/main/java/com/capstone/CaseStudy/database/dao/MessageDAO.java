package com.capstone.CaseStudy.database.dao;

import com.capstone.CaseStudy.database.entity.Message;
import com.capstone.CaseStudy.database.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface MessageDAO extends JpaRepository<Message, Long> {
    @Query("Select distinct m.sender , m.receiver from Message m where m.sender = :user or m.receiver = :user ")
    Set<User> findActiveChats(User user);

    @Query("Select m from Message m where (m.receiver = :user or m.sender = :user) AND m.createdAt > :lastLogout")
    List<Message> findNewMessages(LocalDateTime lastLogout, User user);

    @Query(value = "SELECT m.message FROM message m WHERE (m.incoming_msg_id = :userId OR m.outgoing_msg_id = :userId) ORDER BY m.createdat DESC LIMIT 1", nativeQuery = true)
    String getLastMessage(Integer userId);


    @Query("Select m from Message m where (m.receiver = :user AND m.sender = :loggedInUser) OR (m.receiver = :loggedInUser AND m.sender = :user) order by m.createdAt asc")
    List<Message> findMessagesBetweenUsers(User loggedInUser, User user);

}
