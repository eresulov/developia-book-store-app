package com.example.bookstoreapp.dao.repository;

import com.example.bookstoreapp.dao.entity.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AuthorRepository extends JpaRepository<AuthorEntity,Long> {
}
