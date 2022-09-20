package com.talmo.talboard.domain;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentNo")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberNo")
    private Member member;

    @OneToOne(mappedBy = "comment", fetch = FetchType.LAZY)
    private Notice notice;

}
