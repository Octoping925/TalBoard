package com.talmo.talboard.service;

import com.talmo.talboard.domain.Member;
import com.talmo.talboard.domain.Post;
import com.talmo.talboard.domain.Report;
import com.talmo.talboard.domain.id.ReportPostId;
import com.talmo.talboard.domain.vo.PostReportVO;
import com.talmo.talboard.repository.MemberRepository;
import com.talmo.talboard.repository.PostRepository;
import com.talmo.talboard.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public void reportPost(PostReportVO vo) {
        //boolean isAlreadyBlocked = vo.
        Post post = postRepository.findOne(vo.getPostNo());
        Member member = memberRepository.findOne(vo.getMemberNo());

        Report report = new Report(member, post);
        reportRepository.reportPost(report);
    }
}