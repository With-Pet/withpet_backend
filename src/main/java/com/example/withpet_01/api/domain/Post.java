package com.example.withpet_01.api.domain;
import com.example.withpet_01.api.domain.common.CommonDateEntity;
import com.example.withpet_01.api.domain.service.Service;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "post")
public class Post extends CommonDateEntity {

    @Id
    @Column(length = 5, nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;                 //게시물 고유 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;              //작성자 정보

    @Column(length = 50, nullable = false)
    private String postAuthor;      //작성자 이름

    @Column(length = 1, nullable = false)
    private String postType;        // 산책 : W, 돌봄 : C, 체험 : E

    @JoinColumn(name = "pet_id")
    @OneToOne
    private Pet pet;                //게시물 펫

    @Column(nullable = false, length = 100)
    private String postTitle;       //게시물 제목

    @Column(length = 50)
    private String postPrice;       //시간당 이용 금액

    @Column(length = 100, nullable = false)
    private String postLocation;    //게시물 지역 명

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    private Service service;        //서비스 상세

    @Column(length = 1000)
    private String postRequested;   //요청 사항

    //하나의 게시글이 여러 파일을 가지므로 N(postImage) : 1(post) 관계가 된다.
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private List<Image> postImage = new ArrayList<>();    //게시물 사진

    @Column(length = 100, nullable = false)
    private LocalDateTime postSTime;    //시작 날짜

    @Column(length = 100, nullable = false)
    private LocalDateTime postETime;    //종료 날짜

    public Post setUpdate(String postAuthor, String postTitle) {
        this.postAuthor = postAuthor;
        this.postTitle = postTitle;
        return this;
    }

    // Post 에서 파일 처리 위함
    public void addPhoto(Image postImage) {
        this.postImage.add(postImage);

        // 게시글에 파일이 저장되어있지 않은 경우
        if(postImage.getPost() != this)
            // 파일 저장
            postImage.setPost(this);
    }
}
