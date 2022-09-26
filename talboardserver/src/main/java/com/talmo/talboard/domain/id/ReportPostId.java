package com.talmo.talboard.domain.id;

import com.talmo.talboard.domain.Member;
import com.talmo.talboard.domain.Post;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class ReportPostId implements Serializable {
    @ManyToOne(fetch = FetchType.LAZY)
    Member reportMember;
    @ManyToOne(fetch = FetchType.LAZY)
    Post reportedPost;

    protected ReportPostId() {}

    public ReportPostId(Member reportMember, Post reportedPost) {
        this.reportMember = reportMember;
        this.reportedPost = reportedPost;
    }

}