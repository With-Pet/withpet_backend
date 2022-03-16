package com.example.withpet_01.api.repository;

import com.example.withpet_01.api.domain.Post;
import com.example.withpet_01.api.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAll(Pageable pageable);

    Post findById(int id);

    Page<Post> findByPostSTimeGreaterThanAndPostETimeLessThanAndPostPriceBetweenAndPostType(Pageable pageable, LocalDateTime minTime, LocalDateTime maxTime,String minPrice, String maxPrice, String postType);


}
