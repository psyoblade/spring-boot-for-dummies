package me.suhyuk.spring.webmvc.thymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ThymeleafController {

    @GetMapping("/c_psyoblade")
    public String getAdministrator(Model model) {
        model.addAttribute("name", "psyoblade");
        return "v_psyoblade";
    }
}
