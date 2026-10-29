package com.example.library_api.books.dto;

import java.time.Instant;
import java.util.UUID;

public record BookResponse(UUID id, String title, String author, String isbn, Integer publishedYear, Instant createdAt,
    Instant updatedAt) {

}
