package com.talmo.talboard.domain;

import com.sun.istack.NotNull;
import com.talmo.talboard.domain.vo.MemberJoinVO;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Getter
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberNo;

    @NotNull
    @Embedded
    private LoginInfo loginInfo;

    @NotNull
    private boolean adminYn;

    @NotNull
    private boolean resignYn;

    @NotNull
    private LocalDateTime registDate;

    @OneToMany(mappedBy = "member")
    private List<Post> posts = Collections.synchronizedList(new ArrayList<>());

    @OneToMany(mappedBy = "blockId.member")
    private Set<Block> blockList = Collections.synchronizedSet(new HashSet<>());

    @OneToMany(mappedBy = "blockId.blockedMember")
    private Set<Block> blockedList = Collections.synchronizedSet(new HashSet<>());

    @OneToMany(mappedBy = "member")
    private Set<Likes> likesList = Collections.synchronizedSet(new HashSet<>());

    @OneToMany(mappedBy = "member")
    private List<Notice> notices = Collections.synchronizedList(new ArrayList<>());

    @OneToMany(mappedBy = "member")
    private List<Comment> comments = Collections.synchronizedList(new ArrayList<>());

    @OneToMany(mappedBy = "reportPostId.reportMember")
    private List<Report> reports = Collections.synchronizedList(new ArrayList<>());

    protected Member() {}

    private Member(String id, String password, String emailAddress) throws IllegalArgumentException {
        this.loginInfo = new LoginInfo(id, password, emailAddress);
        this.adminYn = false;
        this.resignYn = false;
        this.registDate = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof Member)) return false;
        return Objects.equals(this.memberNo, ((Member) obj).getMemberNo());
    }

    @Override
    public int hashCode() {
        return Long.hashCode(this.memberNo);
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

    //==???????????? ??????==//
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

    /**
     * ??????????????? ????????? ????????? ?????? ???????????? ??????????????? ??????
     * @param member ??????????????? ????????? ??????
     * @return ?????? ??????
     */
    public boolean isBlockMember(Member member) {
        return blockList.stream()
                .map(Block::getBlockedMember)
                .anyMatch(blockedMember -> blockedMember.equals(member));
    }

    /**
     * ????????? ????????? ????????? HashSet??? ??????
     */
    public Set<Member> getBlockMembers() {
        return this.getBlockList().stream()
                .map(Block::getBlockedMember)
                .collect(Collectors.toSet());
    }

    /**
     * ????????? ????????? ????????? HashSet??? ??????
     */
    public Set<Member> getBlockedMembers() {
        return this.getBlockedList().stream()
                .map(Block::getMember)
                .collect(Collectors.toSet());
    }

    /**
     * ?????? ????????? ?????? ???????????? ???????????? ????????? ??????
     */
    public boolean isReportedPost(Member member) {
        return reports.stream()
                .map(report -> report.getReportMember())
                .anyMatch(reportMember -> reportMember.equals(member));
    }

    /**
     * ?????? ????????? ?????? ???????????? ?????? ??????/????????? ????????? ??????
     */
    public boolean isAlreadyLikedPost(Post post) {
        return likesList.stream().anyMatch(likes -> likes.getPost().equals(post));
    }

}
