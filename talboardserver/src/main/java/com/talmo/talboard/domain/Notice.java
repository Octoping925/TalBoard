package com.talmo.talboard.domain;

import com.sun.istack.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;
import javax.persistence.*;

@Getter
@Entity
public class Notice {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long noticeNo;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberNo")
    private Member member;

    @NotNull
    @OneToOne
    @JoinColumn(name="commentNo")
    private Comment comment;

    @NotNull
    private boolean readYn;

    @NotNull
    private LocalDateTime createDate;

    //==생성 로직==//
    protected Notice() {}

    private Notice(Member member, Comment comment) {
        this.member = member;
        this.comment = comment;
        this.readYn = false;
        this.createDate = LocalDateTime.now();
    }

    //==비즈니스 로직==//
    public static Notice createNotice(Member member, Comment comment) {
        Notice notice = new Notice(member, comment);
        member.getNotices().add(notice);
        return notice;
    }

    public void read() {
        this.readYn = true;
    }


}