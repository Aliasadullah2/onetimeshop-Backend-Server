package com.tuts.ots.onetimeshop.repositires;

import com.tuts.ots.onetimeshop.entities.Category;
import com.tuts.ots.onetimeshop.entities.ProductEnitity;
import com.tuts.ots.onetimeshop.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface ProductRepo extends JpaRepository<ProductEnitity,Integer> {
     List<ProductEnitity> findByUser(User user);
     List<ProductEnitity> findByCategory (Category category);

     List<ProductEnitity> findByProdNameContaining(String prodName);


}
