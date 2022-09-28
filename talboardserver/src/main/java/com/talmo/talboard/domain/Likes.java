package com.talmo.talboard.domain;

import com.sun.istack.NotNull;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.Getter;

@Entity
@Getter
public class Likes {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postNo")
    private Post post;

    @NotNull
    private boolean likeYn;

    @NotNull
    private LocalDateTime likeDate;

    protected Likes() {}

    private Likes(Member member, Post post, boolean likeYn) {
        this.member = member;
        this.post = post;
        this.likeYn = likeYn;
        this.likeDate = LocalDateTime.now();
    }

    public static Likes likePost(Member member, Post post) {
        return new Likes(member, post, true);
    }

    public static Likes dislikePost(Member member, Post post) {
        return new Likes(member, post, false);
    }
}
