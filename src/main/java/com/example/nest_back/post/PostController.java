package com.example.nest_back.post;


import com.example.nest_back.user.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {
    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        Post createdPost = postService.createPost(post);
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
            @PathVariable Integer postId,
            @RequestBody Comment comment
    ) {
        postService.deleteCommentFromPost(postId, comment);
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
