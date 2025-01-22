package com.capstone.CaseStudy.controller;

import com.capstone.CaseStudy.database.dao.MessageDAO;
import com.capstone.CaseStudy.database.dao.UserActivityDAO;
import com.capstone.CaseStudy.database.dao.UserDAO;
import com.capstone.CaseStudy.database.dao.UserLogDAO;
import com.capstone.CaseStudy.database.entity.Activity;
import com.capstone.CaseStudy.database.entity.Message;
import com.capstone.CaseStudy.database.entity.User;
import com.capstone.CaseStudy.security.AuthenticatedUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class MessageController {

    private static final Logger LOG = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    MessageDAO messageDAO;

    @Autowired
    UserLogDAO userLogDAO;

    @Autowired
    AuthenticatedUserService authenticatedUserService;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserActivityDAO userActivityDAO;

    @GetMapping("/messages")
    public ModelAndView MessagePage() {
        ModelAndView response = new ModelAndView();
        User loggedInUser = authenticatedUserService.loadCurrentUser();
        Set<User> activeChats = messageDAO.findActiveChats(loggedInUser);
        activeChats.remove(loggedInUser);

        Map<User, String> userMessages = new HashMap<>();
        LocalDateTime lastLogout = userLogDAO.findLastLogout(loggedInUser);
        List<Message> newMessages = messageDAO.findNewMessages( lastLogout, loggedInUser);
        Map<User, String> newMessageMap = new HashMap<>();

        for(Message message : newMessages) {
            User user = message.getSender();
            newMessageMap.put(user, message.getMessage());
        }
        response.setViewName("messages");
        for (User user : activeChats) {
            String message = messageDAO.getLastMessage(user.getId());
            userMessages.put(user, message);
        }



        response.addObject("newMessages", newMessageMap);
        response.addObject("userId", loggedInUser.getId());
        response.addObject("activeChats", activeChats);
        response.addObject("userMessages", userMessages);
        return response;
    }


    @GetMapping("/messages/user/")
    public ResponseEntity<?> changeReceiver(@RequestParam Integer userId) {
        User user = userDAO.findById(userId);
        User loggedInUser = authenticatedUserService.loadCurrentUser();
        List<Activity> userActivities = userActivityDAO.findUserActivities(user);
        List<Message> messages = messageDAO.findMessagesBetweenUsers(loggedInUser, user);

        try {
            LOG.debug("now chatting with {}", user.getFirstName());
            return ResponseEntity.ok(
                    Map.of(
                            "user", user,
                            "activities", userActivities,
                            "messages", messages
                    )
            );
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return ResponseEntity.status(500).body("Error switching users " + e.getMessage());
        }
    }

}
