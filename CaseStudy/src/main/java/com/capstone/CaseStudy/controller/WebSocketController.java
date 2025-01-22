package com.capstone.CaseStudy.controller;

import com.capstone.CaseStudy.database.dao.MessageDAO;
import com.capstone.CaseStudy.database.dao.UserDAO;
import com.capstone.CaseStudy.database.entity.Message;
import com.capstone.CaseStudy.database.entity.User;
import com.capstone.CaseStudy.form.ChatMessageDTO;
import com.capstone.CaseStudy.security.AuthenticatedUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.List;

@Controller
public class WebSocketController {

    private static final Logger LOG = LoggerFactory.getLogger(WebSocketController.class);
    @Autowired
    private MessageDAO messageDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;



    @MessageMapping("/sendMessage")
    public void handleMessage(@Payload ChatMessageDTO messageDTO, Principal principal) {
        // Map DTO to Message entity
        String username = principal.getName();
        User loggedInUser = userDAO.findByEmail(username);

        User receiver = userDAO.findById(messageDTO.getReceiverId());
        LOG.debug("sender {}, receiver: {}", loggedInUser.getId(), receiver.getId());
        Message message = new Message();
        message.setMessage(messageDTO.getMessage());
        message.setSender(loggedInUser);
        message.setReceiver(receiver);
        // Save message to the database
        messageDAO.save(message);

        messagingTemplate.convertAndSend("/topic/messages/" + messageDTO.getReceiverId(), message);
    }
}
