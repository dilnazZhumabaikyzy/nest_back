package com.example.nest_back.post;

import com.example.nest_back.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue
    private Integer id;
    private byte[] photo;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User commentingUser;
    @Column(length = 500)
    private String commentText;

//    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
//    private List<Like> likes = new ArrayList<>();
//
//
//    public void addLike(User user) {
//        Like like = new Like();
//        like.setUser(user);
//        likes.add(like);
//    }
//
//    public void removeLike(User user) {
//        likes.removeIf(like -> like.getUser().equals(user));
//    }

}
