package com.talmo.talboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainContoller {
    @GetMapping("/api_docs")
    public String apiDocs() {
        // TODO: 스프링 시큐리티에서 인증 없이 들어올 수 있는 부분에 해당 페이지 추가하면 사용 가능해짐
        return "redirect:/swagger-ui/index.html";
    }
}
