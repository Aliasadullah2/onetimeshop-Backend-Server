package com.tuts.ots.onetimeshop.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Role {

    @Id
    private int id;

    @Column(name = "name")
    private String name;

//    @ManyToMany
//    private User user;

}
