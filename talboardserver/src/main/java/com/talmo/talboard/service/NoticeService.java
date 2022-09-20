package com.talmo.talboard.service;

import com.talmo.talboard.domain.Notice;
import com.talmo.talboard.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NoticeService {
    private final NoticeRepository noticeRepository;


    public Long create(Notice notice) {
        noticeRepository.save(notice);
        return notice.getNoticeNo();
    }
}
