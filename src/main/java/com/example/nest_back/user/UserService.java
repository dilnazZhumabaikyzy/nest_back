package com.example.nest_back.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserRelationshipRepository userRelationshipRepository;
    @Autowired
    public UserService(UserRepository userRepository, UserRelationshipRepository userRelationshipRepository) {
        this.userRepository = userRepository;
        this.userRelationshipRepository = userRelationshipRepository;
    }

    public List<User> getFollowers(Integer userId) {
        List<UserRelationship> userRelationships = userRelationshipRepository.findByHostUserId(userId);
        return userRelationships.stream()
                .map(UserRelationship::getFollowerUser)
                .collect(Collectors.toList());
    }

    public boolean addFollower(Integer hostUserId, Integer followerUserId) {
        Optional<UserRelationship> existingRelationship = userRelationshipRepository
                .findByHostUserIdAndFollowerUserId(hostUserId, followerUserId);

        if (existingRelationship.isEmpty()) {
            User hostUser = userRepository.getById(hostUserId);
            User followerUser = userRepository.getById(followerUserId);

            UserRelationship userRelationship = UserRelationship.builder()
                    .hostUser(hostUser)
                    .followerUser(followerUser)
                    .build();

            userRelationshipRepository.save(userRelationship);
            return true;
        }

        return false;
    }

    public void updateBio(Integer userId, String newBio) {
        User user = userRepository.getById(userId);
        user.setBio(newBio);
        userRepository.save(user);
    }


    public boolean deleteFollower(Integer hostUserId, Integer followerUserId) {
        Optional<UserRelationship> existingRelationship = userRelationshipRepository
                .findByHostUserIdAndFollowerUserId(hostUserId, followerUserId);

        if (existingRelationship.isPresent()) {
            // Delete the relationship if it exists
            userRelationshipRepository.delete(existingRelationship.get());
            return true;
        } else {
            // Return false if the relationship doesn't exist
            return false;
        }
    }
}
