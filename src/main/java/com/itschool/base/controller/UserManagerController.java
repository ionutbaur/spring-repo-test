package com.itschool.base.controller;

import com.itschool.base.model.UserDTO;
import com.itschool.base.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User Manager", description = "User Manager API that manipulates all the operations related to users") // Swagger annotation to group the API endpoints under the "User Manager" tag
@RestController
@RequestMapping("/users")
public class UserManagerController {

    private final UserService userService;

    public UserManagerController(UserService userService) { // Constructor-based dependency injection. It injects the UserService bean into the UserController
        this.userService = userService;
    }

    @Operation(summary = "Add a new user", description = "Add a new user to the database along with their address and return the created user")
    @PostMapping
    public UserDTO insertUser(@RequestBody UserDTO userDTO) { // @RequestBody annotation needed because the request body is a JSON object that holds another JSON object
        return userService.addUser(userDTO);
    }

    @Operation(summary = "Find all users", description = "Find all users in the database and return a list of users")
    @GetMapping
    public List<UserDTO> findAllUsers() {
        return userService.findAllUsers();
    }

    @Operation(summary = "Find a user by id", description = "Find a user by their id and return the user")
    @GetMapping("{id}")
    public UserDTO findUser(@PathVariable Long id) {
        return userService.findUser(id);
    }

    @Operation(summary = "Update a user by id", description = "Update a user by their id and return the updated user")
    @PutMapping("{id}")
    public UserDTO updateUser(@PathVariable Long id,
                              @RequestBody UserDTO userDTO) {
        return userService.updateUser(id, userDTO);
    }

    @Operation(summary = "Delete a user by id", description = "Delete a user by their id and return true if the user was deleted")
    @DeleteMapping("{id}")
    public boolean deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }
}
