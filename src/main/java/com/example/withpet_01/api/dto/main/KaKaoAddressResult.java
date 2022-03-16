package com.example.withpet_01.api.dto.main;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class KaKaoAddressResult {
    private ArrayList<AddressDocuments> documents;
}
