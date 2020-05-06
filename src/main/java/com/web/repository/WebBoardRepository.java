package com.web.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.web.domain.WebBoard;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import com.web.domain.QWebBoard;

public interface WebBoardRepository extends CrudRepository<WebBoard, Long>, QuerydslPredicateExecutor<WebBoard>{

    public default Predicate makePredicate(String type, String keyword){

        BooleanBuilder builder = new BooleanBuilder();

        QWebBoard board = QWebBoard.webBoard;

        builder.and(board.bno.gt(0));

        if(type == null){
            return builder;
        }

        switch(type){
            case "t":
                builder.and(board.title.like("%" + keyword +"%"));
                break;
            case "c":
                builder.and(board.content.like("%" + keyword +"%"));
                break;
            case "w":
                builder.and(board.writer.like("%" + keyword +"%"));
                break;
        }

        return builder;
    }

    public default Predicate searchType(String type, String keyword){
        BooleanBuilder builder=new BooleanBuilder();
        QWebBoard board=QWebBoard.webBoard;
        builder.and(board.bno.gt(0));

        if(type==null)
            return builder;
        switch (type){
            case "t": builder.and(board.title.like("%"+keyword+"%"));
                break;
            case "c": builder.and(board.content.like("%"+keyword+"%"));
                break;
            case "w": builder.and(board.writer.like("%"+keyword+"%"));
                break;

        }
        return builder;
    }
}





