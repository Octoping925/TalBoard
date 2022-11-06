package com.talmo.talboard.service;

import com.talmo.talboard.config.TestHelper;
import com.talmo.talboard.domain.Member;
import com.talmo.talboard.domain.Post;
import com.talmo.talboard.repository.LikesRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class PostServiceTest {
    @Autowired MemberService memberService;
    @Autowired PostService postService;
    @Autowired LikesRepository likesRepository;

    Member member1, member2, member3;
    Post post1;

    @BeforeEach
    void beforeEach() {
        member1 = TestHelper.createMember(1);
        member2 = TestHelper.createMember(2);
        member3 = TestHelper.createMember(3);
        memberService.join(member1);
        memberService.join(member2);
        memberService.join(member3);
        post1 = TestHelper.createPost(member1);
        postService.create(post1);
    }

    @Test
    void likeOrDislikePostTest() {
        // given
        // when
        Long likes1 = postService.likeOrDislikePost(member1, post1, true);
        Long likes2 = postService.likeOrDislikePost(member2, post1, false);

        // then
        Assertions.assertFalse(member1.getLikesList().isEmpty());
        Assertions.assertFalse(member2.getLikesList().isEmpty());
        Assertions.assertFalse(post1.getLikesList().isEmpty());

        Assertions.assertTrue(member1.getLikesList().contains(likesRepository.findOne(likes1)));
        Assertions.assertTrue(member2.getLikesList().contains(likesRepository.findOne(likes2)));
    }


}
