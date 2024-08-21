package org.example.userservice24.services;

import org.example.userservice24.models.Token;
import org.example.userservice24.models.User;

public interface UserService {
    public Token login(String email, String password);
    public User signup(String name, String email, String password);
    public User validateToken(String token);
    public void logout(String token);
}
