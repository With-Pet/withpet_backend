package com.example.withpet_01.api.domain.service;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype") // 구분자 컬럼
@Getter
@Setter
public abstract class Service {
    @Id
    @GeneratedValue
    @Column(name = "service_id")
    private Long id;
}
