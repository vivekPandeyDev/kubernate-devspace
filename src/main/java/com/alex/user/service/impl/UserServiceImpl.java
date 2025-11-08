package com.alex.user.service.impl;


import com.alex.user.dao.UserRepository;
import com.alex.user.model.User;
import com.alex.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public List<User> getAllUsers() {
        log.info("Testing dev container");
        return repository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public User saveUser(User user) {
        return repository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        repository.deleteById(id);
    }
}
