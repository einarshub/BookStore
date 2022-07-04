package com.example.bookstore.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookDto> getBooks(int page) {
        return bookRepository.findAll(PageRequest.of(page, 50, Sort.by("createdDate").descending())).toList();
    }

    public void addBook(BookDto book) {
        if (bookRepository.existsBookDtoByName(book.getName())) {
            throw new IllegalStateException(String.format("Book name %s taken", book.getName()));
        }
        saveBook(book);
    }

    @Transactional
    public void updateBook(String name, String newName, float price) {
        if (name != null && !name.isEmpty()) {
            BookDto book = bookRepository.findBookDtoByName(name)
                    .orElseThrow(() -> new IllegalStateException(String.format("Book with the name %s not found", name)));

            if (newName != null && !newName.isEmpty() && !newName.equals(book.getName())) {
                book.setName(newName);
            }

            updateBookPrice(book, price);
        }
    }

    public void addBookList(List<BookDto> bookList) {
        for (BookDto newBook : bookList){
            Optional<BookDto> existingBook = bookRepository.findBookDtoByName(newBook.getName());
            if(existingBook.isPresent()){
                updateBookPrice(existingBook.get(), newBook.getPrice());
            }else{
                saveBook(newBook);
            }
        }
        bookRepository.flush();
    }

    public void updateBookPrice(BookDto book, float price) {
        if (price > 0 && price != book.getPrice()) {
            book.setPrice(price);
        }
    }

    private void saveBook(BookDto book){
        bookRepository.save(book);
    }
}
