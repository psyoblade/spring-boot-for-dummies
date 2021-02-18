package me.suhyuk.spring.board.controllers;

import me.suhyuk.spring.board.entities.BoardItem;
import me.suhyuk.spring.board.repositoreis.BoardRepository;
import me.suhyuk.spring.board.services.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping(path="/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

    private String now() {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }

    @PostMapping("/add")
    public @ResponseBody String addBoardItem(@RequestParam String title,
                                             @RequestParam String contents,
                                             @RequestParam String creatorId) {
        BoardItem boardItem = new BoardItem();
        boardItem.setTitle(title);
        boardItem.setContents(contents);
        boardItem.setCreatorId(creatorId);
        boardItem.setCreatedDatetime(now());
        boardRepository.save(boardItem);
        return "Added";
    }

    @RequestMapping("/list")
    public @ResponseBody Iterable<BoardItem> getAllBoardItems() {
        return boardRepository.findAll();
    }
}
