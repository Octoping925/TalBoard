package com.talmo.talboard.domain;

import javax.persistence.*;

@Entity
public class Notice {
    @Id @GeneratedValue
    @Column(name = "notice_no")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    private Member member;

}
