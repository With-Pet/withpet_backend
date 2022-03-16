package com.example.withpet_01.api.dto.main;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class Documents {
    private String address_name;    //주소명
    private String address_type;    //주소 타입 (도로명, 지번)
    private String x;               //x 좌표
    private String y;               //y 좌표
}
