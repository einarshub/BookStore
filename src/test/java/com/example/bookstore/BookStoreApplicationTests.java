package com.example.bookstore;

import com.example.bookstore.book.BookDto;
import com.example.bookstore.book.BookRepository;
import com.example.bookstore.book.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BookStoreApplicationTests {

    @Mock
    private BookRepository bookRepository;

    private BookService underTest;

    @BeforeEach
    void setUp() {
        underTest = new BookService(bookRepository);
    }

    @Test
    void canAddAccount() {
        BookDto book = new BookDto("Big Bad Book", 123.01f);

        underTest.addBook(book);

        ArgumentCaptor<BookDto> bookArgumentCaptor = ArgumentCaptor.forClass(BookDto.class);

        verify(bookRepository).save(bookArgumentCaptor.capture());

        BookDto capturedAccount = bookArgumentCaptor.getValue();

        assertThat(capturedAccount).isEqualTo(book);
    }

    @Test
    void willThrowWhenNameTaken() {
        BookDto book = new BookDto("The only book you will ever need", 0.0f);

        given(bookRepository.existsBookDtoByName(book.getName())).willReturn(true);

        assertThatThrownBy(() -> underTest.addBook(book))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(String.format("Book name %s taken", book.getName()));

        verify(bookRepository, never()).save(any());
    }
}
