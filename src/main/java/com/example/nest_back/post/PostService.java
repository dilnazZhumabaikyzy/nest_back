package com.example.nest_back.post;


import com.example.nest_back.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService{

    @Autowired
    private PostRepository postRepository;

    public Post createPost(Post post) {
        return postRepository.save(post);
    }
//    public Post deletePost(Post post) {
//        return postRepository.save(post);
//    }
//
//    public Post editPost(Post post) {
//        return postRepository.save(post);
//    }

    public void addCommentToPost(Integer postId, Comment comment) {
        Post post = postRepository.getById(postId);
        post.addComment(comment);
        postRepository.save(post);
    }

    public void addLikeToPost(Integer postId, User user) {
        Post post = postRepository.getById(postId);
        post.addLike(user);
        postRepository.save(post);
    }

}
