package com.talmo.talboard.controller;

import com.talmo.talboard.config.ResponseConstants;
import com.talmo.talboard.config.TestHelper;
import com.talmo.talboard.domain.Member;
import com.talmo.talboard.domain.vo.MemberJoinVO;
import com.talmo.talboard.config.ExceptionConstants;
import com.talmo.talboard.repository.MemberRepository;
import com.talmo.talboard.service.BlockService;
import com.talmo.talboard.service.MemberService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

// @ExtendWith(SpringExtension.class)
@WebMvcTest(MemberController.class)
// @Transactional
class MemberControllerTest {
    // @Autowired MemberService memberService;
    // @Autowired BlockService blockService;
    // @Autowired MemberController memberController;
    // @Autowired PostRepository postRepository;

    @Autowired MockMvc mockMvc;
    @MockBean MemberService memberService;
    @MockBean BlockService blockService;
    @MockBean MemberRepository memberRepository;

    MultiValueMap<String, String> multiValueMap;

    @BeforeEach
    public void initSetting() {
        multiValueMap = new LinkedMultiValueMap<>();
    }

    @Test
    void join() throws Exception {
        // given
        MemberJoinVO vo = new MemberJoinVO(TestHelper.testId, TestHelper.testPw, TestHelper.testEmail);
        given(memberService.join(Member.regist(vo))).willReturn(1L);

        // when
        mockMvc.perform(post("/members/regist")
        .param("id", vo.getId())
        .param("password", vo.getPassword())
        .param("emailAddress", vo.getEmailAddress())
        .contentType(MediaType.APPLICATION_JSON))

        // then
        .andExpect(status().isOk());
    }

