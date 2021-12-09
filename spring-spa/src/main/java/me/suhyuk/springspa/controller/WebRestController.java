package me.suhyuk.springspa.controller;

import lombok.AllArgsConstructor;
import me.suhyuk.springspa.controller.posts.PostsReadRequestDto;
import me.suhyuk.springspa.controller.posts.PostsSaveRequestDto;
import me.suhyuk.springspa.domain.posts.Posts;
import me.suhyuk.springspa.domain.posts.PostsRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class WebRestController {

    private PostsRepository postsRepository;

    @GetMapping("/hello")
    public String hello() {
        return "hello-world";
    }

    @PostMapping("/posts")
    public void savePosts(@RequestBody PostsSaveRequestDto dto) {
        postsRepository.save(dto.toEntity());
    }

//    @GetMapping("/posts")
//    public List<PostsReadRequestDto> getPosts() {
//        return postsRepository.findAll();
//    }

}
