package com.web.repository;

import com.web.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<User, Long> {
    public User findUserByUserId(String userId);

}
