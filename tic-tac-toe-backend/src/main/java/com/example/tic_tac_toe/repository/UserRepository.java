package com.example.tic_tac_toe.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tic_tac_toe.entity.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    List<User> findTop10ByOrderByWinsDesc();
    Optional<User> findByEmail(String email);


}
