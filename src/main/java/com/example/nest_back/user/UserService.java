package com.example.nest_back.user;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public List<User> getFollowers(Long userId) {
        return userRepository.getFollowers(userId);
    }
    public void addFollower(Long userId, User follower) {
        // Business logic for adding a follower
    }

}
