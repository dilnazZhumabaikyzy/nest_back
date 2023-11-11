package com.example.nest_back.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserRelationship {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "host_user_id")
    private User hostUser;

    @ManyToOne
    @JoinColumn(name = "follower_user_id")
    private User followerUser;
}