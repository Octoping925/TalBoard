package com.talmo.talboard.config;

import com.talmo.talboard.domain.Member;
import com.talmo.talboard.domain.Post;
import com.talmo.talboard.domain.vo.MemberJoinVO;
import com.talmo.talboard.domain.vo.PostCreateVO;

public class TestHelper {
    public static String testId = "ididid";
    public static String testId2 = "ididid2";
    public static String testPw = "pwpwpw";
    public static String testPw2 = "pwpwpw2";
    public static String testEmail = "test@test.com";
    public static String testEmail2 = "test2@test.com";
    public static String testEmail3 = "test3@test.com";

    public static String failPw = "pw";
    public static String failPw2 = "pwpwpw pw";
    public static String failEmail = "test";
    public static String failEmail2 = "test@";
    public static String failEmail3 = "@test.com";

    public static String loremIpsumEng = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum";
    public static String loremIpsumKor = "계절이 지나가는 하늘에는 가을로 가득 차 있습니다. 나는 아무 걱정도 없이 가을 속의 별들을 다 헤일 듯합니다... 가슴 속에 하나 둘 새겨지는 별을 이제 다 못 헤는 것은 쉬이 아침이 오는 까닭이요, 내일 밤이 남은 까닭이요, 아직 나의 청춘이 다하지 않은 까닭입니다.  별 하나에 추억과 별 하나에 사랑과 별 하나에 쓸쓸함과 별 하나에 동경과 별 하나에 시와 별 하나에 어머니, 어머니 어머님, 나는 별 하나에 아름다운 말 한 마디씩 불러 봅니다. 소학교 때 책상을 같이했던 아이들의 이름과, 패, 경, 옥 이런 이국 소녀들의 이름과, 벌써 아기 어머니 된 계집애들의 이름과, 가난한 이웃 사람들의 이름과, 비둘기, 강아지, 토끼, 노새, 노루, '프랑시스 잠', '라이너 마리아 릴케', 이런 시인의 이름을 불러 봅니다. 이네들은 너무나 멀리 있습니다. 별이 아스라이 멀 듯이, 어머님, 그리고 당신은 멀리 북간도에 계십니다 나는 무엇인지 그리워 이 많은 별빛이 내린 언덕 위에 내 이름자를 써 보고, 흙으로 덮어 버리었읍니다. 딴은, 밤을 새워 우는 벌레는 부끄러운 이름을 슬퍼하는 까닭입니다. 그러나 겨울이 지나고 나의 별에도 봄이 오면 무덤 위에 파란 잔디가 피어나듯이 내 이름자 묻힌 언덕 위에도 자랑처럼 풀이 무성할 게외다.";

    public static String getLoremIpsumEng(int length) {
        return loremIpsumEng.substring(0, length);
    }

    public static String getLoremIpsumKor(int length) {
        return loremIpsumKor.substring(0, length);
    }

    public static Member createMember() {
        MemberJoinVO vo = new MemberJoinVO(testId, testPw, testEmail);
        return Member.regist(vo);
    }
    public static Member createMember(int number) {
        MemberJoinVO vo = new MemberJoinVO(testId + number, testPw + number, testEmail + number);
        return Member.regist(vo);
    }
    public static Member createMember(String id, String password, String emailAddress) {
        MemberJoinVO vo = new MemberJoinVO(id, password, emailAddress);
        return Member.regist(vo);
    }

    public static Post createPost(Member member) {
        PostCreateVO vo = new PostCreateVO(getLoremIpsumKor(27), getLoremIpsumKor(64));
        return Post.create(vo, member);
    }

    public static Post createPost(Member member, int titleLength, int contextLength) {
        PostCreateVO vo = new PostCreateVO(getLoremIpsumKor(titleLength), getLoremIpsumKor(contextLength));
        return Post.create(vo, member);
    }
}