    @Test
    void join_아이디유효성실패() throws Exception {
        mockMvc.perform(post("/members/regist")
        .param("id", TestHelper.failId)
        .param("password", TestHelper.failPw)
        .param("emailAddress", TestHelper.testEmail)
        .contentType(MediaType.APPLICATION_JSON))

        // then
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value(ExceptionConstants.INVALID_ID_MESSAGE));
    }

    @Test
    void join_비밀번호유효성실패() throws Exception {
        mockMvc.perform(post("/members/regist")
        .param("id", TestHelper.testId)
        .param("password", TestHelper.failPw)
        .param("emailAddress", TestHelper.testEmail)
        .contentType(MediaType.APPLICATION_JSON))

        // then
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value(ExceptionConstants.INVALID_PW_MESSAGE));
    }

    @Test
    void join_이메일유효성실패() throws Exception {
        mockMvc.perform(post("/members/regist")
        .param("id", TestHelper.testId)
        .param("password", TestHelper.testPw)
        .param("emailAddress", TestHelper.failEmail)
        .contentType(MediaType.APPLICATION_JSON))

        // then
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value(ExceptionConstants.INVALID_EMAIL_MESSAGE));
    }
    
    @Test
    void join_중복아이디이메일() throws Exception {
        // given
        MemberJoinVO vo = new MemberJoinVO(TestHelper.testId, TestHelper.testPw, TestHelper.testEmail);
        when(memberService.join(Member.regist(vo)))
        .thenThrow(new IllegalStateException(ExceptionConstants.DUPLICATE_EMAIL_MESSAGE));


        // when
        mockMvc.perform(post("/members/regist")
        .param("id", vo.getId())
        .param("password", vo.getPassword())
        .param("emailAddress", vo.getEmailAddress())
        .contentType(MediaType.APPLICATION_JSON))

        // then
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.message").value(ExceptionConstants.DUPLICATE_EMAIL_MESSAGE));
    }

    @Test
    void resign() throws Exception {
        mockMvc.perform(delete("/members/resign")
        .param("memberNo", "1")
        .param("resignMemberNo", "2")
        .contentType(MediaType.APPLICATION_JSON))

        // then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message").value(ResponseConstants.RESIGN_SUCCESS_MESSAGE));
    }

    // @Test
    // void resign_권한없음() {
    //     // given
    //     Member member = TestHelper.createMember(1);
    //     Member member2 = TestHelper.createMember(2);
    //     memberService.join(member);
    //     memberService.join(member2);

    //     // when
    //     MemberResignVO vo = new MemberResignVO(member.getMemberNo(), member2.getMemberNo());
    //     Map<String, Object> body = memberController.resign(vo).getBody();

    //     // then
    //     assertNull(body.get("data"));
    //     assertEquals(ExceptionConstants.NO_AUTHORIZE_MESSAGE, body.get("message"));
    // }

    // @Test
    // void resign_회원찾기실패() {
    //     // given
    //     Member member = TestHelper.createMember();
    //     memberService.join(member);
    //     MemberResignVO vo = new MemberResignVO(member.getMemberNo(), -1L);
    //     MemberResignVO vo2 = new MemberResignVO(-1L, member.getMemberNo());
    //     MemberResignVO vo3 = new MemberResignVO(-1L, -1L);

    //     // when
    //     Map<String, Object> body = memberController.resign(vo).getBody();
    //     Map<String, Object> body2 = memberController.resign(vo2).getBody();
    //     Map<String, Object> body3 = memberController.resign(vo3).getBody();

    //     // then
    //     assertNull(body.get("data"));
    //     assertEquals(ExceptionConstants.NO_MEMBER_FOUND_MESSAGE, body.get("message"));
    //     assertNull(body2.get("data"));
    //     assertEquals(ExceptionConstants.NO_MEMBER_FOUND_MESSAGE, body2.get("message"));
    //     assertNull(body3.get("data"));
    //     assertEquals(ExceptionConstants.NO_MEMBER_FOUND_MESSAGE, body3.get("message"));
    // }

    // @Test
    // void findId() {
    //     // given
    //     Member member = TestHelper.createMember();
    //     memberService.join(member);
    //     MemberFindIdVO vo = new MemberFindIdVO();
    //     vo.setEmailAddress(member.getEmailAddress());

    //     // when
    //     Map<String, Object> body = memberController.findId(vo).getBody();

    //     // then
    //     assertEquals(member.getId(), body.get("data"));
    //     assertEquals(ResponseConstants.FINDID_SUCCESS_MESSAGE, body.get("message"));
    // }

    // @Test
    // void findId_실패() {
    //     // given
    //     MemberFindIdVO vo = new MemberFindIdVO();
    //     vo.setEmailAddress(TestHelper.testEmail);

    //     // when
    //     Map<String, Object> body = memberController.findId(vo).getBody();

    //     // then
    //     assertEquals(null, body.get("data"));
    //     assertEquals(ExceptionConstants.NO_MEMBER_FOUND_MESSAGE, body.get("message"));
    // }

    // @Test
    // void findPassword() {
    //     // given
    //     Member member = TestHelper.createMember();
    //     memberService.join(member);
    //     MemberFindPasswordVO vo = new MemberFindPasswordVO();
    //     vo.setId(member.getId());

    //     // when
    //     Map<String, Object> body = memberController.findPassword(vo).getBody();

    //     // then
    //     assertEquals(member.getPassword(), body.get("data"));
    //     assertEquals(ResponseConstants.FINDPW_SUCCESS_MESSAGE, body.get("message"));
    // }

    // @Test
    // void findPassword_실패() {
    //     // given
    //     MemberFindPasswordVO vo = new MemberFindPasswordVO();
    //     vo.setId(TestHelper.testPw);

    //     // when
    //     Map<String, Object> body = memberController.findPassword(vo).getBody();

    //     // then
    //     assertEquals(null, body.get("data"));
    //     assertEquals(ExceptionConstants.NO_MEMBER_FOUND_MESSAGE, body.get("message"));
    // }

    // @Test
    // void changeAccountInfo() {
    // }

    // @Test
    // void changeAccountInfo_실패() {
    // }

    // @Test
    // void findBlockList() {
    // }

    // @Test
    // void findBlockList_실패() {
    // }

    // @Test
    // void blockMember() {
    //     // given
    //     Member member = TestHelper.createMember(1);
    //     Member member2 = TestHelper.createMember(2);
    //     memberService.join(member);
    //     memberService.join(member2);

    //     // when
    //     MemberBlockVO vo = new MemberBlockVO();
    //     vo.setMemberNo(member.getMemberNo());
    //     vo.setBlockedMemberNo(member2.getMemberNo());
    //     ResponseEntity<Map<String, Object>> res = memberController.blockMember(vo);

    //     // then
    //     Map<String, Object> body = res.getBody();
    //     assertEquals(HttpStatus.OK, res.getStatusCode());
    //     assertNull(body.get("data"));
    //     assertEquals(ResponseConstants.BLOCK_SUCCESS_MESSAGE, body.get("message"));
    // }

    // @Test
    // void blockMember_실패() {
    // }

    // @Test
    // void unblockMember() {
    //     // given
    //     Member member = TestHelper.createMember(1);
    //     Member member2 = TestHelper.createMember(2);
    //     memberService.join(member);
    //     memberService.join(member2);
    //     blockService.blockMember(member, member2);

    //     // when
    //     MemberBlockVO vo = new MemberBlockVO();
    //     vo.setMemberNo(member.getMemberNo());
    //     vo.setBlockedMemberNo(member2.getMemberNo());
    //     ResponseEntity<Map<String, Object>> res = memberController.unblockMember(vo);

    //     // then
    //     Map<String, Object> body = res.getBody();
    //     assertEquals(HttpStatus.OK, res.getStatusCode());
    //     assertNull(body.get("data"));
    //     assertEquals(ResponseConstants.UNBLOCK_SUCCESS_MESSAGE, body.get("message"));
    // }

    // @Test
    // void unblockMember_실패() {
        
    // }

    // @Test
    // void getMemberPostList() {
    //     // given
    //     Member member = TestHelper.createMember();
    //     Post post = TestHelper.createPost(member);
    //     Post post2 = TestHelper.createPost(member, 3, 5);

    //     memberService.join(member);
    //     postRepository.save(post);
    //     postRepository.save(post2);

    //     MemberNoVO vo = new MemberNoVO();
    //     vo.setMemberNo(member.getMemberNo());

    //     // when
    //     Map<String, Object> body = memberController.getMemberPostList(vo).getBody();
    //     List<PostInfoVO> postList = (List<PostInfoVO>) body.get("data");
    //     String message = (String) body.get("message");

    //     // then
    //     assertEquals(2, postList.size());
    //     assertEquals(postList.get(0).getPost_no(), post.getPost_no());
    //     assertEquals(postList.get(0).getTitle(), post.getTitle());
    //     assertEquals(postList.get(1).getPost_no(), post2.getPost_no());
    //     assertEquals(postList.get(1).getTitle(), post2.getTitle());
    //     assertEquals(ResponseConstants.SEARCH_SUCCESS_MESSAGE, message);
    // }

    // @Test
    // void getMemberPostList_멤버없을때() {
    //     // given
    //     MemberNoVO vo = new MemberNoVO();
    //     vo.setMemberNo(-1L);

    //     // when
    //     Map<String, Object> body = memberController.getMemberPostList(vo).getBody();
    //     List<PostInfoVO> postList = (List<PostInfoVO>) body.get("data");
    //     String message = (String) body.get("message");

    //     // then
    //     assertEquals(0, postList.size());
    //     assertEquals(ResponseConstants.SEARCH_SUCCESS_MESSAGE, message);
    // }
}