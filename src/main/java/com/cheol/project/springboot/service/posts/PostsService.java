package com.cheol.project.springboot.service.posts;

import com.cheol.project.springboot.config.auth.dto.SessionUser;
import com.cheol.project.springboot.domain.posts.Posts;
import com.cheol.project.springboot.domain.posts.PostsRepository;
import com.cheol.project.springboot.web.dto.PostsListResponseDto;
import com.cheol.project.springboot.web.dto.PostsResponseDto;
import com.cheol.project.springboot.web.dto.PostsSaveRequestDto;
import com.cheol.project.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto, SessionUser user) {
        Long n = Long.valueOf(-1);

        Posts posts = postsRepository.findById(id).orElseThrow(() -> new
                IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        if(user.getEmail().equals(requestDto.getEmail())) {
            posts.update(requestDto.getTitle(), requestDto.getContent());
            return id;
        }
        return n;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id, SessionUser user){
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        if(user.getEmail().equals(posts.getEmail())) {
            postsRepository.delete(posts);
        }else {
            postsRepository.delete(null);
        }
    }
}
