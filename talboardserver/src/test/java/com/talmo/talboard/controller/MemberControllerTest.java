package com.talmo.talboard.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.talmo.talboard.config.TestHelper;
import com.talmo.talboard.domain.Member;
import com.talmo.talboard.domain.Post;
import com.talmo.talboard.domain.vo.MemberNoVO;
import com.talmo.talboard.domain.vo.PostCreateVO;
import com.talmo.talboard.domain.vo.PostInfoVO;
import com.talmo.talboard.repository.PostRepository;
import com.talmo.talboard.service.MemberService;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class MemberControllerTest {
    @Autowired MemberService memberService;
    @Autowired MemberController memberController;
    @Autowired PostRepository postRepository;

    @Test
    public void join() {
    }

    @Test
    public void resign() {
    }

    @Test
    public void findId() {
    }

    @Test
    public void findPassword() {
    }

    @Test
    public void changeAccountInfo() {
    }

    @Test
    public void findBlockList() {
    }

    @Test
    public void blockMember() {
    }

    @Test
    public void unblockMember() {
    }

    @Test
    public void getMemberPostList() {
        // given
        Member member = TestHelper.createMember();
        Post post = TestHelper.createPost(member, 3, 5);
        Post post2 = TestHelper.createPost(member, 5, 8);

        memberService.join(member);
        postRepository.save(post);
        postRepository.save(post2);

        // when
        MemberNoVO vo = new MemberNoVO();
        vo.setMember_no(member.getMember_no());
        ResponseEntity<Map<String, Object>> postList = memberController.getMemberPostList(vo);

        // then
        List<PostInfoVO> data = (List<PostInfoVO>) postList.getBody().get("data");
        String message = (String) postList.getBody().get("message");

        assertEquals(2, data.size());
        assertEquals(data.get(0).getPost_no(), post.getPost_no());
        assertEquals(data.get(0).getTitle(), post.getTitle());
        assertEquals(data.get(1).getPost_no(), post2.getPost_no());
        assertEquals(data.get(1).getTitle(), post2.getTitle());
        assertEquals("조회 성공", message);
    }
}