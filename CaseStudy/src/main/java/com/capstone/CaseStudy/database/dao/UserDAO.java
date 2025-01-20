package com.capstone.CaseStudy.database.dao;


import com.capstone.CaseStudy.database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface UserDAO extends JpaRepository<User, Long> {
    User findById(Integer id);

    @Query(value = "select u from User u where lower(u.email) = lower(:email)")
    User findByEmail(String email);


    @Query(value = "select * from user order by id asc;", nativeQuery = true)
    List<User> findAllUsers();

    @Query(value = "select u from User u where u.state = :state")
    List<User> findUserByState(String state);

    @Query(value = "select u from User u where lower(u.firstName) LIKE lower(concat( '%', :queryString , '%'))"
            + " OR lower(u.lastName) LIKE lower(concat( '%', :queryString , '%'))"
            + " OR lower(u.email)  LIKE lower(concat( '%', :queryString , '%'))"
            + " OR lower(u.description) LIKE lower(concat( '%', :queryString , '%'))"
            + " OR lower(u.city)  LIKE lower(concat( '%', :queryString , '%'))"
            + " OR lower(u.state)  LIKE lower(concat( '%', :queryString , '%'))")
    Set<User> findUserByQueryString(String queryString);

    @Query(value = "select u from User u where u.state = :city")
    List<User> findUserByCity(String city);




}
