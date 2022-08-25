package com.talmo.talboard.domain;

import com.sun.istack.NotNull;
import com.talmo.talboard.domain.vo.MemberJoinVO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    private LoginInfo loginInfo;

    @NotNull
    private boolean adminYn;

    @NotNull
    private boolean resignYn;

    @NotNull
    private LocalDateTime registDate;

    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();

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

    private Member(String id, String password, String emailAddress) throws IllegalArgumentException {
        this.loginInfo = new LoginInfo(id, password, emailAddress);
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

    public String getId() {
        return this.loginInfo.getId();
    }

    public String getPassword() {
        return this.loginInfo.getPassword();
    }

    public String getEmailAddress() {
        return this.loginInfo.getEmailAddress();
    }

    //==비즈니스 로직==//
    public static Member regist(MemberJoinVO vo) throws IllegalArgumentException {
        return new Member(vo.getId(), vo.getPassword(), vo.getEmailAddress());
    }

    public void resign() {
        this.resignYn = true;
        this.adminYn = false;
    }

    public void changePassword(String password) throws IllegalArgumentException {
        this.loginInfo = new LoginInfo(this.getId(), password, this.getEmailAddress());
    }

    public void changeEmailAddress(String emailAddress) throws IllegalArgumentException {
        this.loginInfo = new LoginInfo(this.getId(), this.getPassword(), emailAddress);
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
