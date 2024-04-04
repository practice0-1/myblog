package com.blog5.service.impl;

import com.blog5.entity.Comment;
import com.blog5.entity.Post;
import com.blog5.exception.ResourceNotFoundException;
import com.blog5.payload.CommentDto;
import com.blog5.repository.CommentRepository;
import com.blog5.repository.PostRepository;
import com.blog5.service.CommentService;
import com.sun.org.apache.regexp.internal.RE;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;

    private PostRepository postRepository;

    private ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment = maptoEntity(commentDto);

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post not fount by id:" +postId)
        );

        comment.setPost(post);

        Comment savedComment = commentRepository.save(comment);

        CommentDto dto = maptoDto(savedComment);

        return dto;
    }

    @Override
    public List<CommentDto> findCommentById(long postId) {
        postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post not found by id" + postId)
        );
        List<Comment> comment = commentRepository.findByPostId(postId);
        List<CommentDto> collect = comment.stream().map(comment1 -> maptoDto(comment1)).collect(Collectors.toList());
        return collect;
    }

    @Override
    public CommentDto findCommentById(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post not fount by postId" + postId)
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("comment not found by id" + commentId));

        CommentDto dto = maptoDto(comment);


        return dto;
    }

    @Override
    public List<CommentDto> getAllComments() {
        List<Comment> all = commentRepository.findAll();
        return all.stream().map(al -> maptoDto(al)).collect(Collectors.toList());
    }

    @Override
    public void deleteComment(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post not found by postId" + postId)
        );
        commentRepository.findById(commentId).orElseThrow(
                ()-> new ResourceNotFoundException("comment not found by Id"+commentId)
        );
        commentRepository.deleteById(commentId);
    }

    private CommentDto maptoDto(Comment savedComment) {
        CommentDto dto = modelMapper.map(savedComment, CommentDto.class);
        return dto;
    }

    private Comment maptoEntity(CommentDto commentDto) {
        Comment comment = modelMapper.map(commentDto, Comment.class);
        return comment;
    }
}
