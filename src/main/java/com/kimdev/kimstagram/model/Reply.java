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
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Lob
    public String comment;

    @ManyToOne
    @JoinColumn(name="accountid")
    @OnDelete(action = OnDeleteAction.CASCADE)
    public Account account;

    @ManyToOne
    @JoinColumn(name="postid")
    @OnDelete(action = OnDeleteAction.CASCADE)
    public Post post;

    @CreationTimestamp
    private Timestamp createDate;
}
