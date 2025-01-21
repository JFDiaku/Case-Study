package com.capstone.CaseStudy.controller;

import com.capstone.CaseStudy.database.dao.MessageDAO;
import com.capstone.CaseStudy.database.dao.UserActivityDAO;
import com.capstone.CaseStudy.database.dao.UserDAO;
import com.capstone.CaseStudy.database.entity.Activity;
import com.capstone.CaseStudy.database.entity.User;
import com.capstone.CaseStudy.database.entity.UserActivity;
import com.capstone.CaseStudy.security.AuthenticatedUserService;
import io.github.cdimascio.dotenv.Dotenv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
public class IndexController {
    private static final Logger LOG = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    UserDAO userDAO;

    @Autowired
    MessageDAO messageDAO;

    @Autowired
    AuthenticatedUserService authenticatedUserService;

    @Autowired
    UserActivityDAO userActivityDAO;

    @GetMapping("/dashboard")
    public ModelAndView index() {
        ModelAndView response = new ModelAndView();
        response.setViewName("index");

        User loggedInUser = authenticatedUserService.loadCurrentUser();
        String apiKey = Dotenv.load().get("API_KEY");
//        find current users activities
        List<Activity> activities = userActivityDAO.findUserActivities(loggedInUser);
        Integer userCount = userDAO.findAll().size();

//       find users in the same city or state then combine them into a set
        List<User> stateUsers = userDAO.findUserByState(loggedInUser.getState()) ;
        List<User> cityUsers = userDAO.findUserByState(loggedInUser.getCity()) ;
        Set<User> suggestedUsers = new HashSet<>();
       suggestedUsers.addAll(stateUsers);
       suggestedUsers.addAll(cityUsers);
       suggestedUsers.remove(loggedInUser);

//      find 5 active chats to display on the dash
        Set<User> activeChats = messageDAO.findActiveChats(loggedInUser);
        activeChats.remove(loggedInUser);
        List<User> chats = new ArrayList<>(activeChats);
        if (chats.size() > 5) {
            chats.subList(5, chats.size()).clear();
        }
        activeChats = new HashSet<>(chats);

//      create a hashmap of a suggesterUser and a list of their activities
        Map<User, List<Activity>> userActivityMap = new HashMap<>();
        for (User user : suggestedUsers) {
          List<Activity> activityList = userActivityDAO.findUserActivities(user);
          userActivityMap.put(user, activityList);
        }
        response.addObject("apiKey", apiKey);
        response.addObject("activeChats", activeChats);
        response.addObject("suggestedUserActivities", userActivityMap);
        response.addObject("suggestedUsers", suggestedUsers);
        response.addObject("user", loggedInUser);
        response.addObject("userCount", userCount);
        response.addObject("activities", activities);
        return response;
    }


}
