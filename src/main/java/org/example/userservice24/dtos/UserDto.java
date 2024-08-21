package org.example.userservice24.dtos;

import lombok.Getter;
import lombok.Setter;
import org.example.userservice24.models.Role;
import org.example.userservice24.models.User;

import java.util.List;
@Getter
@Setter
public class UserDto {
    private String name;
    private String email;
    private List<Role> roles;

    public static UserDto fromUser(User user) {
        if (user == null) {
            return null;
        }

        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());

        return userDto;
    }

}
