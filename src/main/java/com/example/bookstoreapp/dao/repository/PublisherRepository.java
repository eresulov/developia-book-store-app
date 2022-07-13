package com.example.bookstoreapp.dao.repository;

import com.example.bookstoreapp.dao.entity.PublisherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<PublisherEntity,Long> {
}
