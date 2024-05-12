package com.tuts.ots.onetimeshop.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CategoriesTb")
@NoArgsConstructor
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title",length = 100,nullable = false)
    private String categorytitle;



    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL,fetch = FetchType.LAZY )
    private List<ProductEnitity> productEnitities = new ArrayList<>();

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL,fetch = FetchType.LAZY )
    private List<Vender> venders = new ArrayList<>();


    @ManyToOne
    @JoinColumn(name = "User_Id")
    private User user;

}
