package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCrypt;

@ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
class NoUserException extends Exception {
    public NoUserException(String message) {
        super(message);
    }
}

@RestController
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) throws NoUserException {
        var user = userRepository.findById(id);
        if (user.isEmpty()) throw new NoUserException("No user with id " + id);
        return user.get();
    }

    @PostMapping("/users/create")
    public User createUser(@RequestBody User user) {
        // Hash password
        user.password = BCrypt.hashpw(user.password, BCrypt.gensalt());
        return userRepository.save(user);
    }

    @GetMapping("/users/login")
    public User login(String email, String password) throws NoUserException {
        var user = userRepository.findByEmail(email);
        if(user.isEmpty())
            throw new NoUserException("No user with email " + email);

        if (!BCrypt.checkpw(password, user.get().password))
            throw new NoUserException("Wrong password for user " + email);

        return user.get();
    }
}
