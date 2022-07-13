package com.example.bookstoreapp.dao.repository;

import com.example.bookstoreapp.dao.entity.AuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<AuthorityEntity,Long> {

    AuthorityEntity findByAuthority(String authority);

}
