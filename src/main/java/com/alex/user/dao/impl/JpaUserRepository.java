package com.alex.user.dao.impl;

import com.alex.user.dao.UserRepository;
import com.alex.user.mapper.UserMapper;
import com.alex.user.model.User;
import com.alex.user.persistence.UserJpaPersistence;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaUserRepository implements UserRepository {

    private final UserJpaPersistence userPersistence;

    @Override
    public List<User> findAll() {
        return userPersistence.findAll().stream().map(UserMapper.INSTANCE::toDomain).toList();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userPersistence.findById(id).map(UserMapper.INSTANCE::toDomain);
    }

    @Override
    public User save(User user) {
        var savedUser = userPersistence.save(UserMapper.INSTANCE.toEntity(user));
        return UserMapper.INSTANCE.toDomain(savedUser);
    }

    @Override
    public void deleteById(Long id) {
        userPersistence.deleteById(id);
    }
}
