package com.blog5.service;

import com.blog5.payload.PostDto;
import com.blog5.payload.PostResponse;

import java.util.List;

public interface PostService {


    PostDto createPost(PostDto postDto);

    void deletePost(long id);

    PostDto updatePost(long id, PostDto postDto);

    PostDto getPost(long id);


    PostResponse getPost(int pageNo, int pageSize, String by, String sortBy);
}
