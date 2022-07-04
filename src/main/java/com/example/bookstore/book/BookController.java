package com.example.bookstore.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin    //necessary to use the openapi doc from the same pc
@RequestMapping(path = "/")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<BookDto> getBooks() {
        return bookService.getBooks(0);
    }

    @GetMapping(path = "/{page}")
    public List<BookDto> getBooks(@PathVariable("page") int page) {
        return bookService.getBooks(page);
    }

    @PostMapping
    public void addBook(@RequestBody BookDto book) {
        bookService.addBook(book);
    }

    @PutMapping
    public void updateBook(
            @RequestParam(required = true) String name,
            @RequestParam(required = false) String newName,
            @RequestParam(required = false) float price
    ) {
        bookService.updateBook(name, newName, price);
    }

    @PostMapping(path = "/bookListUpdate")
    public void addBookList(@RequestBody List<BookDto> bookList) {
        bookService.addBookList(bookList);
    }
}
