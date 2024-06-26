package com.itschool.base.service.impl;

import com.itschool.base.entity.Address;
import com.itschool.base.entity.User;
import com.itschool.base.model.AddressDTO;
import com.itschool.base.model.UserDTO;
import com.itschool.base.repository.UserRepository;
import com.itschool.base.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Component
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) { // inject the UserRepository
        this.userRepository = userRepository;
    }

    /**
     * Add a new user
     *
     * @param userDTO the user to add
     * @return the added user
     */
    @Override
    public UserDTO addUser(UserDTO userDTO) {
        User user = convertToUser(userDTO);

        // save the User entity (and also the Address entity, because User class contains an object of type Address class) into database
        User createdUser = userRepository.save(user);

        return convertToUserDTO(createdUser);
    }

    /**
     * Find a user by id
     *
     * @return the found user
     */
    @Override
    public UserDTO findUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id: " + id + " not found"));

        return convertToUserDTO(user);
    }

    /**
     * Find all users
     *
     * @return a list of all users
     */
    @Override
    public List<UserDTO> findAllUsers() {
        List<User> allUsers = userRepository.findAll();

        return allUsers.stream()
                .map(this::convertToUserDTO)
                .toList();
    }

    /**
     * Update a user by id
     *
     * @param id      the id of the user to update
     * @param userDTO the User object with the new values to update
     * @return the updated user
     */
    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        AddressDTO addressDTO = userDTO.address();
        Address address = new Address(addressDTO.city(), addressDTO.street(), addressDTO.number(), addressDTO.zipCode());
        User user = new User(id, userDTO.name(), userDTO.email(), userDTO.age(), address);

        User updatedUser = userRepository.save(user);

        return convertToUserDTO(updatedUser);
    }

    /**
     * Delete a user by id
     *
     * @param id the id of the user to delete
     * @return true if the user was deleted
     */
    @Override
    public boolean deleteUser(Long id) {
        userRepository.deleteById(id);
        return true;
    }

    private User convertToUser(UserDTO userDTO) {
        // get the addressDTO from the userDTO
        AddressDTO addressDTO = userDTO.address();

        // convert the addressDTO to an Address entity
        Address address = new Address(addressDTO.city(), addressDTO.street(), addressDTO.number(), addressDTO.zipCode());

        // create a new User entity with userDTO info and the address entity
        return new User(userDTO.name(), userDTO.email(), userDTO.age(), address);
    }

    private UserDTO convertToUserDTO(User user) {
        // get the Address entity from the User entity
        Address address = user.getAddress();

        // convert the Address entity to an AddressDTO
        AddressDTO addressDTO = new AddressDTO(address.getCity(), address.getStreet(), address.getNumber(), address.getZipCode());

        // return a new UserDTO based on the info from User entity and the AddressDTO
        return new UserDTO(user.getName(), user.getEmail(), user.getAge(), addressDTO);
    }
}
