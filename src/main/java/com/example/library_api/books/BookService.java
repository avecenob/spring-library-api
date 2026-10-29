package com.example.library_api.books;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.library_api.books.dto.BookResponse;
import com.example.library_api.books.dto.CreateBookRequest;
import com.example.library_api.books.dto.UpdateBookRequest;
import com.example.library_api.common.exception.DuplicateResourceException;
import com.example.library_api.common.exception.ResourceNotFoundException;

@Service
@Transactional(readOnly = true)
public class BookService {

  private final BookRepository repository;

  public BookService(BookRepository repository) {
    this.repository = repository;
  }

  public Page<BookResponse> findAll(Pageable pageable) {
    return repository.findAll(pageable).map(BookMapper::toResponse);
  }

  public BookResponse findById(UUID id) {
    return BookMapper.toResponse(getOrThrow(id));
  }

  @Transactional
  public BookResponse create(CreateBookRequest request) {
    if (repository.existsByIsbn(request.isbn())) {
      throw new DuplicateResourceException("Book with ISBN " + request.isbn() + " already exists");
    }
    Book newBook = repository.save(BookMapper.toEntity(request));
    return BookMapper.toResponse(newBook);
  }

  @Transactional
  public BookResponse update(UUID id, UpdateBookRequest request) {
    Book book = getOrThrow(id);
    book.setTitle(request.title());
    book.setAuthor(request.author());
    book.setPublishedYear(request.publishedYear());
    // No save() method: a managed entity is written at commit ("dirty checking")
    // savedAndFlush forces the write now so updatedAt is fresh in the response
    return BookMapper.toResponse(repository.saveAndFlush(book));
  }

  @Transactional
  public void delete(UUID id) {
    repository.delete(getOrThrow(id));
  }

  private Book getOrThrow(UUID id) {
    return repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Book " + id + " not found"));
  }
}
