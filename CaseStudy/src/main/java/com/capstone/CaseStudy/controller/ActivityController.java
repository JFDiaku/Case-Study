package com.capstone.CaseStudy.controller;

import com.capstone.CaseStudy.database.dao.ActivityDAO;
import com.capstone.CaseStudy.database.dao.UserActivityDAO;
import com.capstone.CaseStudy.database.entity.Activity;
import com.capstone.CaseStudy.database.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ActivityController {

    private static final Logger LOG = LoggerFactory.getLogger(ActivityController.class);

    @Autowired
    ActivityDAO activityDAO;

    @Autowired
    UserActivityDAO userActivityDAO;

    @GetMapping("/activities")
    public ModelAndView activitiesPage() {
        ModelAndView response = new ModelAndView();
        Map<Activity, List<User>> userActivities = new HashMap<>();
        List<Activity> activities = activityDAO.findAllActivities();
        for(Activity activity: activities){
          List<User> users = userActivityDAO.findUserByActivities(activity);
          userActivities.put(activity, users);
        }
        Map.Entry<Activity, List<User>> firstEntry = userActivities.entrySet().iterator().next();

        response.addObject("activity", firstEntry.getKey());
        response.addObject("members", firstEntry.getValue());
        response.setViewName("activities");
        response.addObject("activities", userActivities);
        return response;
    }


    @GetMapping("/activities/{activityName}")
    public ResponseEntity<?> changeActivity(@PathVariable String activityName) {
        Activity activity = activityDAO.findActivityByName(activityName);
        List<User> members = userActivityDAO.findUserByActivities(activity);
        try {
            if(activity != null){
                LOG.debug("activity found");
            }
            return ResponseEntity.ok(
                    Map.of(
                            "activity",activity,
                            "members", members
                    )
            );
        } catch (Exception e) {

            return ResponseEntity.status(500).body("Error fetching activity: " + e.getMessage());
        }

    }
}
