package com.example.withpet_01.api.repository;

import com.example.withpet_01.api.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostImageRepository extends JpaRepository<Image,Long> {
}
