package com.school.service;

import com.school.model.Users;
import com.school.repository.UserRepository;
import com.school.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public List<Users> getAllUsers() {
        logger.info("Retrieving all users");
        List<Users> users = userRepository.findAll();
        logger.debug("Found {} users", users.size());
        return users;
    }

    public Users getUserById(Long id) {
        logger.info("Retrieving user with id: {}", id);
        return userRepository.findById(id)
            .orElseThrow(() -> {
                logger.error("User not found with id: {}", id);
                return new ResourceNotFoundException("User", "id", id);
            });
    }

    public Users saveUser(Users user) {
        logger.info("Saving new user: {}", user.getUsername());
        // Encode password if it's not already encoded
        if (user.getPassword() != null && !user.getPassword().startsWith("$2a$")) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        Users savedUser = userRepository.save(user);
        logger.debug("User saved successfully with id: {}", savedUser.getId());
        return savedUser;
    }

    public void deleteUserById(Long id) {
        logger.info("Attempting to delete user with id: {}", id);
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            logger.debug("User deleted successfully with id: {}", id);
        } else {
            logger.error("Failed to delete - user not found with id: {}", id);
            throw new ResourceNotFoundException("User", "id", id);
        }
    }

    public Users updateUser(Long id, Users updatedUser) {
        logger.info("Attempting to update user with id: {}", id);
        Optional<Users> existingUserOptional = userRepository.findById(id);

        if (existingUserOptional.isEmpty()) {
            logger.error("Failed to update - user not found with id: {}", id);
            throw new ResourceNotFoundException("User", "id", id);
        }

        Users existingUser = existingUserOptional.get();
        existingUser.setUsername(updatedUser.getUsername());
        // Only update password if a new one is provided
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }
        existingUser.setRole(updatedUser.getRole());

        Users savedUser = userRepository.save(existingUser);
        logger.debug("User updated successfully with id: {}", savedUser.getId());
        return savedUser;
    }
}