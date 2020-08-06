package com.cheol.project.springboot.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsUpdateRequestDto {
    private String title;
    private String content;
    private String email;

    @Builder
    public PostsUpdateRequestDto(String title, String content, String email){
        this.title = title;
        this.content = content;
        this.email = email;
    }
}
