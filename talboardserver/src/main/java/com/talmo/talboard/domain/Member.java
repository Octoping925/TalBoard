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
        if(obj == null) return false;
        if(getClass() == obj.getClass()) {
            return Objects.equals(this.memberNo, ((Member) obj).getMemberNo());
        }
        return false;
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

    /**
     * 매개변수로 주어진 회원이 차단 리스트에 포함되는지 확인
     * @param member 차단됐는지 확인할 회원
     * @return 차단 여부
     */
    public boolean isBlockMember(Member member) {
        return blockList.stream()
                .map(Block::getBlockedMember)
                .anyMatch(blockedMember -> blockedMember.equals(member));
    }

    /**
     * 자신이 차단한 회원의 HashSet을 반환
     */
    public Set<Member> getBlockMembers() {
        return this.getBlockList().stream()
                .map(Block::getBlockedMember)
                .collect(Collectors.toSet());
    }

    /**
     * 자신을 차단한 회원의 HashSet을 반환
     */
    public Set<Member> getBlockedMembers() {
        return this.getBlockedList().stream()
                .map(Block::getMember)
                .collect(Collectors.toSet());
    }

    /**
     * 해당 멤버가 해당 포스트를 신고한적 있는지 확인
     */
    public boolean isReportedPost(Member member) {
        return reports.stream()
                .map(report -> report.getReportMember())
                .anyMatch(reportMember -> reportMember.equals(member));
    }

    /**
     * 해당 멤버가 해당 포스트를 이미 추천/비추천 했는지 확인
     */
    public boolean isAlreadyLikedPost(Post post) {
        return likesList.stream().anyMatch(likes -> likes.getPost().equals(post));
    }

}
