package com.web;

import com.web.domain.Files;
import com.web.domain.User;
import com.web.domain.WebBoard;
import com.web.repository.FilesRepository;
import com.web.repository.UsersRepository;
import com.web.repository.WebBoardRepository;
import com.web.repository.WebReplyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
class BootjpaApplicationTests {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private WebBoardRepository boardRepository;

    @Autowired
    private WebReplyRepository webReplyRepository;

    @Autowired
    private FilesRepository filesRepository;


    @Test
    public void test(){
        WebBoard board=boardRepository.findById(301L).get();
        System.out.println(board.getTitle());
        System.out.println(board.getFiles().isEmpty());
    }
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
    public void makeUser(){
        IntStream.range(0,100).forEach(i->{
            User user=new User();
            if(i<50){
                user.setUserId("TEST_BASIC"+i);

            }else if(i<90){
                user.setUserId("TEST_MANAGER"+i);
            }else{
                user.setUserId("TEST_ADMIN"+i);
            }
            user.setUserPw("1234");
            user.setUserName("TEST_USER"+i);
            user.setUserPhone("01011112222");
            user.setUserEmail("testEmail@google.com");
            usersRepository.save(user);
        });

    }
}
