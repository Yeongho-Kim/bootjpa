package com.web;

import com.web.domain.Files;
import com.web.domain.User;
import com.web.domain.WebBoard;
import com.web.domain.WebReply;
import com.web.repository.FilesRepository;
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
    private FilesRepository filesRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private WebBoardRepository boardRepository;

    @Autowired
    private WebReplyRepository webReplyRepository;
    @Test
    void boardInsert() {
        IntStream.range(0,300).forEach(i->{
            WebBoard board= new WebBoard();

            board.setTitle("Test Title"+i);
            board.setContent("Test Content..."+i);
            board.setWriter("rladudgh337");
            boardRepository.save(board);
        });

    }

    @Test
    public void testReply(){
        int num=usersRepository.findCountByUserId("asddasdasxc");
        System.out.println("☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★$☆★☆★☆★☆★☆★");
        System.out.println(num);
    }

    @Test
    public void makeFiles(){
        //IntStream.range(297,).forEach(i->{
            WebBoard board=new WebBoard();
            board.setBno((long)297);
            for(int j=10;j<20;j++){
                Files files=new Files();
                files.setFileName("testFile.."+j);
                files.setFileUrl("/upload");
                files.setImage(false);
                files.setBoard(board);
                filesRepository.save(files);
            }
        //});
    }
}
