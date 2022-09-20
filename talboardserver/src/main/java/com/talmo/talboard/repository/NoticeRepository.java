package com.talmo.talboard.repository;

import com.talmo.talboard.domain.Notice;
import com.talmo.talboard.exception.NoNoticeFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class NoticeRepository {
    private final EntityManager em;

    @Transactional
    public void save(Notice notice) {
        if(notice.getNoticeNo() == null) {
            em.persist(notice);
        }
        else {
            em.merge(notice);
        }
    }

    @Transactional
    public Notice findOne(Long noticeNo) {
        Notice notice = em.find(Notice.class, noticeNo);
        if(notice == null) throw new NoNoticeFoundException();
        return notice;
    }

}
