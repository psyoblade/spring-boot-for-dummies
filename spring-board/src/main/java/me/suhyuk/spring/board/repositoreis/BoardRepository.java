package me.suhyuk.spring.board.repositoreis;

import me.suhyuk.spring.board.entities.BoardItem;
import org.springframework.data.repository.CrudRepository;

public interface BoardRepository extends CrudRepository<BoardItem, Integer> {
}
