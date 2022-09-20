package com.talmo.talboard.controller;

import com.talmo.talboard.config.ConfigConstants;
import com.talmo.talboard.config.ExceptionConstants;
import com.talmo.talboard.config.ResponseConstants;
import com.talmo.talboard.config.ResponseObject;
import com.talmo.talboard.domain.Member;
import com.talmo.talboard.domain.Notice;
import com.talmo.talboard.domain.vo.MemberInfoVO;
import com.talmo.talboard.domain.vo.MemberJoinVO;
import com.talmo.talboard.domain.vo.MemberNoVO;
import com.talmo.talboard.domain.vo.NoticeInfoVO;
import com.talmo.talboard.exception.NoMemberFoundException;
import com.talmo.talboard.exception.NoNoticeFoundException;
import com.talmo.talboard.repository.MemberRepository;
import com.talmo.talboard.repository.NoticeRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private final NoticeRepository noticeRepository;

    @ApiOperation(value="알림 목록 조회")
    @ApiResponses({
        @ApiResponse(code = 200, message = "조회 성공"),
    })
    @GetMapping("/notice")
    public ResponseEntity<Map<String, Object>> join(MemberNoVO vo) {
        List<NoticeInfoVO> noticeInfo = new ArrayList<>();
        try {
            Member member = memberRepository.findOne(vo.getMemberNo());
//            member.getNotices().stream()
//                    .limit(ConfigConstants.NOTICE_LIMIT_CNT)
//                    .map(NoticeInfoVO::new)
//                    .forEach(noticeInfo::add);

            return ResponseEntity.ok()
                .body(ResponseObject.create(noticeInfo, ResponseConstants.SEARCH_SUCCESS_MESSAGE));
        }
        catch(NoMemberFoundException e) {
            return new ResponseEntity<>(ResponseObject.create(noticeInfo, e.getMessage()),
                HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value="알림 조회 체크")
    @ApiResponses({
            @ApiResponse(code = 200, message = "변경 성공"),
    })
    @PostMapping("/notice/{noticeNo}")
    public ResponseEntity<Map<String, Object>> join(@PathVariable Long noticeNo) {
        try {
            Notice notice = noticeRepository.findOne(noticeNo);
            notice.read();
            return ResponseEntity.ok()
                    .body(ResponseObject.create(null, ResponseConstants.CHANGE_SUCCESS_MESSAGE));
        }
        catch(NoNoticeFoundException e) {
            return new ResponseEntity<>(ResponseObject.create(null, e.getMessage()),
                    HttpStatus.NOT_FOUND);
        }
    }

}
