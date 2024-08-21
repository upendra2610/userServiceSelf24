package org.example.userservice24.dtos;

import lombok.Getter;
import lombok.Setter;
import org.example.userservice24.models.User;

@Getter
@Setter
public class SignupResponseDto {
    private String userName;
    private ResponseStatus responseStatus;


}
