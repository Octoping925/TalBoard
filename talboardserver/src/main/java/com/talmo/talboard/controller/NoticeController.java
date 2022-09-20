package com.talmo.talboard.controller;

import com.talmo.talboard.config.ExceptionConstants;
import com.talmo.talboard.config.ResponseConstants;
import com.talmo.talboard.config.ResponseObject;
import com.talmo.talboard.domain.Member;
import com.talmo.talboard.domain.Notice;
import com.talmo.talboard.domain.vo.MemberJoinVO;
import com.talmo.talboard.domain.vo.NoticeInfoVO;
import com.talmo.talboard.exception.NoMemberFoundException;
import com.talmo.talboard.repository.MemberRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NoticeController {
    private final MemberRepository memberRepository;

    @ApiOperation(value="회원 가입")
    @ApiResponses({
        @ApiResponse(code = 200, message = "회원 가입 성공"),
        @ApiResponse(code = 400, message = "아이디, 비밀번호 유효성 검사 실패"),
        @ApiResponse(code = 409, message = "이미 존재하는 아이디 또는 이메일")
    })
    @GetMapping("/notice/{memberNo}")
    public ResponseEntity<Map<String, Object>> join(@PathVariable Long memberNo) {
        try {
//            Notice notice = noticeRepository.findNoticeByMember
//            Member member = memberRepository.findOne(memberNo);
//            List<Notice> notices = member.getNotices().stream()
//                .map(notice -> new NoticeInfoVO);

            return ResponseEntity.ok()
                .body(ResponseObject.create(memberNo, ResponseConstants.REGIST_SUCCESS_MESSAGE));
        }
        catch(NoMemberFoundException e) {
            return new ResponseEntity<>(ResponseObject.create(ExceptionConstants.NO_MEMBER_FOUND_MESSAGE, e.getMessage()),
                HttpStatus.NOT_FOUND);
        }
    }

}
