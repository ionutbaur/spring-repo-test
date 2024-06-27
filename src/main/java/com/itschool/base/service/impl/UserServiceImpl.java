package com.itschool.base.service.impl;

import com.itschool.base.entity.Address;
import com.itschool.base.entity.Order;
import com.itschool.base.entity.User;
import com.itschool.base.model.AddressDTO;
import com.itschool.base.model.OrderDTO;
import com.itschool.base.model.UserDTO;
import com.itschool.base.repository.UserRepository;
import com.itschool.base.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
        User user = convertToUser(userDTO);
        user.setId(id);

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

        // get the orderDTOs from the userDTO
        List<OrderDTO> orderDTOS = userDTO.orders();

        // convert the orderDTOs to Order entities
        List<Order> orders = Optional.ofNullable(orderDTOS)
                .orElse(Collections.emptyList())
                .stream()
                .map(orderDTO -> new Order(orderDTO.description()))
                .toList();

        // create a new User entity with userDTO info and the address entity
        return new User(userDTO.name(), userDTO.email(), userDTO.age(), address, orders);
    }

    private UserDTO convertToUserDTO(User user) {
        // get the Address entity from the User entity
        Address address = user.getAddress();

        // convert the Address entity to an AddressDTO
        AddressDTO addressDTO = new AddressDTO(address.getCity(), address.getStreet(), address.getNumber(), address.getZipCode());

        List<OrderDTO> orderDTOS = user.getOrders()
                .stream()
                .map(order -> new OrderDTO(order.getId(), order.getDescription()))
                .toList();

        // return a new UserDTO based on the info from User entity and the AddressDTO
        return new UserDTO(user.getName(), user.getEmail(), user.getAge(), addressDTO, orderDTOS);
    }
}
