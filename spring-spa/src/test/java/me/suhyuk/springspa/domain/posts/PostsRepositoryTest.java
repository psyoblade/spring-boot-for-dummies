package me.suhyuk.springspa.domain.posts;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired PostsRepository postsRepository;

    @After
    public void cleanUp() {
        postsRepository.deleteAll();
    }

    @Test
    public void testJpa() {
        System.out.println("한글 출력");
        assertEquals(true, true);
        Posts x = new Posts("a", "b", "c");
    }

    @Test
    public void testBBSCreate() {
        //given
        postsRepository.save(Posts.builder()
                .title("테스트 게시글")
                .content("테스트 본문")
                .author("jojoldu@gmail.com")
                .build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle(), is("테스트 게시글"));
        assertThat(posts.getContent(), is("테스트 본문"));

    }

}