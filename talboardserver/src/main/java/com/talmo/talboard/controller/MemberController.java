package com.talmo.talboard.controller;

import com.talmo.talboard.config.ResponseObject;
import com.talmo.talboard.domain.Block;
import com.talmo.talboard.domain.Member;
import com.talmo.talboard.domain.Post;
import com.talmo.talboard.domain.vo.*;
import com.talmo.talboard.exception.NoAuthorizationException;
import com.talmo.talboard.exception.NoMemberFoundException;
import com.talmo.talboard.repository.MemberRepository;
import com.talmo.talboard.service.BlockService;
import com.talmo.talboard.service.MemberService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.ArrayList;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final BlockService blockService;
    private final MemberRepository memberRepository;

//    @ApiOperation(value="로그인")
//    @ApiResponses({
//            @ApiResponse(code = 200, message = "로그인 성공"),
//            @ApiResponse(code = 400, message = "로그인 실패")
//    })
//    @PostMapping("/members/login")
//    public ResponseEntity<Map<String, Object>> login(Object vo) {
//            return ResponseEntity.ok()
//                    .body(ResponseObject.create(null, "로그인 성공"));
//    }
//
//    @ApiOperation(value="로그아웃")
//    @ApiResponses({
//            @ApiResponse(code = 200, message = "로그아웃 성공"),
//            @ApiResponse(code = 400, message = "로그아웃 실패")
//    })
//    @GetMapping("/members/logout")
//    public ResponseEntity<Map<String, Object>> logout(Object vo) {
//        return ResponseEntity.ok()
//                .body(ResponseObject.create(null, "로그아웃 성공"));
//    }

    @ApiOperation(value="회원 가입")
    @ApiResponses({
            @ApiResponse(code = 200, message = "회원 가입 성공"),
            @ApiResponse(code = 400, message = "아이디, 비밀번호 유효성 검사 실패"),
            @ApiResponse(code = 409, message = "이미 존재하는 아이디 또는 이메일")
    })
    @PostMapping("/members/regist")
    public ResponseEntity<Map<String, Object>> join(MemberJoinVO vo) {
        try {
            Member member = Member.regist(vo);
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
            @ApiResponse(code = 403, message = "회원 탈퇴 권한 없음"),
            @ApiResponse(code = 404, message = "회원 정보 찾지 못함")
    })
    @DeleteMapping("/members/resign")
    public ResponseEntity<Map<String, Object>> resign(MemberResignVO vo) {
        try {
            memberService.resign(vo.getMember_no(), vo.getResign_member_no());
            return ResponseEntity.ok()
                    .body(ResponseObject.create(null, "회원 탈퇴 성공"));
        }
        catch(NoAuthorizationException e) {
            return new ResponseEntity<>(ResponseObject.create(null, e.getMessage()), HttpStatus.FORBIDDEN);
        }
        catch(NoMemberFoundException e) {
            return new ResponseEntity<>(ResponseObject.create(null, e.getMessage()), HttpStatus.NOT_FOUND);
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
        catch(NoMemberFoundException e) {
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
        catch(NoMemberFoundException e) {
            return ResponseEntity.badRequest()
                    .body(ResponseObject.create(null, e.getMessage()));
        }
    }

    @ApiOperation(value="계정 정보 변경")
    @ApiResponses({
            @ApiResponse(code = 200, message = "변경 성공"),
            @ApiResponse(code = 400, message = "비밀번호 또는 메일 유효성 검사 실패"),
            @ApiResponse(code = 404, message = "회원 정보 찾지 못함"),
            @ApiResponse(code = 409, message = "동일한 이메일 존재")
    })
    @PatchMapping("/members/accountInfo")
    public ResponseEntity<Map<String, Object>> changeAccountInfo(MemberDataChangeVO vo) {
        try {
            Member member = memberRepository.findOne(vo.getMember_no());
            memberService.updateMemberData(member, vo);
            return ResponseEntity.ok()
                    .body(ResponseObject.create(null, "변경 성공"));
        }
        catch(IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ResponseObject.create(null, e.getMessage()));
        }
        catch(NoMemberFoundException e) {
            return new ResponseEntity<>(ResponseObject.create(null, e.getMessage()), HttpStatus.NOT_FOUND);
        }
        catch(IllegalStateException e) {
            return new ResponseEntity<>(ResponseObject.create(null, e.getMessage()), HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value="사용자가 차단한 사용자들의 정보 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회 성공")
    })
    @GetMapping("/members/block")
    public ResponseEntity<Map<String, Object>> findBlockList(MemberFindBlockListVO vo) {
        List<MemberInfoVO> blockList;
        try {
            Member member = memberRepository.findOne(vo.getMember_no());
            blockList = member.getBlockList().stream()
                .map(Block::getBlockedMember)
                .map(MemberInfoVO::new)
                .collect(Collectors.toList());
        }
        catch(NoMemberFoundException e) {
            blockList = new ArrayList<>();
        }

        return ResponseEntity.ok()
            .body(ResponseObject.create(blockList, "조회 성공"));
    }

    @ApiOperation(value="사용자 차단")
    @ApiResponses({
            @ApiResponse(code = 200, message = "차단 성공"),
            @ApiResponse(code = 404, message = "회원 정보 찾지 못함"),
    })
    @PostMapping("/members/block")
    public ResponseEntity<Map<String, Object>> blockMember(MemberBlockVO vo) {
        try {
            Member member = memberRepository.findOne(vo.getMember_no());
            Member blockMember = memberRepository.findOne(vo.getBlocked_member_no());
            blockService.blockMember(member, blockMember);
            return ResponseEntity.ok()
                    .body(ResponseObject.create(null, "차단 성공"));
        }
        catch(NoMemberFoundException e) {
            return new ResponseEntity<>(ResponseObject.create(null, e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value="사용자 차단 해제")
    @ApiResponses({
            @ApiResponse(code = 200, message = "차단 해제 성공"),
            @ApiResponse(code = 404, message = "회원 정보 찾지 못함"),
    })
    @DeleteMapping("/members/block")
    public ResponseEntity<Map<String, Object>> unblockMember(MemberBlockVO vo) {
        try {
            Member member = memberRepository.findOne(vo.getMember_no());
            Member blockMember = memberRepository.findOne(vo.getBlocked_member_no());

            blockService.unblockMember(member, blockMember);
            return ResponseEntity.ok()
                    .body(ResponseObject.create(null, "차단 해제 성공"));
        }
        catch(NoMemberFoundException e) {
            return new ResponseEntity<>(ResponseObject.create(null, e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value="사용자 작성 게시글 조회")
    @ApiResponses({
        @ApiResponse(code = 200, message = "조회 성공"),
    })
    @GetMapping("/members/posts")
    public ResponseEntity<Map<String, Object>> getMemberPostList(MemberNoVO vo) {
        try {
            Member member = memberRepository.findOne(vo.getMember_no());
            List<Post> postList = member.getPosts();

            List<PostInfoVO> postListVO = postList.stream()
                .map(post -> new PostInfoVO(member.getMember_no(), post))
                .collect(Collectors.toList());

            return ResponseEntity.ok()
                .body(ResponseObject.create(postListVO, "조회 성공"));
        }
        catch(NoMemberFoundException e) {
            return ResponseEntity.ok()
                .body(ResponseObject.create(new ArrayList<>(), "조회 성공"));
        }
    }



}
