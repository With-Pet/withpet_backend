package com.example.withpet_01.api.domain;

import com.example.withpet_01.api.domain.common.CommonDateEntity;
import com.example.withpet_01.api.domain.enumExam.PetSex;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Pet extends CommonDateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 5, nullable = false, unique = true)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User owner;

    private String name;

    private LocalDateTime birthday;

    private String kind;

    @Column(length = 500)
    private String note;

    private Long weight;

    @Column(length = 500)
    private String review; //?

    private boolean neuralization;

    @Enumerated(EnumType.STRING)
    private PetSex sex;
}
