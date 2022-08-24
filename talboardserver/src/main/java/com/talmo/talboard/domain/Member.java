package com.talmo.talboard.domain;

import com.sun.istack.NotNull;
import com.talmo.talboard.domain.vo.MemberJoinVO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    private List<Block> blockList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Notice> notices = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();

    protected Member() {
        this.adminYn = false;
        this.resignYn = false;
        this.registDate = LocalDateTime.now();
    }

    private Member(String id, String password, String emailAddress) {
        this.id = id;
        this.password = password;
        this.emailAddress = emailAddress;
        this.adminYn = false;
        this.resignYn = false;
        this.registDate = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Member) {
            return Objects.equals(this.member_no, ((Member) obj).getMember_no());
        }
        return false;
    }

    private static boolean isValidId(String id) {
        if(id.contains(" ")) return false;
        if(id.length() < 6) return false;
        return true;
    }

    private static boolean isValidPassword(String password) {
        return password.length() >= 6 && !password.contains(" ");
    }

    private static boolean isValidEmailAddress(String emailAddress) {
        String regx = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(emailAddress);
        return matcher.matches();
    }

    //==비즈니스 로직==//
    public static Member regist(MemberJoinVO vo) {
        if(!isValidId(vo.getId())
            || !isValidPassword(vo.getPassword())
            || !isValidEmailAddress(vo.getEmailAddress())) {
            throw new IllegalArgumentException("아이디, 비밀번호 유효성 검사 실패");
        }

        return new Member(vo.getId(), vo.getPassword(), vo.getEmailAddress());
    }

    public void resign() {
        this.resignYn = true;
        this.adminYn = false;
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

    public boolean isBlockMember(Member member) {
        return blockList.stream().map(Block::getBlockedMember)
            .anyMatch(blockedMember -> blockedMember.equals(member));
    }

    public void cleanBlockList() {
        this.blockList.clear();
    }

}
