package com.alex.user.persistence;

import com.alex.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserJpaPersistence extends JpaRepository<UserEntity,Long> {
}
