package com.example.withpet_01.api.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PhotoDto {
    private String origFileName;
    private String filePath;
    private long fileSize;
}
