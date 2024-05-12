package com.tuts.ots.onetimeshop.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "Report")
@Data
public class Report  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int repId;

    @Column(name = "Title")
    private String title;

    @Column(name = "About_us")
    private String about;

    @Column(name = "Product_Date")
    private Date addedDate;

    @ManyToOne
    @JoinColumn(name = "User_Id")
    private User user;
}
