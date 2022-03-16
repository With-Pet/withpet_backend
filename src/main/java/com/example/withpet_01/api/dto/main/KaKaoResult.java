package com.example.withpet_01.api.dto.main;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class KaKaoResult {
    private ArrayList<Documents> documents;
}
