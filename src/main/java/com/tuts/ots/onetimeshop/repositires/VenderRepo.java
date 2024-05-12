package com.tuts.ots.onetimeshop.repositires;

import com.tuts.ots.onetimeshop.entities.Category;
import com.tuts.ots.onetimeshop.entities.ProductEnitity;
import com.tuts.ots.onetimeshop.entities.User;
import com.tuts.ots.onetimeshop.entities.Vender;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VenderRepo extends JpaRepository<Vender,Integer> {
    List<Vender> findByUser(User user);
    List<Vender> findByCategory (Category category);
}
