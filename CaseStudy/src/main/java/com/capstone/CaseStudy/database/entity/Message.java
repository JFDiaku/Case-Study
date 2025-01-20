package com.capstone.CaseStudy.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "message")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "incoming_msg_id", nullable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(name = "outgoing_msg_id", nullable = false)
    private User receiver;


    @Column(name = "message")
    private String message;

    @Column(name = "createdat", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }


}
