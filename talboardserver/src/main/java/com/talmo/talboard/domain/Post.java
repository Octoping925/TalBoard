package com.talmo.talboard.domain;

import com.sun.istack.NotNull;
import com.talmo.talboard.domain.vo.PostCreateVO;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Post {
    @Id @GeneratedValue
    private Long post_no;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    private Member member;

    @NotNull
    private String title;

    @NotNull
    private String context;

    @NotNull
    private String delete_yn;

    @NotNull
    private LocalDateTime create_date;

    private Post(Member member, String title, String context) {
        this.member = member;
        this.title = title;
        this.context = context;
        this.delete_yn = "N";
        this.create_date = LocalDateTime.now();
    }

    public static Post create(PostCreateVO vo, Member member) {
        return new Post(member, vo.getTitle(), vo.getContext());
    }

}
