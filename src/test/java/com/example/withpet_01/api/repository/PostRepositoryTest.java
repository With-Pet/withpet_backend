package com.example.withpet_01.api.repository;

import com.example.withpet_01.api.domain.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostRepositoryTest {
    @Autowired
    private PostRepository postRepository;

    @Test
    void postTest() {
        Post post = Post.builder()
                .postAuthor("김형주")
                .postType("W")
                .postSTime(LocalDateTime.parse("2022-02-15T18:45:22.639616"))
                .postETime(LocalDateTime.parse("2022-02-20T18:45:22.639616"))
                .postTitle("코코 산책시켜주실분")
                .postLocation("서울시 종로구 무악동 현대아파트")
                .postRequested("가파른 산은 가급적 피해주세요")
                .x("35")
                .y("120")
                .postPrice("3000").build();

        postRepository.save(post);
//        Post post2 = postRepository.findByPostSTimeGreaterThanAndPostETimeLessThanAndPostPriceBetweenAndPostType
//                (LocalDateTime.parse("2022-02-14T18:45:22.639616"),
//                        LocalDateTime.parse("2022-02-21T18:45:22.639616"),
//                        "0","4000","W");
//        System.out.println(post2);
    }
}