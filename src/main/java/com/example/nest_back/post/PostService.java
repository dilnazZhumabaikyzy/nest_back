package com.example.nest_back.post;


import com.example.nest_back.user.User;
import com.example.nest_back.user.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;



    public Post createPost(Post post) {
        return postRepository.save(post);
    }


    public List<Comment> getCommentsForPost(Integer postId) {
        Post post = postRepository.getById(postId);
        List<Comment> comments = new ArrayList<>(post.getComments());
        //System.out.println(comments.toString());

        return comments;
    }
    public void addCommentToPost(Integer postId, Comment comment, Integer userId) {
        Post post = postRepository.getById(postId);
        User commentingUser = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        comment.setCommentingUser(commentingUser);
        post.addComment(comment);
        postRepository.save(post);
    }
    public void deleteCommentFromPost(Integer postId, Comment comment) {
        Post post = postRepository.getById(postId);
        postRepository.save(post);
    }

    public void updateComment(Integer postId, Comment updatedComment, Integer commentId) {
        Post post = postRepository.getById(postId);

        // Get the comments from the post
        List<Comment> comments = post.getComments();

        // Update the existing comment or add the new comment if not found
        boolean commentUpdated = false;
        for (Comment comment : comments) {
            if (comment.getId().equals(commentId)) {
                // Update the existing comment
                comment.setCommentText(updatedComment.getCommentText());
                commentUpdated = true;
                break;
            }
        }

        // If the comment wasn't found, add the new comment
        if (!commentUpdated) {
            comments.add(updatedComment);
        }

        // Set the updated comments list to the post
        post.setComments(comments);

        // Save the updated post
        postRepository.save(post);
    }

    public Post updatePost(Integer postId, Post updatedPost) {
        Optional<Post> existingPostOptional = postRepository.findById(postId);

        if (existingPostOptional.isPresent()) {
            Post existingPost = existingPostOptional.get();
            existingPost.setPhoto(updatedPost.getPhoto());
            existingPost.setDescription(updatedPost.getDescription());
            return postRepository.save(existingPost);
        } else {
            return null;
        }
    }

    public boolean deletePost(Integer postId) {
        Optional<Post> existingPostOptional = postRepository.findById(postId);

        if (existingPostOptional.isPresent()) {
            Post existingPost = existingPostOptional.get();

            postRepository.delete(existingPost);
            return true;
        } else {
            return false;
        }
    }

}
