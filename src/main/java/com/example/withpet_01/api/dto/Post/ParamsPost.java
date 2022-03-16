package com.example.withpet_01.api.dto.Post;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ParamsPost {

    @NotEmpty(message = "작성자명은 필수 값입니다.")
    @Size(min = 2, max = 50, message = "작성자명은 최소 2자에서 50자까지입니다.")
    @ApiModelProperty(value = "작성자명", required = true)
    private String postAuthor;

    @NotEmpty(message = "제목은 필수 값입니다.")
    @Size(min = 2, max = 100, message = "제목은 최소 2자에서 100자까지입니다.")
    @ApiModelProperty(value = "제목", required = true)
    private String postTitle;

    @NotEmpty(message = "게시물 타입은 필수 값입니다.")
    @ApiModelProperty(value = "게시물 타입", required = true)
    private String postType;   // 산책 : W, 돌봄 : C, 체험 : E

//    @NotBlank(message = "반려동물 고유 아이디는 필수 값입니다.")
//    @Size(min =1, max = 5)
//    @ApiModelProperty(value = "반려동물 고유 아이디", required = true)
//    private String petId;

    @NotNull
    @Size(max = 50, message = "시간당 이용 금액은 최대 50자까지입니다.")
    @ApiModelProperty(value = "시간당 이용 금액", required = true)
    private String postPrice;

    @NotEmpty(message = "지역명은 필수 값입니다.")
    @Size(min = 1, max = 100, message = "지역명은 최소 2자에서 100자까지입니다.")
    @ApiModelProperty(value = "지역명", required = true)
    private String postLocation;

    //private Service service;

    @NotNull
    @Size(max = 1000, message = "요청사항은 최대 100자까지입니다.")
    @ApiModelProperty(value = "요청사항", required = true)
    private String postRequested;

    @ApiModelProperty(value = "게시물 사진", required = true)
    private List<MultipartFile> files;

    @NotNull(message = "시작날짜는 필수 값입니다.")
    @ApiModelProperty(value = "시작날짜", required = true)
    private LocalDateTime  postSTime;

    @NotNull(message = "종료날짜는 필수 값입니다.")
    @ApiModelProperty(value = "종료날짜", required = true)
    private LocalDateTime postETime;

    @NotBlank(message = "반려동물을 선택해주세요")
    @ApiModelProperty(value = "반려동물 고유 아이디", required = true)
    private int petId;


}
