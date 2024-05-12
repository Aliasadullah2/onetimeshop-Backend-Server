package com.tuts.ots.onetimeshop.repositires;

import com.tuts.ots.onetimeshop.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category,Integer> {

}
