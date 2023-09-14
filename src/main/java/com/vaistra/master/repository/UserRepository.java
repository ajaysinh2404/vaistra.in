package com.vaistra.master.repository;

import com.vaistra.master.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmailIgnoreCase(String email);

    Optional<User> findByEmail(String email);

    User findByEmailIgnoreCase(String email);


}
