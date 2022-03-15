package com.example.withpet_01.api.dto.Post;

import com.example.withpet_01.api.dto.login.CommonResult;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
public class PageResult<T> extends CommonResult {
    private Page<T> data;
}