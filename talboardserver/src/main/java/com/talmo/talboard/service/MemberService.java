package com.talmo.talboard.service;

import com.talmo.talboard.domain.Member;
import com.talmo.talboard.exception.NoAuthorizationException;
import com.talmo.talboard.repository.MemberRepository;
import java.util.List;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    /**
     * 회원 정보 찾기
     */
    @Transactional
    public Member findMemberById(String id) throws IllegalStateException {
        List<Member> findMember = memberRepository.findActualMemberById(id);
        if(findMember.isEmpty()) {
            throw new IllegalStateException("회원 정보 찾지 못함");
        }

        return findMember.get(0);
    }

    /**
     * 회원 가입
     */
    @Transactional
    public Long join(Member member) {
        chkDuplicateMember(member);
        memberRepository.save(member);
        return member.getMember_no();
    }

    /**
     * 아이디가 같거나 이메일이 같은 회원이 있는지 체크
     */
    private void chkDuplicateMember(Member member) {
        if(!memberRepository.findActualMemberById(member.getId()).isEmpty()
            || !memberRepository.findActualMemberByEmailAddress(member.getEmailAddress()).isEmpty()) {
            throw new IllegalStateException("이미 존재하는 아이디 또는 이메일");
        }
    }

    /**
     * 회원 탈퇴
     */
    @Transactional
    public void resign(String id, String resign_member_id) {
        Member member = findMemberById(id);
        Member resignMember= findMemberById(resign_member_id);

        if(!member.getMember_no().equals(resignMember.getMember_no())
        && !member.isAdminYn()) {
            throw new NoAuthorizationException("회원 탈퇴");
        }

        resignMember.resign();
    }

    /**
     * 이메일 주소로 아이디 찾기
     */
    @Transactional
    public String findId(String emailAddress) throws IllegalStateException {
        List<Member> findMember = memberRepository.findActualMemberByEmailAddress(emailAddress);
        if(findMember.isEmpty()) {
            throw new IllegalStateException("회원 정보 찾지 못함");
        }
        Member m = findMember.get(0);
        return m.getId();
    }

    /**
     * 아이디로 비밀번호 찾기
     */
    @Transactional
    public String findPassword(String id) {
        // TODO: 해당 멤버의 이메일 주소로 비밀번호 정보 보내는 기능
        List<Member> findMember = memberRepository.findActualMemberById(id);
        if(findMember.isEmpty()) {
            throw new IllegalStateException("회원 정보 찾지 못함");
        }
        Member m = findMember.get(0);
        return m.getPassword();
    }
}
