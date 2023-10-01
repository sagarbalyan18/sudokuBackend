package com.underscore.sudokuprime.repository;

import com.underscore.sudokuprime.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserModel, Integer> {

    UserModel getUserByUserId(String userId);

}
