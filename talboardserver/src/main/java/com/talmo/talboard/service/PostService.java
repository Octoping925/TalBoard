package com.talmo.talboard.service;

import com.talmo.talboard.domain.Post;
import com.talmo.talboard.domain.vo.PostRequirementVO;
import com.talmo.talboard.domain.vo.PostUpdateVO;
import com.talmo.talboard.repository.MemberRepository;
import com.talmo.talboard.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Post findOne(Long postNo) {
        Post post = postRepository.findOne(postNo);

        if(post.getDeleteYn().equals("Y")) {
            throw new IllegalStateException("게시글 상세 조회 실패");
        }

        return post;
    }

    @Transactional
    public List<Post> findRequirementsAll(PostRequirementVO vo) {
        if(vo.getMemberId() != null && vo.getTitle() != null) {
            throw new IllegalArgumentException("하나의 값만 넣어주세요.");
        } else if(vo.getMemberId() != null) {
            return postRepository.searchById(vo.getMemberId());
        } else if(vo.getTitle() != null){
            return postRepository.searchByTitle(vo.getTitle());
        }

        throw new IllegalArgumentException("작성자 또는 제목을 검색 해주세요.");
    }

    @Transactional
    public Long create(Post post) {
        postRepository.save(post);
        return post.getPostNo();
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
