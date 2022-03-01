package com.fisher_map.fisher_map.service.impl;

import com.fisher_map.fisher_map.model.Role;
import com.fisher_map.fisher_map.model.Status;
import com.fisher_map.fisher_map.model.User;
import com.fisher_map.fisher_map.repository.RoleRepository;
import com.fisher_map.fisher_map.repository.UserRepository;
import com.fisher_map.fisher_map.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User register(User user) {
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);
        User registeredUser = userRepository.save(user);

        log.info("IN register - user : {} successfully registered", registeredUser);
        return registeredUser;

    }

    @Override
    public List<User> getAll() {
        List<User> users = userRepository.findAll();
        log.info("IN getAll - {} users found", users.size());

        return users;
    }

    @Override
    public User findByUsername(String name) {
        User user = userRepository.findByUsername(name);
        log.info("IN findByUsername - user: {} successfully found", user);
        return user;
    }

    @Override
    public User findById(UUID id) {
        User user = userRepository.findById(id).orElse(null);
        if(user == null){
            log.warn("IN findById - no user found by id: {}", id);
            return null;
        }
        log.info("IN findById - user: {} found by id: {}", user, id);
        return user;
    }

    @Override
    public void delete(UUID id) {
        userRepository.deleteById(id);
        log.info("IN delete - user with id: {} successfully deleted", id);
    }
}
