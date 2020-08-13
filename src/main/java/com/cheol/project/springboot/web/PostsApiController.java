package com.cheol.project.springboot.web;

import com.cheol.project.springboot.config.auth.LoginUser;
import com.cheol.project.springboot.config.auth.dto.SessionUser;
import com.cheol.project.springboot.service.posts.PostsService;
import com.cheol.project.springboot.web.dto.PostsResponseDto;
import com.cheol.project.springboot.web.dto.PostsSaveRequestDto;
import com.cheol.project.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {
    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public long save(@RequestBody PostsSaveRequestDto requestDto){
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto,
                       @LoginUser SessionUser user){
            return postsService.update(id, requestDto, user);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id){
        return postsService.findById(id);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id, @LoginUser SessionUser user){
        postsService.delete(id, user);
        return id;
    }
}
