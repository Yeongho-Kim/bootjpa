package com.web.repository;

import com.web.domain.Files;
import com.web.domain.WebBoard;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FilesRepository extends CrudRepository<Files,Long> {
    @Query("select f from Files f where f.board=?1 and  f.fno > 0 order by f.fno desc")
    List<Files> getRepliesofBoard(WebBoard board);

    void deleteAllByBoard(WebBoard board);
}
