package com.talmo.talboard.integration;

import static org.junit.jupiter.api.Assertions.*;

import com.talmo.talboard.config.TestHelper;
import com.talmo.talboard.controller.NoticeController;
import com.talmo.talboard.domain.Member;
import com.talmo.talboard.domain.Notice;
import com.talmo.talboard.domain.Post;
import com.talmo.talboard.repository.NoticeRepository;
import com.talmo.talboard.service.NoticeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class NoticeIntegrationTest {
    @Autowired NoticeService noticeService;
    @Autowired NoticeRepository noticeRepository;

//    @Test
//    void read() {
//        // given
//        Member member1 = TestHelper.createMember(1);
//        Member member2 = TestHelper.createMember(2);
//        Post post = TestHelper.createPost(member1);
//
//        // TODO: 댓글 기능 완성되면, createNotice가 아니라 member2가 댓글 달기를 통해 Notice를 생성하도록 로직 수정
//        Notice notice = Notice.createNotice(post, member2);
//        Notice notice2 = Notice.createNotice(post, member2);
//        noticeService.create(notice);
//        noticeService.create(notice2);
//
//        // when
//        Notice findNotice1 = noticeRepository.findOne(notice.getNoticeNo());
//        Notice findNotice2 = noticeRepository.findOne(notice2.getNoticeNo());
//        findNotice1.read();
//
//        // then
//        assertEquals(2, member1.getNotices().size());
//
//        assertTrue(findNotice1.isReadYn());
//        assertTrue(notice.isReadYn());
//        assertTrue(member1.getNotices().get(0).isReadYn());
//
//        assertFalse(findNotice2.isReadYn());
//        assertFalse(member1.getNotices().get(1).isReadYn());
//        assertFalse(notice2.isReadYn());
//    }
}

