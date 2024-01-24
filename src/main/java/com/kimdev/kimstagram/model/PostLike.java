package com.kimdev.kimstagram.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Timestamp;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // 좋아요를 누른 사람
    @ManyToOne
    @JoinColumn(name="accountid")
    @OnDelete(action = OnDeleteAction.CASCADE)
    public Account account;

    // 좋아요 대상 게시글
    @ManyToOne
    @JoinColumn(name="postid")
    @OnDelete(action = OnDeleteAction.CASCADE)
    public Post post;

    @CreationTimestamp
    private Timestamp createDate;
}
