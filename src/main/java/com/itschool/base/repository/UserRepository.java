package com.itschool.base.repository;

import com.itschool.base.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> { // this repository will inherit all the methods from JpaRepository (e.g. CRUD operations)
}
