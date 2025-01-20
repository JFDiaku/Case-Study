package com.capstone.CaseStudy.controller;

import com.capstone.CaseStudy.database.dao.ActivityDAO;
import com.capstone.CaseStudy.database.dao.UserActivityDAO;
import com.capstone.CaseStudy.database.dao.UserDAO;
import com.capstone.CaseStudy.database.entity.Activity;
import com.capstone.CaseStudy.database.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
public class SearchController {

    @Autowired
    UserDAO userDAO;

    @Autowired
    UserActivityDAO userActivityDAO;

    @Autowired
    ActivityDAO activityDAO;

    @GetMapping("/search")
    public ModelAndView employees(@RequestParam(required = false) String query) {
        ModelAndView response = new ModelAndView();

        response.setViewName("search");

        Set<User> queriedUsers = new HashSet<>();
        Set<User> activityUsers = new HashSet<>();
        List<User> users ;
        Set<User> resultUsers = new HashSet<>();
        if(query == null || query.isEmpty()){
            users = Collections.emptyList();
        }else{
           String[] queryStrings = query.trim().split("\\s+");

           for(String word : queryStrings){

               Activity activity = activityDAO.findActivityByName(word);
               if(activity != null){
                   activityUsers.addAll(userActivityDAO.findUserByActivities(activity));
               }
               queriedUsers.addAll(userDAO.findUserByQueryString(word));
           }

            resultUsers.addAll(queriedUsers);
            resultUsers.addAll(activityUsers);

        }
        response.addObject("resultUsers", resultUsers);

        return response;
    }

}
