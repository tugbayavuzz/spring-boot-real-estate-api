package com.realestate.repository;

import com.realestate.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByEmail(String email);

}
