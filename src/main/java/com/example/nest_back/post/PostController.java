package com.example.nest_back.post;


import com.example.nest_back.user.User;
import com.example.nest_back.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {
    private PostService postService;
    private UserService userService;

    @Autowired
    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService  = userService;
    }

    @PostMapping("/{user_id}")
    public ResponseEntity<Post> createPost(@RequestBody Post post, @PathVariable("user_id") Integer userId,
                                           Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Retrieve the authenticated user's ID from the SecurityContextHolder
        User userDetails = (User) authentication.getPrincipal();
        String authenticatedUsername = userDetails.getUsername();
        // You may need to parse the authenticated user's username or ID based on your UserDetails implementation

        // Compare the authenticated user's ID with the user_id provided in the request
        // Assume you have a method to fetch the user's ID based on the username
        Integer authenticatedUserId = userService.getUserIdByUsername(authenticatedUsername);

        if (!authenticatedUserId.equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // If the authenticated user ID matches the requested user ID, proceed to create the post
        Post createdPost = postService.createPost(post, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<String> editPost(@PathVariable Integer postId, @RequestBody Post post) {
        // Assuming your PostService has an updatePost method
        Post updatedPost = postService.updatePost(postId, post);

        if (updatedPost != null) {
            return ResponseEntity.ok("Post updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
        }
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Integer postId) {
        boolean postDeleted = postService.deletePost(postId);

        if (postDeleted) {
            return ResponseEntity.ok("Post deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
        }
    }

    @Transactional
    @PostMapping("/{postId}/comments")
    public ResponseEntity<String> addCommentToPost(
            @PathVariable Integer postId,
            @RequestBody Comment comment,
            @RequestParam Integer userId
    ) {
        postService.addCommentToPost(postId, comment, userId);
        return ResponseEntity.ok("Comment added successfully");
    }

    @Transactional(readOnly = true)
    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<Comment>>  getCommentsForPost(@PathVariable Integer postId) {
        List<Comment> comments  = postService.getCommentsForPost(postId);
        return ResponseEntity.ok(comments);
    }

    @DeleteMapping("/{postId}/comments")
    public ResponseEntity<String> deleteCommentFromPost(
            @PathVariable Integer postId,@RequestParam Integer commentID
    ) {
        postService.deleteCommentFromPost(postId, commentID);
        return ResponseEntity.ok("Comment deleted successfully");
    }

    @PutMapping("/{postId}/comments/")
    public ResponseEntity<String> updateComment(
            @PathVariable Integer postId,@RequestParam Integer commentID,
            @RequestBody Comment comment
    ) {
        postService.updateComment(postId, comment, commentID);
        return ResponseEntity.ok("Comment updated successfully");
    }


//    @PostMapping("/{postId}/likes")
//    public ResponseEntity<String> addLikeToPost(
//            @PathVariable Integer postId,
//            @RequestBody User user
//    ) {
//        postService.addLikeToPost(postId, user);
//        return ResponseEntity.ok("Like added successfully");
//    }
}
