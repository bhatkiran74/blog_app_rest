package com.kiran.blog_app_rest.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDto {
    private Long id;

    @NotEmpty
    @Size(min = 2,message = "Title : Please provide at least 2 character")
    private String title;

    @NotEmpty
    @Size(min = 5,message = "description : Please provide at least 5 character")
    private String description;

    @NotEmpty
    private String content;
}
