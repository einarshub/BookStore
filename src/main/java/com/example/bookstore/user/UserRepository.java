package com.example.bookstore.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserDto, Integer> {

    boolean existsUserDtoByUserName(String userName);

    Optional<UserDto> findByUserName(String userName);
}
