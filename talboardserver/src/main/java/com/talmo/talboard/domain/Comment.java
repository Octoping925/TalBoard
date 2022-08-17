package com.talmo.talboard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Comment {
    @Id @GeneratedValue
    @Column(name = "comment_no")
    private Long id;

}
