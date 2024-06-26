package com.itschool.base.controller;

import com.itschool.base.model.UserDTO;
import com.itschool.base.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Add a new user")
    @PostMapping("/users")
    public UserDTO insertUser(UserDTO userDTO) {
        return userService.addUser(userDTO);
    }

    @GetMapping("/users/{id}")
    public UserDTO findUser(@PathVariable Long id) {
        return userService.findUser(id);
    }

    @PutMapping("/users/{id}")
    public UserDTO updateUser(@PathVariable Long id, UserDTO userDTO) {
        return userService.updateUser(id, userDTO);
    }

    @DeleteMapping("/users/{id}")
    public boolean deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }
}
