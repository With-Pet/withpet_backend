package com.example.withpet_01.api.domain;
import com.example.withpet_01.api.domain.common.CommonDateEntity;
import com.example.withpet_01.api.domain.service.Service;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter
@NoArgsConstructor
public class Post extends CommonDateEntity {

    @Id
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, length = 50)
    private String author;

    @JoinColumn(name = "pet_id")
    @OneToOne
    private Pet pet;

    @Column(nullable = false, length = 100)
    private String title;
    @Column(length = 500)
    private String content;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    private Service service;

    @Column(length = 500)
    private String requested;

    private Long pay;

    private LocalDateTime serviceStartedAt;
    private LocalDateTime serviceFinishedAt;

    public Post(User user,  String author, String title, String content) {
        this.user = user;
        this.author = author;
        this.title = title;
        this.content = content;
    }
    public Post setUpdate(String author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
        return this;
    }
}
