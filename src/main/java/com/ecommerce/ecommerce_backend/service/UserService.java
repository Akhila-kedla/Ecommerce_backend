package com.ecommerce.ecommerce_backend.service;

import com.ecommerce.ecommerce_backend.entity.User;
import com.ecommerce.ecommerce_backend.exception.BadRequestException;
import com.ecommerce.ecommerce_backend.exception.ResourceNotFoundException;
import com.ecommerce.ecommerce_backend.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class UserService {

    private static final Logger logger = Logger.getLogger(UserService.class.getName());

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) {

        logger.info("Attempting to register user: " + user.getEmail());

        if (userRepository.existsByEmail(user.getEmail())) {
            logger.warning("Registration failed. Email already exists: " + user.getEmail());
            throw new BadRequestException("Email already registered");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (user.getRole() == null) {
            user.setRole(User.Role.CUSTOMER);
        }

        User savedUser = userRepository.save(user);

        logger.info("User registered successfully with ID: " + savedUser.getId());
        return savedUser;
    }

    public Optional<User> findByEmail(String email) {
        logger.info("Searching user by email: " + email);
        return userRepository.findByEmail(email);
    }

    public User getUserById(Long id) {

        logger.info("Fetching user by ID: " + id);

        return userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.severe("User not found with ID: " + id);
                    return new ResourceNotFoundException("User not found");
                });
    }

    public void deleteUser(Long id) {
        logger.warning("Deleting user with ID: " + id);
        userRepository.deleteById(id);
    }
}
