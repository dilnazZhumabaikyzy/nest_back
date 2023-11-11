package com.example.nest_back.post;


import com.example.nest_back.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/{postId}/comments")
    public ResponseEntity<String> addCommentToPost(
            @PathVariable Integer postId,
            @RequestBody Comment comment
    ) {
        postService.addCommentToPost(postId, comment);
        return ResponseEntity.ok("Comment added successfully");
    }

    @PostMapping("/{postId}/likes")
    public ResponseEntity<String> addLikeToPost(
            @PathVariable Integer postId,
            @RequestBody User user
    ) {
        postService.addLikeToPost(postId, user);
        return ResponseEntity.ok("Like added successfully");
    }
}
