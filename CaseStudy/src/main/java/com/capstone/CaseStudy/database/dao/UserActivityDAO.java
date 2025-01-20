package com.capstone.CaseStudy.database.dao;

import com.capstone.CaseStudy.database.entity.Activity;
import com.capstone.CaseStudy.database.entity.User;
import com.capstone.CaseStudy.database.entity.UserActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserActivityDAO extends JpaRepository<UserActivity, Long> {

    @Query("Select ua.activity from UserActivity ua where ua.user = :user ")
    List<Activity> findUserActivities(User user);


    @Query("Select ua.user from UserActivity ua where ua.activity = :activity ")
    List<User> findUserByActivities(Activity  activity);

    @Transactional
    @Modifying
    @Query("DELETE from UserActivity ua where ua.activity = :activity and ua.user = :user ")
    Integer removeUserActivity(Activity activity, User user);

    @Query("Select ua from UserActivity ua where ua.activity = :activity and ua.user = :user")
     UserActivity findUserActivity(Activity  activity, User user);

}
