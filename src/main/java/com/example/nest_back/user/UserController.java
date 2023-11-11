package com.example.nest_back.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}/followers")
    public ResponseEntity<List<User>> getFollowers(@PathVariable Integer userId) {
        // Delegate to the service
        List<User> followers = userService.getFollowers(userId);

        return ResponseEntity.ok(followers);
    }

    @PostMapping("/{host_user_id}/add_follower/{follower_user_id}")
    public ResponseEntity<String> addFollower(
            @PathVariable Integer host_user_id,
            @PathVariable Integer follower_user_id
    ) {
        // Delegate to the service
        boolean success = userService.addFollower(host_user_id, follower_user_id);

        if (success) {
            return ResponseEntity.ok("Follower added successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/{host_user_id}/delete_follower/{follower_user_id}")
    public ResponseEntity<String> deleteFollower(
            @PathVariable Integer host_user_id,
            @PathVariable Integer follower_user_id
    ) {
        // Delegate to the service
        boolean success = userService.deleteFollower(host_user_id, follower_user_id);

        if (success) {
            return ResponseEntity.ok("Follower deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{userId}/bio")
    public ResponseEntity<String> updateBio(
            @PathVariable Integer userId,
            @RequestParam String newBio
    ) {
        userService.updateBio(userId, newBio);
        return ResponseEntity.ok("Bio updated successfully");
    }

}
