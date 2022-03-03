package com.example.withpet_01.api.controller;
import com.example.withpet_01.api.domain.Post;
import com.example.withpet_01.api.domain.User;
import com.example.withpet_01.api.service.post.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//
//@WebMvcTest(PostController.class)
//class PostControllerTest {
//    @Autowired
//    MockMvc mvc;
//
//    @MockBean
//    PostService postService;
//
//    @Test
//    @DisplayName("포스트 만들기 테스트")
//    public void postTest() throws Exception{
//        //given
//        List<Post> posts = new ArrayList<>();
//        User user = new User();
//        posts.add(new Post(user,"test","testtitle","zzzzzz"));
//
//
//        mvc.perform(get("/post"))
//                .andExpect(status().isOk())
//                .andExpect(content().string(containsString("test")));
//        }
//        //when
//
//
//        //then
//
//    }


