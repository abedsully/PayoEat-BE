package com.example.PayoEat.repository;

import com.example.PayoEat.model.Image;
import com.example.PayoEat.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, UUID> {
    Image findByMenu(Menu menu);
}
