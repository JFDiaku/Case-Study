package com.capstone.CaseStudy.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageDTO {
    private Integer receiverId; // ID of the receiver
    private String message;
}
