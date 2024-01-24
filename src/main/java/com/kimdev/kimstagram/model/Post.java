package com.kimdev.kimstagram.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @ManyToOne
    @JoinColumn(name="accountid")
    @OnDelete(action = OnDeleteAction.CASCADE)
    public Account account;

    public int likecount;

    @Lob
    public String comment;

    public int pic_size;

    @CreationTimestamp
    private Timestamp createDate;
}
