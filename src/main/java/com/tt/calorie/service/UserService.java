package com.tt.calorie.service;

import com.tt.calorie.common.authorization.Role;
import com.tt.calorie.model.User;
import com.tt.calorie.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByCredentials(String username, String password) {
        if (password != null) {
            return userRepository.findByUsernameAndPassword(username, password);
        }
        return userRepository.findByUsername(username);
    }

    public User createUser(User user) {
        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }
        if (user.getLastUpdated() == null) {
            user.setLastUpdated(LocalDateTime.now());
        }
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        if (user.getLastUpdated() == null) {
            user.setLastUpdated(LocalDateTime.now());
        }
        return userRepository.save(user);
    }

    public User patchUser(Long userId, Long calorieLimit) {
        User savedUser = userRepository.getById(userId);
        savedUser.setCalorieLimit(calorieLimit);
        savedUser.setLastUpdated(LocalDateTime.now());
        return userRepository.save(savedUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
