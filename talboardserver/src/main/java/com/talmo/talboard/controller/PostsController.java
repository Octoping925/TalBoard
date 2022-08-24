package com.talmo.talboard.controller;

import com.talmo.talboard.domain.Post;
import com.talmo.talboard.repository.PostRepository;
import com.talmo.talboard.service.PostService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostsController {
    private final PostService postService;
    private final PostRepository postRepository;

    @ApiOperation(value="전체 게시글 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK : 전체 게시글 조회 성공"),
            @ApiResponse(code = 404, message = "Not Found : 전체 게시글 조회 실패")
    })
    @GetMapping("")
    public List<Post> getAllPosts() {
        return null;
    }
    
    @ApiOperation(value="게시글 상세 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK : 게시글 상세 조회 성공"),
            @ApiResponse(code = 404, message = "Not Found : 게시글 상세 조회 실패")
    })
    @GetMapping("/${post_no}")
    public List<Post> getDetailOfPost() {
        return null;
    }

    @ApiOperation(value="게시글 수정")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK : 게시글 수정 성공"),
            @ApiResponse(code = 400, message = "Bad Request : 데이터 유효성 검사 실패"),
            @ApiResponse(code = 401, message = "Unauthorized : 수정 권한 없음"),
            @ApiResponse(code = 404, message = "Not Found : 게시글 수정 실패")
    })
    @PatchMapping("/${post_no}")
    public List<Post> updatePost() {
        return null;
    }

    @ApiOperation(value="게시글 삭제")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK : 게시글 삭제 성공"),
            @ApiResponse(code = 401, message = "Unauthorized : 삭제 권한 없음"),
            @ApiResponse(code = 404, message = "Not Found : 게시글 삭제 실패")
    })
    @DeleteMapping("/${post_no}")
    public List<Post> deletePost() {
        return null;
    }

    @ApiOperation(value="게시글 추천/비추천 수 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK : 게시글 추천/비추천 수 조회 성공"),
            @ApiResponse(code = 404, message = "Not Found : 게시글 추천/비추천 수 조회 실패")
    })
    @GetMapping("/${post_no}/like")
    public List<Post> getLikeAndDislike() {
        return null;
    }

    @ApiOperation(value="게시글 추천/비추천")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK : 게시글 추천/비추천 성공"),
            @ApiResponse(code = 400, message = "Bad Request : 데이터 유효성 검사 실패"),
            @ApiResponse(code = 404, message = "Not Found : 게시글 추천/비추천 실패")
    })
    @PostMapping("/${post_no}/like")
    public List<Post> setLikeAndDislike() {
        return null;
    }

    @ApiOperation(value="댓글 작성")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK : 댓글 작성 성공"),
            @ApiResponse(code = 400, message = "Bad Request : 데이터 유효성 검사 실패"),
            @ApiResponse(code = 404, message = "Not Found : 댓글 작성 실패")
    })
    @PostMapping("/${post_no}/comment")
    public List<Post> setComment() {
        return null;
    }

    @ApiOperation(value="댓글 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK : 조회 성공"),
            @ApiResponse(code = 404, message = "Not Found : 댓글 조회 실패")
    })
    @GetMapping("/${post_no}/comment")
    public List<Post> getComment() {
        return null;
    }

    @ApiOperation(value="댓글 수정")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK : 댓글 수정 성공"),
            @ApiResponse(code = 400, message = "Bad Request : 데이터 유효성 검사 실패"),
            @ApiResponse(code = 401, message = "Unauthorized : 수정 권한 없음"),
            @ApiResponse(code = 404, message = "Not Found : 댓글 수정 실패")
    })
    @PatchMapping("/${post_no}/comment")
    public List<Post> updateComment() {
        return null;
    }

    @ApiOperation(value="댓글 삭제")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK : 댓글 삭제 성공"),
            @ApiResponse(code = 401, message = "Unauthorized : 삭제 권한 없음"),
            @ApiResponse(code = 404, message = "Not Found : 댓글 삭제 실패")
    })
    @DeleteMapping("/${post_no}/comment")
    public List<Post> deleteComment() {
        return null;
    }

    @ApiOperation(value="게시글 신고")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK : 게시글 신고 성공"),
            @ApiResponse(code = 404, message = "Not Found : 사용자 또는 게시글 번호 조회 실패")
    })
    @PostMapping("/${post_no}/report")
    public List<Post> reportPost() {
        return null;
    }

    @ApiOperation(value="게시글 검색")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK : 게시글 검색 성공"),
            @ApiResponse(code = 404, message = "Not Found : 게시글 검색 실패")
    })
    @GetMapping("/search")
    public List<Post> getPost() {
        return null;
    }

    @ApiOperation(value="신고된 글 목록 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK : 신고된 글 목록 조회 성공"),
            @ApiResponse(code = 404, message = "Not Found : 신고된 글 목록 조회 실패")
    })
    @GetMapping("/report")
    public List<Post> getAllReportedPosts() {
        return null;
    }
}
