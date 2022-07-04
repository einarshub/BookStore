package com.example.bookstore.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookDto, Long> {

    boolean existsBookDtoByName(String name);

    Optional<BookDto> findBookDtoByName(String name);
}
