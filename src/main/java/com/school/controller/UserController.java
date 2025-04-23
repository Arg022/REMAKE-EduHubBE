package com.school.controller;

import com.school.model.Users;
import com.school.dto.UserDTO;
import com.school.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Users", description = "Endpoints for managing users")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    private UserDTO convertToDTO(Users user) {
        return new UserDTO(
            user.getId(),
            user.getUsername(),
            user.getRole()
        );
    }

    @Operation(summary = "Get all users", description = "Retrieve a list of all users")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> userDTOs = userService.getAllUsers()
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }

    @Operation(summary = "Get user by ID", description = "Retrieve a user by their ID")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @userService.getUserById(#id).username == authentication.name")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(convertToDTO(userService.getUserById(id)));
    }

    @Operation(summary = "Create a new user", description = "Add a new user to the system")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> createUser(@RequestBody Users user) {
        return ResponseEntity.ok(convertToDTO(userService.saveUser(user)));
    }

    @Operation(summary = "Register a new user", description = "Register a new user with encrypted password")
    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> registerUser(@RequestBody Users user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return ResponseEntity.ok(convertToDTO(userService.saveUser(user)));
    }

    @Operation(summary = "Update a user", description = "Update the details of an existing user")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @userService.getUserById(#id).username == authentication.name")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody Users user) {
        return ResponseEntity.ok(convertToDTO(userService.updateUser(id, user)));
    }

    @Operation(summary = "Delete a user", description = "Remove a user from the system")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}