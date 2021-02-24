package me.suhyuk.spring.board.controllers;

import me.suhyuk.spring.board.entities.BoardItem;
import me.suhyuk.spring.board.entities.ResponseMessage;
import me.suhyuk.spring.board.repositoreis.BoardRepository;
import me.suhyuk.spring.board.services.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    @PostMapping(value = "/add")
    @ResponseBody
    public String createBoardItem(@RequestParam String title,
                             @RequestParam String contents,
                             @RequestParam String creatorId) {
        BoardItem boardItem = new BoardItem();
        boardItem.setTitle(title);
        boardItem.setContents(contents);
        boardItem.setCreatorId(creatorId);
        boardItem.setCreatedDatetime(now());
        boardRepository.save(boardItem);
        return boardItem.toString();
    }

    @PostMapping(value = "/create")
    @ResponseBody
    public String createBoardItem(@RequestBody BoardItem boardItem) {
        boardRepository.save(boardItem);
        return boardItem.toString();
    }

    @ResponseBody
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public String handleHttpMediaTypeNotAcceptableException() {
        return "acceptable MIME type:" + MediaType.APPLICATION_JSON_VALUE;
    }

    @PostMapping(value = "/read",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public ResponseMessage readBoardItemByXml(@RequestBody BoardItem boardItem) {
        Optional<BoardItem> found = boardRepository.findById(boardItem.getBoardIdx());
        return new ResponseMessage(boardItem.toString());
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String deleteBoardItem(@RequestParam int boardIdx) {
        BoardItem boardItem = new BoardItem();
        boardItem.setBoardIdx(boardIdx);
        boardRepository.delete(boardItem);
        return boardItem.toString();
    }

    @RequestMapping("/list")
    public @ResponseBody Iterable<BoardItem> getAllBoardItems() {
        return boardRepository.findAll();
    }
}
