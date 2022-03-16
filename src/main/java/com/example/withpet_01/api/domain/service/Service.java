package com.example.withpet_01.api.domain.service;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Map;

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

//    private Map<String, Boolean> bath;      //목욕
//
//    private Map<String, Boolean> garden;    //마당 보유
//
//    private Map<String, Boolean> pickUp;    //집 앞 픽업
//
//    private Map<String, Boolean> indoorPlay;//실내 놀이
//
//    private Map<String, Boolean> beauty;    //미용
//
//    private Map<String, Boolean> hairCare;  //모발 관리
//
//    private Map<String, Boolean> dogCare;   //노견 케어
//
//    private Map<String, Boolean> puppyCare; //퍼피 케어
//
//    private Map<String, Boolean> hiking;    //등산
//
//    private Map<String, Boolean> disinfection;  //소독

}
