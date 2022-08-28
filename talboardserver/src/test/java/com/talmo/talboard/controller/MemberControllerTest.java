package com.talmo.talboard.controller;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.anyLong;
import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.doReturn;
import static org.mockito.BDDMockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.talmo.talboard.config.ExceptionConstants;
import com.talmo.talboard.config.ResponseConstants;
import com.talmo.talboard.config.TestHelper;
import com.talmo.talboard.domain.Member;
import com.talmo.talboard.domain.vo.MemberDataChangeVO;
import com.talmo.talboard.exception.NoAuthorizationException;
import com.talmo.talboard.exception.NoMemberFoundException;
import com.talmo.talboard.repository.MemberRepository;
import com.talmo.talboard.service.BlockService;
import com.talmo.talboard.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(MemberController.class)
class MemberControllerTest {
    @Autowired MockMvc mockMvc;
    @MockBean MemberService memberService;
    @MockBean BlockService blockService;
    @MockBean MemberRepository memberRepository;

    @Test
    void join() throws Exception {
        // given
        doReturn(1L).when(memberService).join(any(Member.class));

        // when
        mockMvc.perform(post("/members/regist")
                .param("id", TestHelper.testId)
                .param("password", TestHelper.testPw)
                .param("emailAddress", TestHelper.testEmail)
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
        doThrow(new IllegalStateException(ExceptionConstants.DUPLICATE_EMAIL_MESSAGE))
                .when(memberService).join(any(Member.class));

        // when
        mockMvc.perform(post("/members/regist")
                .param("id", TestHelper.testId)
                .param("password", TestHelper.testPw)
                .param("emailAddress", TestHelper.testEmail)
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

     @Test
     void resign_권한없음() throws Exception {
         // given
         doReturn(TestHelper.createMember()).when(memberRepository).findOne(anyLong());
         doThrow(new NoAuthorizationException()).when(memberService).resign(any(Member.class), any(Member.class));

         // when
         mockMvc.perform(delete("/members/resign")
                 .param("memberNo", "1")
                 .param("resignMemberNo", "2")
                 .contentType(MediaType.APPLICATION_JSON))

         // then
         .andExpect(status().isForbidden())
         .andExpect(jsonPath("$.message").value(ExceptionConstants.NO_AUTHORIZE_MESSAGE));
     }

     @Test
     void resign_회원찾기실패() throws Exception {
         // given
         doThrow(new NoMemberFoundException()).when(memberRepository).findOne(anyLong());

         // when
         mockMvc.perform(delete("/members/resign")
                 .param("memberNo", "1")
                 .param("resignMemberNo", "2")
                 .contentType(MediaType.APPLICATION_JSON))

         // then
         .andExpect(status().isNotFound())
         .andExpect(jsonPath("$.message").value(ExceptionConstants.NO_MEMBER_FOUND_MESSAGE));
     }

     @Test
     void findId() throws Exception {
         // given
         doReturn(TestHelper.testEmail).when(memberService).findId(anyString());

         // when
         mockMvc.perform(get("/members/find/id")
                         .param("emailAddress", TestHelper.testEmail)
                         .contentType(MediaType.APPLICATION_JSON))

         // then
         .andExpect(status().isOk())
         .andExpect(jsonPath("$.data").value(TestHelper.testEmail))
         .andExpect(jsonPath("$.message").value(ResponseConstants.FINDID_SUCCESS_MESSAGE));
     }

     @Test
     void findId_실패() throws Exception {
         // given
         doThrow(new NoMemberFoundException()).when(memberService).findId(anyString());

         // when
         mockMvc.perform(get("/members/find/id")
                         .param("emailAddress", TestHelper.testEmail)
                         .contentType(MediaType.APPLICATION_JSON))

         // then
         .andExpect(status().isBadRequest())
         .andExpect(jsonPath("$.message").value(ExceptionConstants.NO_MEMBER_FOUND_MESSAGE));
     }

     @Test
     void findPassword() throws Exception {
         // given
         doReturn(TestHelper.testPw).when(memberService).findPassword(anyString());

         // when
         mockMvc.perform(get("/members/find/password")
                         .param("id", TestHelper.testId)
                         .contentType(MediaType.APPLICATION_JSON))

         // then
         .andExpect(status().isOk())
         .andExpect(jsonPath("$.data").value(TestHelper.testPw))
         .andExpect(jsonPath("$.message").value(ResponseConstants.FINDPW_SUCCESS_MESSAGE));
     }

     @Test
     void findPassword_실패() throws Exception {
         // given
         doThrow(new NoMemberFoundException()).when(memberService).findPassword(anyString());

         // when
         mockMvc.perform(get("/members/find/password")
                         .param("id", TestHelper.testId)
                         .contentType(MediaType.APPLICATION_JSON))

         // then
         .andExpect(status().isBadRequest())
         .andExpect(jsonPath("$.message").value(ExceptionConstants.NO_MEMBER_FOUND_MESSAGE));
     }

     @Test
     void changeAccountInfo() throws Exception {
         // given
         doReturn(TestHelper.createMember()).when(memberRepository).findOne(anyLong());

         // when
         mockMvc.perform(patch("/members/accountInfo")
                 .param("memberNo", "1")
                 .param("password", TestHelper.testPw)
                 .param("emailAddress", TestHelper.testEmail)
                 .contentType(MediaType.APPLICATION_JSON))

         // then
         .andExpect(status().isOk())
         .andExpect(jsonPath("$.message").value(ResponseConstants.CHANGE_SUCCESS_MESSAGE));
     }

     @Test
     void changeAccountInfo_유효성검사실패() throws Exception {
         // given
         doReturn(TestHelper.createMember()).when(memberRepository).findOne(anyLong());
         doThrow(new IllegalArgumentException(ExceptionConstants.INVALID_PW_MESSAGE))
             .when(memberService).updateMemberData(any(Member.class), any(MemberDataChangeVO.class));

         // when
         mockMvc.perform(patch("/members/accountInfo")
                 .param("memberNo", "1")
                 .param("password", TestHelper.failPw)
                 .param("emailAddress", TestHelper.failEmail)
                 .contentType(MediaType.APPLICATION_JSON))

         // then
         .andExpect(status().isBadRequest())
         .andExpect(jsonPath("$.message").value(ExceptionConstants.INVALID_PW_MESSAGE));
     }

    @Test
    void changeAccountInfo_회원찾기실패() throws Exception {
        // given
        doThrow(new NoMemberFoundException()).when(memberRepository).findOne(anyLong());

        // when
        mockMvc.perform(patch("/members/accountInfo")
                .param("memberNo", "1")
                .param("password", TestHelper.testPw)
                .param("emailAddress", TestHelper.testEmail)
                .contentType(MediaType.APPLICATION_JSON))

            // then
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value(ExceptionConstants.NO_MEMBER_FOUND_MESSAGE));
    }

    @Test
    void changeAccountInfo_동일이메일존재() throws Exception {
        // given
        doReturn(TestHelper.createMember()).when(memberRepository).findOne(anyLong());
        doThrow(new IllegalStateException(ExceptionConstants.DUPLICATE_EMAIL_MESSAGE))
            .when(memberService).updateMemberData(any(Member.class), any(MemberDataChangeVO.class));

        // when
        mockMvc.perform(patch("/members/accountInfo")
                .param("memberNo", "1")
                .param("password", TestHelper.testPw)
                .param("emailAddress", TestHelper.testEmail)
                .contentType(MediaType.APPLICATION_JSON))

            // then
            .andExpect(status().isConflict())
            .andExpect(jsonPath("$.message").value(ExceptionConstants.DUPLICATE_EMAIL_MESSAGE));
    }

     @Test
     void findBlockList() throws Exception {
         // given
         doReturn(TestHelper.createMember()).when(memberRepository).findOne(anyLong());

         // when
         mockMvc.perform(get("/members/block")
                 .param("memberNo", "1")
                 .contentType(MediaType.APPLICATION_JSON))

         // then
         .andExpect(status().isOk())
         .andExpect(jsonPath("$.data").isArray())
         .andExpect(jsonPath("$.message").value(ResponseConstants.SEARCH_SUCCESS_MESSAGE));
     }

     @Test
     void findBlockList_실패() throws Exception {
         // given
         doThrow(new NoMemberFoundException()).when(memberRepository).findOne(anyLong());

         // when
         mockMvc.perform(get("/members/block")
                 .param("memberNo", "1")
                 .contentType(MediaType.APPLICATION_JSON))

         // then
         .andExpect(status().isOk())
         .andExpect(jsonPath("$.data").isArray())
         .andExpect(jsonPath("$.message").value(ResponseConstants.SEARCH_SUCCESS_MESSAGE));
     }

     @Test
     void blockMember() throws Exception {
         // given
         doReturn(TestHelper.createMember()).when(memberRepository).findOne(anyLong());

         // when
         mockMvc.perform(post("/members/block")
                         .param("memberNo", "1")
                         .param("blockMemberNo", "2")
                         .contentType(MediaType.APPLICATION_JSON))

         // then
         .andExpect(status().isOk())
         .andExpect(jsonPath("$.message").value(ResponseConstants.BLOCK_SUCCESS_MESSAGE));
     }

     @Test
     void blockMember_실패() throws Exception {
         // given
         doThrow(new NoMemberFoundException()).when(memberRepository).findOne(anyLong());

         // when
         mockMvc.perform(post("/members/block")
                         .param("memberNo", "1")
                         .param("blockMemberNo", "2")
                         .contentType(MediaType.APPLICATION_JSON))

         // then
         .andExpect(status().isNotFound())
         .andExpect(jsonPath("$.message").value(ExceptionConstants.NO_MEMBER_FOUND_MESSAGE));
     }

     @Test
     void unblockMember() throws Exception {
         // given
         doReturn(TestHelper.createMember()).when(memberRepository).findOne(anyLong());

         // when
         mockMvc.perform(delete("/members/block")
                         .param("memberNo", "1")
                         .param("blockMemberNo", "2")
                         .contentType(MediaType.APPLICATION_JSON))

         // then
         .andExpect(status().isOk())
         .andExpect(jsonPath("$.message").value(ResponseConstants.UNBLOCK_SUCCESS_MESSAGE));
     }

     @Test
     void unblockMember_실패() throws Exception {
         // given
         doThrow(new NoMemberFoundException()).when(memberRepository).findOne(anyLong());

         // when
         mockMvc.perform(delete("/members/block")
                         .param("memberNo", "1")
                         .param("blockMemberNo", "2")
                         .contentType(MediaType.APPLICATION_JSON))

         // then
         .andExpect(status().isNotFound())
         .andExpect(jsonPath("$.message").value(ExceptionConstants.NO_MEMBER_FOUND_MESSAGE));
     }

     @Test
     void getMemberPostList() throws Exception {
         // given
         doReturn(TestHelper.createMember()).when(memberRepository).findOne(anyLong());

         // when
         mockMvc.perform(get("/members/posts")
                         .param("memberNo", "1")
                         .contentType(MediaType.APPLICATION_JSON))

                 // then
                 .andExpect(status().isOk())
                 .andExpect(jsonPath("$.message").value(ResponseConstants.SEARCH_SUCCESS_MESSAGE));
     }

     @Test
     void getMemberPostList_멤버없을때() throws Exception {
         // given
         doReturn(TestHelper.createMember()).when(memberRepository).findOne(anyLong());

         // when
         mockMvc.perform(get("/members/posts")
                         .param("memberNo", "1")
                         .contentType(MediaType.APPLICATION_JSON))

         // then
         .andExpect(status().isOk())
         .andExpect(jsonPath("$.data").isArray())
         .andExpect(jsonPath("$.message").value(ResponseConstants.SEARCH_SUCCESS_MESSAGE));
     }
}