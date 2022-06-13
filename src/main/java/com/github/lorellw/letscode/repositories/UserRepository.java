package com.github.lorellw.letscode.repositories;

import com.github.lorellw.letscode.entiites.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUsername(String name);

    User findByActivationCode(String code);
}
