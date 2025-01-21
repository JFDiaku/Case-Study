package com.capstone.CaseStudy.controller;

import com.capstone.CaseStudy.database.dao.ActivityDAO;
import com.capstone.CaseStudy.database.dao.MessageDAO;
import com.capstone.CaseStudy.database.dao.UserActivityDAO;
import com.capstone.CaseStudy.database.dao.UserDAO;
import com.capstone.CaseStudy.database.entity.Activity;
import com.capstone.CaseStudy.database.entity.User;
import com.capstone.CaseStudy.database.entity.UserActivity;
import com.capstone.CaseStudy.security.AuthenticatedUserService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import io.github.cdimascio.dotenv.Dotenv;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class UserController {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserDAO userDAO;

    @Autowired
    AuthenticatedUserService authenticatedUserService;

    @Autowired
    MessageDAO messageDAO;

    @Autowired
    ActivityDAO activityDAO;

    @Autowired
    UserActivityDAO userActivityDAO;

    @GetMapping("/user/{userId}")
    public ModelAndView userProfile(@PathVariable("userId") Integer userId){
        ModelAndView response = new ModelAndView();
        // this is the page primer for edit
        response.setViewName("user");

        User loggedInUser = authenticatedUserService.loadCurrentUser();

       User user = userDAO.findById(userId);
        Set<User> loggedInUserChats = messageDAO.findActiveChats(loggedInUser);
        Set<User> userChats = messageDAO.findActiveChats(user);
        String apiKey = Dotenv.load().get("API_KEY");
        userChats.retainAll(loggedInUserChats);
        userChats.remove(loggedInUser);
        userChats.remove(user);
        //        find current users activities
        List<Activity> activities = userActivityDAO.findUserActivities(user);
        response.addObject("apiKey", apiKey);
        response.addObject("mutualChats", userChats);
        response.addObject("user", user);
        response.addObject("activities", activities);
        return response;

    }


    @GetMapping("/account")
    public ModelAndView userAccount(){
        ModelAndView response = new ModelAndView();

        User loggedInUser = authenticatedUserService.loadCurrentUser();
        List<Activity> userActivities = userActivityDAO.findUserActivities(loggedInUser);
        List<Activity> activities = activityDAO.findAllActivities();
        response.setViewName("account");
        response.addObject("userActivities", userActivities);
        response.addObject("User", loggedInUser);
        response.addObject("activities", activities);
        return response;
    }

    @GetMapping("/account/activities")
    public ResponseEntity<List<Activity>> fetchActivities(){
        List<Activity> activities = userActivityDAO.findUserActivities(authenticatedUserService.loadCurrentUser());
        return ResponseEntity.ok(activities);
    }



    @DeleteMapping("/account/deleteActivity/{activityId}")
    public ResponseEntity<Void> DeleteUserActivity(@PathVariable Integer activityId) throws Exception {
        User loggedInUser = authenticatedUserService.loadCurrentUser();
        Activity activity = activityDAO.findActivityById(activityId);
        Integer deleteCount = userActivityDAO.removeUserActivity(activity, loggedInUser);
        boolean success = deleteCount > 0;
        if (success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/account/addActivity/{activityId}")
    public ResponseEntity<String> addUserActivity(@PathVariable Integer activityId) throws Exception {
        User loggedInUser = authenticatedUserService.loadCurrentUser();
        Activity activity = activityDAO.findActivityById(activityId);
        UserActivity userActivity = userActivityDAO.findUserActivity(activity, loggedInUser);
        if(userActivity == null){
            userActivity = new UserActivity();
            userActivity.setActivity(activity);
            userActivity.setUser(loggedInUser);
            userActivityDAO.save(userActivity);
            return ResponseEntity.ok(activity.getName() + " has been added to your activities" );
        }else{
            return ResponseEntity.ok("You already have " + activity.getName() + " added  as an activity" );
        }

    }

    @PatchMapping("/account/updateImage")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile image) {
        User loggedInUser = authenticatedUserService.loadCurrentUser();
        if (image.isEmpty()) {
            return ResponseEntity.badRequest().body("No file uploaded");
        }

        try {
            LOG.debug("uploaded filename = {} size = {}", image.getOriginalFilename(), image.getSize());
            String pathToSave = "./src/main/webapp/pub/images/" + image.getOriginalFilename();
            Files.copy(image.getInputStream(), Paths.get(pathToSave), StandardCopyOption.REPLACE_EXISTING);
            String url = "/pub/images/" + image.getOriginalFilename();
            loggedInUser.setImage(url);
            userDAO.save(loggedInUser);
            LOG.debug("updated {}'s profile picture", loggedInUser.getFirstName());
            return ResponseEntity.ok(
                    Map.of(
                            "message", "Image uploaded successfully!",
                            "url", url
                    )
            );
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return ResponseEntity.status(500).body("Error uploading image: " + e.getMessage());
        }
    }

    @PatchMapping("/account/updatePersonal")
    public ResponseEntity<?> updatePersonal(@RequestBody Map<String, String> payload, HttpSession session) {
        User loggedInUser = authenticatedUserService.loadCurrentUser();
        String firstName = payload.get("firstName");
        String lastName = payload.get("lastName");

        try {
            loggedInUser.setFirstName(firstName);
            loggedInUser.setLastName(lastName);
            userDAO.save(loggedInUser);

            LOG.debug("updated {}'s personal info", loggedInUser.getFirstName());
            return ResponseEntity.ok(
                    Map.of(
                            "message", "profile updated successfully!"
                    )
            );
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return ResponseEntity.status(500).body("Error updating profile: " + e.getMessage());
        }
    }

    @PatchMapping("/account/updateLocation")
    public ResponseEntity<?> updateLocation(@RequestBody Map<String, String> payload) {
        User loggedInUser = authenticatedUserService.loadCurrentUser();
        String city = payload.get("city");
        String state = payload.get("state");

        try {
            loggedInUser.setCity(city);
            loggedInUser.setState(state);
            userDAO.save(loggedInUser);

            LOG.debug("updated {}'s location", loggedInUser.getFirstName());
            return ResponseEntity.ok(
                    Map.of(
                            "message", "Location updated successfully!"
                    )
            );
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return ResponseEntity.status(500).body("Error updating Location: " + e.getMessage());
        }
    }


    @PatchMapping("/account/updateBio")
    public ResponseEntity<?> updateBio(@RequestBody Map<String, String> payload) {
        User loggedInUser = authenticatedUserService.loadCurrentUser();
        String description = payload.get("description");

        try {
            loggedInUser.setDescription(description);
            userDAO.save(loggedInUser);

            LOG.debug("updated {}'s Bio", loggedInUser.getFirstName());
            return ResponseEntity.ok(
                    Map.of(
                            "message", "Bio updated successfully!"
                    )
            );
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return ResponseEntity.status(500).body("Error updating Bio: " + e.getMessage());
        }
    }






}
