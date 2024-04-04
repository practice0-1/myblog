package com.blog5.controller;

import com.blog5.payload.CommentDto;
import com.blog5.payload.PostDto;
import com.blog5.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    //http://localhost:8080/api/posts/1/comments

    @PostMapping("/posts/{postId}/comment")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") long postId,
                                                    @RequestBody CommentDto commentDto) {
        CommentDto comment = commentService.createComment(postId, commentDto);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);

    }
    @GetMapping("/posts/{postId}/comment")
    public List<CommentDto> findCommentById(@PathVariable(value="postId") long postId) {
       List<CommentDto> dto = commentService.findCommentById(postId);
       return dto;
    }
    @GetMapping("/posts/{postId}/comment/{commentId}")
    public CommentDto findbyCommentId(@PathVariable(value="postId")long postId,
                                      @PathVariable(value="commentId")long commentId){
        CommentDto dto = commentService.findCommentById(postId,commentId);
        return dto;

    }
    @GetMapping("/comments")
    public List<CommentDto> getAllComments(){
        List<CommentDto> allComments = commentService.getAllComments();
        return allComments;
    }
    @DeleteMapping("/posts/{postId}/comment/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable(value = "postId")long postId,
                                                @PathVariable(value = "commentId")long commentId){
        commentService.deleteComment(postId,commentId);
        return new ResponseEntity<>("comment deleted",HttpStatus.OK);
    }
}


