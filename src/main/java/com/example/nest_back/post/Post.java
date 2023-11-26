package com.example.nest_back.post;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import com.example.nest_back.user.User;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue
    private Integer id;

    @Lob
    private byte[] photo;

    @Column(length = 1000)
    private String description;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostLike> likes = new ArrayList<PostLike>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setPost(this); // Set the association in the bidirectional relationship
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setPost(null); // Remove the association in the bidirectional relationship
    }

    public void addLike(User user) {
        PostLike like = new PostLike();
        like.setUser(user);
        likes.add(like);
    }

    public void removeLike(User user) {
        likes.removeIf(like -> like.getUser().equals(user));
    }
}
