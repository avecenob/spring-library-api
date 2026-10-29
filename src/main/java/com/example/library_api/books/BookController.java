package com.example.library_api.books;

import java.net.URI;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.library_api.books.dto.BookResponse;
import com.example.library_api.books.dto.CreateBookRequest;
import com.example.library_api.books.dto.UpdateBookRequest;

import jakarta.validation.Valid;

public class BookController {

  private final BookService service;

  public BookController(BookService service) {
    this.service = service;
  }

  @GetMapping
  public PagedModel<BookResponse> list(@PageableDefault(size = 20, sort = "id") Pageable pageable) {
    return new PagedModel<>(service.findAll(pageable));
  }

  @GetMapping("/{id}")
  public BookResponse get(@PathVariable UUID id) {
    return service.findById(id);
  }

  @PostMapping 
  public ResponseEntity<BookResponse> create(@Valid @RequestBody CreateBookRequest request) {
    BookResponse created = service.create(request);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}").buildAndExpand(created.id()).toUri();
    return ResponseEntity.created(location).body(created);
  }

  @PutMapping("/{id}")
  public BookResponse update(@PathVariable UUID id, @Valid @RequestBody UpdateBookRequest request) {
    return service.update(id, request);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    service.delete(id);
  }
}
