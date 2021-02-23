package me.suhyuk.spring.board.services;

import me.suhyuk.spring.board.entities.BoardItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService{

    @Override
    public List<BoardItem> selectBoardList() throws Exception {
        return null;
    }

    @Override
    public void insertBoard(BoardItem boardItem) throws Exception {

    }

    @Override
    public BoardItem selectBoardDetail(int boardIndex) throws Exception {
        return null;
    }

    @Override
    public void updateBoard(BoardItem boardItem) throws Exception {

    }

    @Override
    public void deleteBoard(int boardIndex) throws Exception {

    }
}
