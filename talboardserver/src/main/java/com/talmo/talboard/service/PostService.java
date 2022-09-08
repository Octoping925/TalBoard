package com.talmo.talboard.service;

import com.talmo.talboard.domain.Post;
import com.talmo.talboard.domain.vo.PostUpdateVO;
import com.talmo.talboard.repository.MemberRepository;
import com.talmo.talboard.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Post findOne(Long postNo) {
        Post post = postRepository.findOne(postNo);

        if(post.getDelete_yn().equals("Y")) {
            throw new IllegalStateException("게시글 상세 조회 실패");
        }

        return post;
    }

    @Transactional
    public Long create(Post post) {
        postRepository.save(post);
        return post.getPost_no();
    }

    @Transactional
    public void update(Post post, PostUpdateVO vo) {
        post.update(vo);
    }

    @Transactional
    public void delete(Post post) {
        post.delete();
    }

}
