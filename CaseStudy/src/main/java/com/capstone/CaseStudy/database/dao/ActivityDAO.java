package com.capstone.CaseStudy.database.dao;

import com.capstone.CaseStudy.database.entity.Activity;
import com.capstone.CaseStudy.database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActivityDAO extends JpaRepository<Activity, Long> {
    @Query("Select a from Activity a where LOWER(a.name) = :activityName ")
    Activity findActivityByName(String activityName);

    @Query("Select a from Activity a")
    List<Activity> findAllActivities();

    @Query("Select a from Activity a where a.id = :id")
    Activity findActivityById(Integer id);
}
