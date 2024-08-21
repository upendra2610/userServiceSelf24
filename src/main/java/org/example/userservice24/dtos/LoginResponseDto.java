package org.example.userservice24.dtos;

import lombok.Getter;
import lombok.Setter;
import org.example.userservice24.models.Token;

@Getter
@Setter
public class LoginResponseDto {
    private Token token;
}
