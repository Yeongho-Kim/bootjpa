package com.web.controller;

import com.web.domain.WebBoard;
import com.web.domain.WebReply;
import com.web.repository.WebReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/replies")
public class WebReplyController {
    @Autowired
    private WebReplyRepository replyRepository;

    @GetMapping("/{bno}")
    public ResponseEntity<List<WebReply>> getReplies(@PathVariable("bno")Long bno){
        WebBoard board=new WebBoard();
        board.setBno(bno);
        return new ResponseEntity<>(getListByBoard(board),HttpStatus.OK);
    }
    @Transactional
    @PostMapping("/{bno}")
    public ResponseEntity<List<WebReply>> addReply(@PathVariable("bno")Long bno,@RequestBody WebReply reply){
        WebBoard board = new WebBoard();
        board.setBno(bno);
        reply.setBoard(board);
        replyRepository.save(reply);

        return new ResponseEntity<>(getListByBoard(board), HttpStatus.CREATED);
    }

    @DeleteMapping("/{bno}/{rno}")
    public ResponseEntity<List<WebReply>> delReply(@PathVariable("bno")Long bno,@PathVariable("rno")Long rno){
        replyRepository.deleteById(rno);
        WebBoard board=new WebBoard();
        board.setBno(bno);
        return new ResponseEntity<>(getListByBoard(board),HttpStatus.OK);
    }
    @Transactional
    @PutMapping("/{bno}")
    public ResponseEntity<List<WebReply>> modReply(@PathVariable("bno")Long bno,@RequestBody WebReply reply){
        replyRepository.findById(reply.getRno()).ifPresent(origin->{
            origin.setReplyText(reply.getReplyText());
            replyRepository.save(origin);
        });
        WebBoard board=new WebBoard();
        board.setBno(bno);
        return new ResponseEntity<>(getListByBoard(board),HttpStatus.CREATED);
    }

    private List<WebReply> getListByBoard(WebBoard board) throws RuntimeException {
        return replyRepository.getRepliesofBoard(board);
    }
}
