package com.web.repository;

import com.web.domain.WebBoard;
import org.springframework.data.repository.CrudRepository;

public interface CustomCrudRepository extends CrudRepository<WebBoard,Long>, CustomWebBoard {
}
