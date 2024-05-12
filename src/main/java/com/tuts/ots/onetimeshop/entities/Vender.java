package com.tuts.ots.onetimeshop.entities;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "Vender")
@Data
public class Vender {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer venId;

    @Column(name = "Name")
    private String name;

    @Column(name = "Title")
    private String title;

    @Column(name = "About_us",length = 100000  )
    private String about;

    @Column(name = "Price")
    private String price;

    @Column(name = "Vender_Date")
    private Date addedDate;

    @Column(name = "Phone_Number")
    private String phonenumber;

    @Column(name = "Vender_Image")
    private String venImg;

    @ManyToOne
    @JoinColumn(name = "Category_Id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "User_Id")
    private User user;















}
