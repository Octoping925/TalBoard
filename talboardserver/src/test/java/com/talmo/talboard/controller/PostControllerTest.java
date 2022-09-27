package com.talmo.talboard.controller;

import com.talmo.talboard.config.ExceptionConstants;
import com.talmo.talboard.config.TestHelper;
import com.talmo.talboard.repository.PostRepository;
import com.talmo.talboard.service.PostService;
import com.talmo.talboard.service.ReportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostControllerTest.class)
public class PostControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    PostService postService;
    @MockBean
    PostRepository postRepository;
    @MockBean
    ReportService reportService;

    @Test
    void get_신고된게시글조회() throws Exception {
        mockMvc.perform(get("/posts/search/report")
                .param("memberNo", "1")
                .contentType(MediaType.APPLICATION_JSON))

            .andExpect(status().isOk());
    }

    @Test
    void get_신고된게시글조회실패() throws Exception {
        mockMvc.perform(get("/posts/search/report")
                .param("memberNo", "1")
                .contentType(MediaType.APPLICATION_JSON))

        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.massage").value(ExceptionConstants.NOT_FOUND_REPORT_POST_MESSAGE));
    }
}
