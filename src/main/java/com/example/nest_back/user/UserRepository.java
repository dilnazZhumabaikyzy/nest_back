package com.example.nest_back.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String email);

    @Query("SELECT ur.followerUser FROM UserRelationship ur WHERE ur.hostUser.id = :userId")
    List<User> getFollowers(@Param("userId") Long userId);
}
