package com.blog5.service.impl;

import com.blog5.entity.Post;
import com.blog5.exception.ResourceNotFoundException;
import com.blog5.payload.PostDto;
import com.blog5.payload.PostResponse;
import com.blog5.repository.PostRepository;
import com.blog5.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;


    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        Post newPost = postRepository.save(post);

        PostDto dto = new PostDto();
        dto.setId(newPost.getId());
        dto.setTitle(newPost.getTitle());
        dto.setDescription(newPost.getDescription());
        dto.setContent(newPost.getContent());

        return dto;
    }

    @Override
    public void deletePost(long id) {
        postRepository.deleteById(id);
    }

    @Override
    public PostDto updatePost(long id, PostDto postDto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("post not found by this id " + id)
        );
        post.setId(postDto.getId());
       post.setTitle(postDto.getTitle());
       post.setContent(postDto.getContent());
       post.setDescription(postDto.getDescription());
        Post save = postRepository.save(post);
        PostDto dto = mapToDto(save);
        return dto;

    }

    @Override
    public PostDto getPost(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("post not found by id " + id)
        );

        PostDto dto = mapToDto(post);
        return dto;
    }

    @Override
    public PostResponse getPost(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort orders = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo,pageSize,orders);
        Page<Post> pagePosts = postRepository.findAll(pageable);
        List<Post> posts = pagePosts.getContent();
        List<PostDto> dto = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setPostDto(dto);
        postResponse.setPageNo(pagePosts.getTotalPages());
        postResponse.setPageSize(pagePosts.getSize());
        postResponse.setLast(pagePosts.isLast());
        postResponse.setTotalElements(pagePosts.getTotalElements());
        postResponse.setTotalPage(pagePosts.getTotalPages());
        return postResponse;
    }



    PostDto mapToDto(Post post){
        PostDto dto = modelMapper.map(post, PostDto.class);

      /*  PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
      dto.setDescription(post.getDescription());

       */
        return dto;


    }
    Post mapToEntity(PostDto postDto){
      Post post =  modelMapper.map(postDto, Post.class);

     /*  Post post = new Post();
        post.setId(postDto.getId());
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
*/

        return post;

    }
}

