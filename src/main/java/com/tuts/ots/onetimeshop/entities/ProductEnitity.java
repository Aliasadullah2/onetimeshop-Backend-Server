package com.tuts.ots.onetimeshop.entities;

import com.tuts.ots.onetimeshop.payloads.CommentDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Product")
@NoArgsConstructor
@Getter
@Setter
public class ProductEnitity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer prodId;

    @Column(name = "Product_Name",length = 10000,nullable = false)
    private String prodName;



    @Column(name = "Desprition",length = 100000  )
    private String prodDesc;

    @Column(name = "Product_Price")
    private String prodPrice;

    @Column(name = "Product_Image")
    private String prodImg;

    @Column(name = "Product_Date")
    private Date addedDate;

//    @Column(name = "Product_Image_Name")
//    private String prodImageName;
//


    @ManyToOne
    @JoinColumn(name = "Category_Id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "User_Id")
    private User user;

    @OneToMany(mappedBy = "productEnitity",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Comment> comments=new HashSet<>();
}
