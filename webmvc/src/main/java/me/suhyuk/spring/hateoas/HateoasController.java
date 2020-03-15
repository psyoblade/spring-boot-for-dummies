package me.suhyuk.spring.hateoas;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ResourceBundle;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class HateoasController {

    @GetMapping("/hateoas")
    public Resource<LuckyBox> getHateoas() {
        LuckyBox luckyBox = new LuckyBox("화이트 데이 선물");
        Resource<LuckyBox> resourceLuckyBox = new Resource<>(luckyBox);
        // 현재 컨트롤러의 getHateoas 메소드에 _self rel 을 link 걸어서 추가해라
        resourceLuckyBox.add(linkTo(methodOn(HateoasController.class).getHateoas()).withSelfRel());
        // 별도의 URL 정보를 넣을 수도 있습니다 <- 해당 rel 값이 self 와 같은 위치에 오게 되는데 어떻게 쓰는건지..?
        String rel = "https://www.kakaocorp.com/service/KakaoTalkGift";
        resourceLuckyBox.add(linkTo(methodOn(HateoasController.class).getHateoas()).withRel(rel));
        return resourceLuckyBox;
    }
}
