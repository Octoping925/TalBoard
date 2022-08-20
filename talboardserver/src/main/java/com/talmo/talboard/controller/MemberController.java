package com.talmo.talboard.controller;

import com.talmo.talboard.config.ResponseObject;
import com.talmo.talboard.domain.Member;
import com.talmo.talboard.domain.vo.MemberFindIdVO;
import com.talmo.talboard.domain.vo.MemberFindPasswordVO;
import com.talmo.talboard.domain.vo.MemberJoinVO;
import com.talmo.talboard.domain.vo.MemberResignVO;
import com.talmo.talboard.exception.NoAuthorizationException;
import com.talmo.talboard.service.MemberService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @ApiOperation(value="회원 가입")
    @ApiResponses({
            @ApiResponse(code = 200, message = "회원 가입 성공"),
            @ApiResponse(code = 400, message = "아이디, 비밀번호 유효성 검사 실패"),
            @ApiResponse(code = 409, message = "이미 존재하는 아이디 또는 이메일")
    })
    @PostMapping("/members/regist")
    public ResponseEntity<Map<String, Object>> join(MemberJoinVO vo) {
        try {
            Member member = new Member(vo.getId(), vo.getPassword(), vo.getEmailAddress());
            Long member_no = memberService.join(member);
            return ResponseEntity.ok()
                    .body(ResponseObject.create(member_no, "회원 가입 성공"));
        }
        catch(IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ResponseObject.create(null, e.getMessage()));
        }
        catch(IllegalStateException e) {
            return new ResponseEntity<>(ResponseObject.create(null, e.getMessage()), HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value="회원 탈퇴")
    @ApiResponses({
            @ApiResponse(code = 200, message = "회원 탈퇴 성공"),
            @ApiResponse(code = 400, message = "회원 정보 찾지 못함"),
            @ApiResponse(code = 403, message = "회원 탈퇴 권한 없음")
    })
    @DeleteMapping("/members/resign")
    public ResponseEntity<Map<String, Object>> resign(MemberResignVO vo) {
        try {
            memberService.resign(vo.getId(), vo.getResign_member_id());
            return ResponseEntity.ok()
                    .body(ResponseObject.create(null, "회원 탈퇴 성공"));
        }
        catch(IllegalStateException e) {
            return ResponseEntity.badRequest()
                    .body(ResponseObject.create(null, e.getMessage()));
        }
        catch(NoAuthorizationException e) {
            return new ResponseEntity<>(ResponseObject.create(null, e.getMessage()), HttpStatus.FORBIDDEN);
        }
    }



    @ApiOperation(value="아이디 찾기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "아이디 찾기 성공"),
            @ApiResponse(code = 400, message = "회원 정보 찾지 못함")
    })
    @GetMapping("/members/find/id")
    public ResponseEntity<Map<String, Object>> findId(MemberFindIdVO vo) {
        try {
            String id = memberService.findId(vo.getEmailAddress());
            return ResponseEntity.ok()
                    .body(ResponseObject.create(id, "아이디 찾기 성공"));
        }
        catch(IllegalStateException e) {
            return ResponseEntity.badRequest()
                    .body(ResponseObject.create(null, e.getMessage()));
        }
    }

    @ApiOperation(value="비밀번호 찾기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "비밀번호 찾기 성공"),
            @ApiResponse(code = 400, message = "회원 정보 찾지 못함")
    })
    @GetMapping("/members/find/password")
    public ResponseEntity<Map<String, Object>> findPassword(MemberFindPasswordVO vo) {
        try {
            String id = memberService.findPassword(vo.getId());
            return ResponseEntity.ok()
                    .body(ResponseObject.create(id, "비밀번호 찾기 성공"));
        }
        catch(IllegalStateException e) {
            return ResponseEntity.badRequest()
                    .body(ResponseObject.create(null, e.getMessage()));
        }
    }


}
