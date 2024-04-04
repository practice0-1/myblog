package com.blog5.service;

import com.blog5.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);

    List<CommentDto> findCommentById(long postId);

    CommentDto findCommentById(long postId, long commentId);

    List<CommentDto> getAllComments();

    void deleteComment(long postId, long commentId);
}
