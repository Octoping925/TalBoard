package com.talmo.talboard.controller;

import com.talmo.talboard.domain.Member;
import com.talmo.talboard.domain.Post;
import com.talmo.talboard.domain.vo.MemberJoinVO;
import com.talmo.talboard.domain.vo.PostCreateVO;
import com.talmo.talboard.service.BlockService;
import com.talmo.talboard.service.MemberService;
import com.talmo.talboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class TestController {
    private final MemberService memberService;
    private final BlockService blockService;
    private final PostService postService;

    @GetMapping("/createTestData")
    public void createTestData() {
        /* Create Test Member */
        Member member = Member.regist(new MemberJoinVO("octoping925", "password", "test@naver.com"));
        Member member2 = Member.regist(new MemberJoinVO("faker123", "playlol", "mid123@lol.com"));
        Member member3 = Member.regist(new MemberJoinVO("leesang1", "ls12345", "ls123@daum.net"));
        Member member4 = Member.regist(new MemberJoinVO("themanwhoresigned", "nobody123", "whocares@aboutme.com"));
        Member member5 = Member.regist(new MemberJoinVO("chinese123", "hanjalove", "hanja@qq.com"));
        memberService.join(member);
        memberService.join(member2);
        memberService.join(member3);
        memberService.join(member4);
        member4.resign();
        memberService.join(member5);

        /* Create Test Block */
        blockService.blockMember(member2, member3);

        /* Create Test Post */
        Post post = Post.create(new PostCreateVO("별 헤는 밤", "절이 지나가는 하늘에는 가을로 가득 차 있습니다. 나는 아무 걱정도 없이 가을 속의 별들을 다 헤일 듯합니다..."), member);
        Post post2 = Post.create(new PostCreateVO("서시", "죽는 날까지 하늘을 우러러 한 점 부끄럼 없기를. 잎새에 이는 바람에도 나는 괴로워했다."), member);
        Post post3 = Post.create(new PostCreateVO("Lorem ipsum dolor sit amet", "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."), member2);
        Post post4 = Post.create(new PostCreateVO("날개", "박제가 되어 버린 천재를 아시오?"), member3);
        Post post5 = Post.create(new PostCreateVO("회한의 장", "가장 무력한 사내가 되기 위해 나는 얼금뱅이였다"), member3);
        Post post6 = Post.create(new PostCreateVO("탈퇴맨", "가입하자마자 탈퇴하며 DB를 축내는 것이 나다"), member4);
        Post post7 = Post.create(new PostCreateVO("廣開土大王陵碑", "願太王陵安如山固如岳"), member5);

        postService.create(post);
        postService.create(post2);
        postService.create(post3);
        postService.create(post4);
        postService.create(post5);
        postService.create(post6);
        postService.create(post7);

        /* Create Test Comment */


    }

}
