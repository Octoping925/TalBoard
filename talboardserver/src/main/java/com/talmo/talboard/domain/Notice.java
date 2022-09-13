package com.talmo.talboard.domain;

import com.sun.istack.NotNull;
import java.time.LocalDateTime;
import javax.persistence.*;

@Entity
public class Notice {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_no")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    private Member member;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postNo")
    private Post post;

    @NotNull
    private boolean readYn;

    @NotNull
    private LocalDateTime createDate;

    //==생성 로직==//
    protected Notice() {}

    private Notice(Member member, Post post) {
        this.member = member;
        this.post = post;
        this.readYn = false;
        this.createDate = LocalDateTime.now();
    }

    //==비즈니스 로직==//
    public static Notice createNotice(Member member, Post post) {
        Notice notice = new Notice(member, post);
        member.getNotices().add(notice);
        return notice;
    }

    public void read() {
        this.readYn = true;
        this.member.getNotices().remove(this);
    }


}
