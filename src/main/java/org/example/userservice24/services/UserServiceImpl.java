package org.example.userservice24.services;

import org.example.userservice24.models.Token;
import org.example.userservice24.models.User;
import org.example.userservice24.repositories.TokenRepository;
import org.example.userservice24.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, TokenRepository tokenRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    @Override
    public Token login(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty()){
            //some exception not present email
            return null;
        }

        User user = userOptional.get();
        if(!(bCryptPasswordEncoder.matches(password, user.getHashedPassword()))){
            //exception password incorrect
            return null;
        }
        Token token = createToken(user);
        token.setDeleted(false);
        token.setCreatedAt(new Date());
        return tokenRepository.save(token);
    }

    @Override
    public User signup(String name, String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isPresent()){
            // some exception
            return null;
        }
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setDeleted(false);
        user.setCreatedAt(new Date());
        user.setHashedPassword(bCryptPasswordEncoder.encode(password));

        userRepository.save(user);

        return user;
    }

    @Override
    public User validateToken(String token) {
        Optional<Token> tokenOptional = tokenRepository
                .findByValueAndDeletedAndExpiryAtGreaterThan(token, false, new Date());

        if(tokenOptional.isEmpty()){
            //throw exception
            return null;
        }
        return tokenOptional.get().getUser();
    }

    @Override
    public void logout(String tokenValue) {

        Optional<Token> optionalToken = tokenRepository.findByValueAndDeleted(tokenValue, false);

        if (optionalToken.isEmpty()) {
            //Throw some exception
        }

        Token token = optionalToken.get();

        token.setDeleted(true);
        tokenRepository.save(token);
    }

    private Token createToken(User user){
        Token token = new Token();
        token.setUser(user);
        token.setValue(RandomStringUtils.randomAlphanumeric(128));

        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_YEAR, 30);
        Date date30DaysFromToday = calendar.getTime();

        token.setExpiryAt(date30DaysFromToday);
        token.setDeleted(false);

        return token;
    }
}
