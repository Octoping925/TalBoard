package com.talmo.talboard.service;

import com.talmo.talboard.domain.Block;
import com.talmo.talboard.domain.Member;
import com.talmo.talboard.domain.vo.MemberDataChangeVO;
import com.talmo.talboard.exception.ExceptionConstants;
import com.talmo.talboard.exception.NoAuthorizationException;
import com.talmo.talboard.exception.NoMemberFoundException;
import com.talmo.talboard.repository.BlockRepository;
import com.talmo.talboard.repository.MemberRepository;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final BlockService blockService;
    private final MemberRepository memberRepository;
    private final BlockRepository blockRepository;

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
        if(memberRepository.chkExistsActualMemberById(member.getId())) {
            throw new IllegalStateException(ExceptionConstants.DUPLICATE_ID_MESSAGE);
        }
        if(memberRepository.chkExistsActualMemberByEmailAddress(member.getEmailAddress())) {
            throw new IllegalStateException(ExceptionConstants.DUPLICATE_EMAIL_MESSAGE);
        }
    }

    /**
     * 회원 탈퇴
     */
    @Transactional
    public void resign(Long id, Long resign_member_id) throws NoMemberFoundException {
        Member member = memberRepository.findOne(id);
        Member resignMember = memberRepository.findOne(resign_member_id);

        if(!member.equals(resignMember)
        && !member.isAdminYn()) {
            throw new NoAuthorizationException();
        }

        blockService.cleanMember(resignMember);
        resignMember.resign();
    }

    /**
     * 이메일 주소로 아이디 찾기
     */
    @Transactional
    public String findId(String emailAddress) throws NoMemberFoundException {
        return memberRepository.findOneActualMemberByEmailAddress(emailAddress).getId();
    }

    /**
     * 아이디로 비밀번호 찾기
     */
    @Transactional
    public String findPassword(String id) throws NoMemberFoundException {
        // TODO: 해당 멤버의 이메일 주소로 비밀번호 정보 보내는 기능
        return memberRepository.findOneActualMemberById(id).getPassword();
    }

    /**
     * 회원 정보 변경
     */
    @Transactional
    public void updateMemberData(Member member, MemberDataChangeVO vo) throws IllegalArgumentException {
        if(vo.getPassword() != null) {
            member.changePassword(vo.getPassword());
        }
        if(vo.getEmailAddress() != null) {
            if(memberRepository.chkExistsActualMemberByEmailAddress(vo.getEmailAddress())) {
                throw new IllegalStateException(ExceptionConstants.DUPLICATE_EMAIL_MESSAGE);
            }
            member.changeEmailAddress(vo.getEmailAddress());
        }
    }

    /**
     * 회원 차단 목록 조회
     */
    public List<Member> findMemberBlockList(Long member_no) throws NoMemberFoundException {
        Member member = memberRepository.findOne(member_no);
        return blockRepository.findMemberBlockList(member.getMember_no()).stream()
                .map(Block::getBlockedMember)
                .collect(Collectors.toList());
    }
}
