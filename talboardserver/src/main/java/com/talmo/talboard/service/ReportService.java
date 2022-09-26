package com.talmo.talboard.service;

import com.talmo.talboard.domain.Member;
import com.talmo.talboard.domain.Post;
import com.talmo.talboard.domain.Report;
import com.talmo.talboard.domain.id.ReportPostId;
import com.talmo.talboard.domain.vo.PostReportVO;
import com.talmo.talboard.exception.PostReportException;
import com.talmo.talboard.repository.MemberRepository;
import com.talmo.talboard.repository.PostRepository;
import com.talmo.talboard.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;

    public void reportPost(Member member, Post reportPost) {
        boolean isAlreadyReport = member.isReportedPost(member);

        if(!isAlreadyReport) {
            Report report = new Report(member, reportPost);
            reportRepository.reportPost(report);
        } else {
            throw new PostReportException();
        }
    }
}