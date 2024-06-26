package com.itschool.base.service.impl;

import com.itschool.base.entity.User;
import com.itschool.base.model.UserDTO;
import com.itschool.base.repository.UserRepository;
import com.itschool.base.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO addUser(UserDTO userDTO) {
        User user = new User(userDTO.name(), userDTO.email(), userDTO.age());
        User createdUser = userRepository.save(user);
        return new UserDTO(createdUser.getName(), createdUser.getEmail(), createdUser.getAge());
    }

    @Override
    public UserDTO findUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return new UserDTO(user.getName(), user.getEmail(), user.getAge());
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = new User(id, userDTO.name(), userDTO.email(), userDTO.age());
        User createdUser = userRepository.save(user);
        return new UserDTO(createdUser.getName(), createdUser.getEmail(), createdUser.getAge());
    }

    @Override
    public boolean deleteUser(Long id) {
        userRepository.deleteById(id);
        return true;
    }
}
