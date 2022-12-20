package com.example.mvpbot.Entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "name")
    private String name;

    @Column(name = "state")
    private String state;

    @Column(name = "startwait")
    private boolean startWait;

    @Column(name = "viewed_video")
    private boolean viewedVideo;

    @Column(name = "category")
    private String category;

    @Column(name = "registration_time")
    private LocalDateTime registrationDate;

    @Column(name = "another_country")
    private String anotherCountry;
}
