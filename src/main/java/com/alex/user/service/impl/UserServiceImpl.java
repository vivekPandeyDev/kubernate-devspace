package com.alex.user.service.impl;


import com.alex.user.dao.UserRepository;
import com.alex.user.model.User;
import com.alex.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    public UserServiceImpl(@Qualifier("jpaUserRepository") UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<User> getAllUsers() {
        log.info("fetching all users information");
        return repository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        log.info("fetching user info by user id: {}", id);
        return repository.findById(id).orElse(null);
    }

    @Override
    public void saveUser(User user) {
        log.info("saving user information: {}", user);
        repository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        log.info("deleting user by user id: {}", id);
        repository.deleteById(id);
    }
}
