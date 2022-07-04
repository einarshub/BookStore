package com.example.bookstore;

import com.example.bookstore.book.BookDto;
import com.example.bookstore.book.BookRepository;
import com.example.bookstore.user.UserDto;
import com.example.bookstore.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

@Configuration
public class Config {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public Config(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    CommandLineRunner commandLineRunner(BookRepository bookRepository, UserRepository userRepository) {
        return args -> {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream is = classloader.getResourceAsStream("BookData.csv");
            Scanner sc = new Scanner(is);

            while (sc.hasNextLine()) {
                bookRepository.save(new BookDto(sc.nextLine()));
            }

            userRepository.saveAll(List.of(
                    new UserDto("user", passwordEncoder.encode("user"), "ROLE_USER"),
                    new UserDto("admin", passwordEncoder.encode("admin"), "ROLE_ADMIN")
            ));
        };
    }
}
