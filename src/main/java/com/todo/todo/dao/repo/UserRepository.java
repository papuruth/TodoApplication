package com.todo.todo.dao.repo;

import com.todo.todo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Long countUserByEmail(String email);

    User findByEmail(String email);

    User findByEmailAndEntityStatus(String email, String status);
}
