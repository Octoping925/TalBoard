package com.talmo.talboard.domain;

import com.sun.istack.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Getter;

@Entity
@Getter
public class Member {
    @Id @GeneratedValue
    private Long member_no;

    @NotNull
    private String id;

    @NotNull
    private String password;

    @NotNull
    private String emailAddress;

    @NotNull
    private boolean adminYn;

    @NotNull
    private boolean resignYn;

    @NotNull
    private LocalDateTime registDate;

//    @OneToMany(mappedBy = "member")
//    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "blockId.member")
    private List<Block> blocks = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Notice> notices = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();

    protected Member() {
        this.resignYn = false;
    }

    public Member(String id, String password, String emailAddress) {
        if(!isValidId(id)
        || !isValidPassword(password)
        || !isValidEmailAddress(emailAddress)) {
            throw new IllegalArgumentException("아이디, 비밀번호 유효성 검사 실패");
        }

        this.id = id;
        this.password = password;
        this.emailAddress = emailAddress;
        this.adminYn = false;
        this.resignYn = false;
        this.registDate = LocalDateTime.now();
    }

    private boolean isValidId(String id) {
        if(id.contains(" ")) return false;
        if(id.length() < 6) return false;
        return true;
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 6 && !password.contains(" ");
    }

    private boolean isValidEmailAddress(String emailAddress) {
        if(!emailAddress.contains("@")) return false;
        if(emailAddress.contains(" ")) return false;

        String[] domain = emailAddress.split("@");
        if(domain.length < 2) return false;

        return true;
    }

    //==비즈니스 로직==//
    public void resign() {
        this.resignYn = true;
    }

    public void changePassword(String password) {
        if(!isValidPassword(password)) {
            throw new IllegalArgumentException("비밀번호 유효성 검사 실패");
        }
        this.password = password;
    }

    public void changeEmailAddress(String emailAddress) {
        if(!isValidEmailAddress(emailAddress)) {
            throw new IllegalArgumentException("이메일 유효성 검사 실패");
        }
        this.emailAddress = emailAddress;
    }

    public void setAdminYn(boolean status) {
        if(!resignYn) {
            this.adminYn = status;
        }
    }



}
