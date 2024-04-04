package com.blog5.payload;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class PostDto {

    private long id;

    @NotEmpty
    @Size(min = 2, message = "post title should be at least 2 characters")
    private String title;

    @NotEmpty
    @Size(min = 4,message = "post description must be at least 4 characters")
    private String description;

    @NotEmpty
    @Size(min=5,message = "post content should be at least 5 characters")
    private String content;
}
