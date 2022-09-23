package com.talmo.talboard.repository;

import com.talmo.talboard.domain.Report;
import com.talmo.talboard.domain.vo.PostReportVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class ReportRepository {
    private final EntityManager em;

    @Transactional
    public void reportPost(Report report) {
        em.persist(report);
    }
}