package com.talmo.talboard.domain;

import com.talmo.talboard.domain.id.ReportPostId;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Report {
    @EmbeddedId
    private ReportPostId reportPost;
    private LocalDateTime reportDate;

    public Report(Member member, Post post) {
        this.reportPost = new ReportPostId(member, post);
        this.reportDate = LocalDateTime.now();
    }
}
