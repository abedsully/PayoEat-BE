package com.example.PayoEat.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ImageDto {
    private UUID id;
    private String filename;
    private String downloadUrl;
}
