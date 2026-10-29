package com.example.library_api.books;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, UUID>{
  boolean existsByIsbn(String isbn);  // query derived from method name
}
