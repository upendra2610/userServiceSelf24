package org.example.userservice24.repositories;

import org.example.userservice24.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByValueAndDeletedAndExpiryAtGreaterThan(
            String tokenValue,
            Boolean deleted,
            Date date
    );

    Optional<Token> findByValueAndDeleted(String tokenValue, Boolean deleted);
}
