package com.web;

import com.web.domain.WebBoard;
import com.web.repository.UsersRepository;
import com.web.repository.WebBoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
class BootjpaApplicationTests {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private WebBoardRepository boardRepository;
    @Test
    void boardInsert() {
        IntStream.range(21,300).forEach(i->{
            WebBoard board= new WebBoard();

            board.setTitle("Test Title"+i);
            board.setContent("Test Content..."+i);
            board.setWriter("rladudgh337");
            boardRepository.save(board);
        });

    }
}
