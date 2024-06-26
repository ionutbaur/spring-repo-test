package com.itschool.base.service;

import com.itschool.base.model.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    /**
     * Add a new user
     *
     * @param userDTO the user to add
     * @return the added user
     */
    UserDTO addUser(UserDTO userDTO);

    /**
     * Find a user by id
     *
     * @return the found user
     */
    UserDTO findUser(Long id);

    /**
     * Find all users
     *
     * @return a list of all users
     */
    List<UserDTO> findAllUsers();

    /**
     * Update a user by id
     *
     * @param id      the id of the user to update
     * @param userDTO the User object with the new values to update
     * @return the updated user
     */
    UserDTO updateUser(Long id, UserDTO userDTO);

    /**
     * Delete a user by id
     *
     * @param id the id of the user to delete
     * @return true if the user was deleted
     */
    boolean deleteUser(Long id);
}
