package me.suhyuk.springspa.controller.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.suhyuk.springspa.domain.posts.Posts;

@Getter
@Setter
@NoArgsConstructor
public class PostsReadRequestDto {

    private String title;
    private String content;
    private String author;

    @Builder
    public PostsReadRequestDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public PostsReadRequestDto fromEntity(Posts posts) {
        return PostsReadRequestDto.builder()
                .title(posts.getTitle())
                .content(posts.getContent())
                .author(posts.getAuthor())
                .build();
    }

}
