package com.example.nest_back.post;

import com.example.nest_back.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "post_id")
    private Post post;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User commentingUser;
    @Column(length = 500)
    private String commentText;
}
