package com.tuts.ots.onetimeshop.repositires;

import com.tuts.ots.onetimeshop.entities.Comment;
import com.tuts.ots.onetimeshop.entities.ProductEnitity;
import com.tuts.ots.onetimeshop.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment,Integer> {
    List<Comment> findByUser(User user);
}
