package com.example.nest_back.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRelationshipRepository extends JpaRepository<UserRelationship, Integer>  {
    List<UserRelationship> findByHostUserId(Integer hostUserId);
    Optional<UserRelationship> findByHostUserIdAndFollowerUserId(Integer hostUserId, Integer followerUserId);

}
