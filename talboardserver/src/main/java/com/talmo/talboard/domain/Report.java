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
    private ReportPostId reportPostId;
    private LocalDateTime reportDate;

    public Report(Member member, Post post) {
        this.reportPostId = new ReportPostId(member, post);
        this.reportDate = LocalDateTime.now();
    }
}
