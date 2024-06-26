package com.itschool.base.service;

import com.itschool.base.model.UserDTO;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    UserDTO addUser(UserDTO userDTO);

    UserDTO findUser(Long id);

    UserDTO updateUser(Long id, UserDTO userDTO);

    boolean deleteUser(Long id);
}
