package com.tuts.ots.onetimeshop.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "CommentTb")
@NoArgsConstructor
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer comId;

    @Column(name = "Content",length = 100,nullable = false)
    private String Content;

    @Column(name = "Comment_Date")
    private Date addedCommentDate;

    @ManyToOne
    private ProductEnitity productEnitity;

    @ManyToOne
    @JoinColumn(name = "User_Id")
    private User user;


}
