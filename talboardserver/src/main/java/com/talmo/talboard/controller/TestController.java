package com.talmo.talboard.controller;

import com.talmo.talboard.domain.Member;
import com.talmo.talboard.domain.Post;
import com.talmo.talboard.domain.vo.MemberJoinVO;
import com.talmo.talboard.domain.vo.PostCreateVO;
import com.talmo.talboard.service.MemberService;
import com.talmo.talboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class TestController {
    private final MemberService memberService;
    private final PostService postService;

    @GetMapping("/createTestData")
    public void createTestData() {
        Member member = Member.regist(new MemberJoinVO("octoping925", "password", "test@naver.com"));
        Member member2 = Member.regist(new MemberJoinVO("faker123", "playlol", "mid123@lol.com"));
        memberService.join(member);
        memberService.join(member2);

        Post post = Post.create(new PostCreateVO("별 헤는 밤", "절이 지나가는 하늘에는 가을로 가득 차 있습니다. 나는 아무 걱정도 없이 가을 속의 별들을 다 헤일 듯합니다...")
        , member);
        Post post2 = Post.create(new PostCreateVO("Lorem ipsum dolor sit amet", "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.")
            , member);
        postService.create(post);
        postService.create(post2);
    }

}
