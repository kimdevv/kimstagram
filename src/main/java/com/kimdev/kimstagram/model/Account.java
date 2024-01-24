package com.kimdev.kimstagram.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 30, unique = true)
    private String username;

    @Column(length = 100)
    private String password;

    private String email;

    private String name;

    private int use_profile_img;

    private String profile_img;

    private String comment;

    private int follower;

    private int following;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    @CreationTimestamp
    private Timestamp createDate;
}
