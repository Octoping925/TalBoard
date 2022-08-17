package com.talmo.talboard.service;

import com.talmo.talboard.domain.Member;
import com.talmo.talboard.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

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
        if(memberRepository.findActualMemberById(member.getId()) == null
            || memberRepository.findActualMemberByEmailAddress(member.getEmailAddress()) == null) {
            throw new IllegalStateException("이미 존재하는 회원입니다");
        }
    }

    /**
     * 회원 탈퇴
     */
    @Transactional
    public void resign(Long member_no) {
        Member m = memberRepository.findOne(member_no);
        m.resign();
    }

    /**
     * 이메일 주소로 아이디 찾기
     */
    @Transactional
    public String findId(String emailAddress) {
        Member m = memberRepository.findActualMemberByEmailAddress(emailAddress);
        return m.getId();
    }

    /**
     * 아이디로 비밀번호 찾기
     */
    @Transactional
    public String findPassword(String id) {
        // TODO: 해당 멤버의 이메일 주소로 비밀번호 정보 보내는 기능
        Member m = memberRepository.findActualMemberById(id);
        return m.getPassword();
    }
}
