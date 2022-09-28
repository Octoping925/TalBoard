package com.talmo.talboard.service;

import com.talmo.talboard.config.TestHelper;
import com.talmo.talboard.domain.Member;
import com.talmo.talboard.domain.Post;
import com.talmo.talboard.repository.LikesRepository;
import java.util.Objects;
import org.junit.jupiter.api.Assertions;
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

    @Test
    void likeOrDislikePostTest() {
        // given
        Member member = TestHelper.createMember(1);
        Member member2 = TestHelper.createMember(2);
        Post post = TestHelper.createPost(member);
        memberService.join(member);
        memberService.join(member2);
        postService.create(post);

        // when
        Long likes1 = postService.likeOrDislikePost(member, post, true);
        Long likes2 = postService.likeOrDislikePost(member2, post, false);

        // then
        Assertions.assertFalse(member.getLikesList().isEmpty());
        Assertions.assertFalse(member2.getLikesList().isEmpty());
        Assertions.assertFalse(post.getLikesList().isEmpty());

        Assertions.assertTrue(member.getLikesList().stream().anyMatch(likes -> likes1.equals(likes.getLikeNo())));
        Assertions.assertTrue(member2.getLikesList().stream().anyMatch(likes -> likes2.equals(likes.getLikeNo())));
    }


}
