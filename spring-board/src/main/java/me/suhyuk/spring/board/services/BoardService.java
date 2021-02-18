package me.suhyuk.spring.board.services;

import me.suhyuk.spring.board.entities.BoardItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BoardService {
    List<BoardItem> selectBoardList() throws Exception;
}
