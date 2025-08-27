package com.example.themleaf.repository;

import com.example.themleaf.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface  USERREPO extends JpaRepository<User,Long> {
    public Optional<User>getByNumber(Long number);
}
