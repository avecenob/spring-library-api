package com.example.library_api.books;

import com.example.library_api.books.dto.BookResponse;
import com.example.library_api.books.dto.CreateBookRequest;

final class BookMapper {
  private BookMapper() {
  }

  static Book toEntity(CreateBookRequest r) {
    return new Book(r.title(), r.author(), r.isbn(), r.publishedYear());
  }

  static BookResponse toResponse(Book b) {
    return new BookResponse(b.getId(), b.getTitle(), b.getAuthor(), b.getIsbn(), b.getPublishedYear(), b.getCreatedAt(),
        b.getUpdatedAt());
  }
}
