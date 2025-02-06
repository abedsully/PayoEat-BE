package com.example.PayoEat.service.image;

import com.example.PayoEat.dto.ImageDto;
import com.example.PayoEat.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface IImageService {
    Image saveImage(MultipartFile file, String menuCode);
    Image getImageById(UUID imageId);
}
