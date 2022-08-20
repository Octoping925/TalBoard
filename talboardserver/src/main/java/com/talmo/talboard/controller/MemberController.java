package com.talmo.talboard.controller;

import com.talmo.talboard.config.ResponseObject;
import com.talmo.talboard.domain.vo.MemberVO;
import com.talmo.talboard.service.MemberService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @ApiOperation(value="아이디 찾기", notes = "회원 이메일로 아이디 찾기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "아이디 찾기 성공"),
            @ApiResponse(code = 400, message = "회원 정보 찾지 못함")
    })
    @RequestMapping(value = "/members/find/id", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> findId(MemberVO vo) {
        try {
            String id = memberService.findId(vo.getEmailAddress());
            return ResponseEntity.ok()
                    .body(ResponseObject.create(id, ""));
        }
        catch(IllegalStateException e) {
            return ResponseEntity.badRequest()
                    .body(ResponseObject.create(null, e.getMessage()));
        }
    }

}
