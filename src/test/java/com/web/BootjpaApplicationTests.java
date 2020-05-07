package com.web;

import com.web.domain.WebBoard;
import com.web.domain.WebReply;
import com.web.repository.UsersRepository;
import com.web.repository.WebBoardRepository;
import com.web.repository.WebReplyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.stream.IntStream;

@SpringBootTest
class BootjpaApplicationTests {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private WebBoardRepository boardRepository;

    @Autowired
    private WebReplyRepository webReplyRepository;
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

    @Test
    public void testReply(){
        Long[] arr = new Long[50];
        for(int i=0; i<50; i++){
            arr[i]=(long)(i+250);
        }

        Arrays.stream(arr).forEach(num->{
            WebBoard board=new WebBoard();
            board.setBno(num);
            IntStream.range(0,15).forEach(i->{
                WebReply reply=new WebReply();
                reply.setReplyText("테스트 리플입니다.");
                reply.setReplyer("basic");
                reply.setBoard(board);
                webReplyRepository.save(reply);
            });
        });
    }
}
