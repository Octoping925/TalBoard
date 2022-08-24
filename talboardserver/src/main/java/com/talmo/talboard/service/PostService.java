package com.talmo.talboard.service;

import com.talmo.talboard.domain.Post;
import com.talmo.talboard.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public Long create(Post post) {
        postRepository.save(post);
        return post.getPost_no();
    }
}
