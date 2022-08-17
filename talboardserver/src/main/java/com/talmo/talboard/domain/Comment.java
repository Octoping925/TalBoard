package com.talmo.talboard.domain;

import javax.persistence.*;

@Entity
public class Comment {
    @Id @GeneratedValue
    @Column(name = "comment_no")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    private Member member;

}
