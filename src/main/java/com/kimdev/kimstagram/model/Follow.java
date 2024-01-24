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
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @ManyToOne
    @JoinColumn(name="fromaccount")
    @OnDelete(action = OnDeleteAction.CASCADE)
    public Account fromaccount;

    @ManyToOne
    @JoinColumn(name="toaccount")
    @OnDelete(action = OnDeleteAction.CASCADE)
    public Account toaccount;

    @CreationTimestamp
    private Timestamp createDate;
}
