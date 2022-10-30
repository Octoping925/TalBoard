package com.talmo.talboard.service;

import com.talmo.talboard.config.ExceptionConstants;
import com.talmo.talboard.domain.Likes;
import com.talmo.talboard.domain.Member;
import com.talmo.talboard.domain.Post;
import com.talmo.talboard.domain.Report;
import com.talmo.talboard.domain.vo.PostRequirementVO;
import com.talmo.talboard.domain.vo.PostUpdateVO;
import com.talmo.talboard.repository.LikesRepository;
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
    private final LikesRepository likesRepository;

    @Transactional(readOnly = true)
    public Post findOne(Long postNo) {
        Post post = postRepository.findOne(postNo);

        if(post.getDeleteYn().equals("Y")) {
            throw new IllegalStateException("게시글 상세 조회 실패");
        }

        return post;
    }

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    public List<Report> findAllReportPosts() {
        return postRepository.findAllReportPosts();
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

    @Transactional
    public Long likeOrDislikePost(Member member, Post post, boolean likeYn) {
        if(member.isAlreadyLikedPost(post)) {
            throw new IllegalStateException(ExceptionConstants.ALREADY_LIKEDORDISLIKED_POST_MESSAGE);
        }

        Likes likes = Likes.create(member, post, likeYn);
        likesRepository.save(likes);
        return likes.getLikeNo();
    }

}
