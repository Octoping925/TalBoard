package com.talmo.talboard.service;

import com.talmo.talboard.domain.Member;
import com.talmo.talboard.domain.Post;
import com.talmo.talboard.domain.vo.UpdatePostVO;
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
    public Long create(Post post) {
        postRepository.save(post);
        return post.getPost_no();
    }

    @Transactional
    public Post findOne(Long postNo) {
        Post post = postRepository.findOne(postNo);

        if(post.getDelete_yn().equals("Y")) {
            throw new IllegalStateException("게시글 상세 조회 실패");
        }

        return post;
    }

//    @Transactional
//    public void updatePost(Post post, UpdatePostVO vo) {
//        if(vo.getTitle() != null && vo.getContext() != null) {
//            post.update(vo, vo.getMemberNo());
//        }
//    }
}
