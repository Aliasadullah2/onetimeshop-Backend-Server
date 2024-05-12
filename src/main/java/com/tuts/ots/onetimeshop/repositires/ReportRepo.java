package com.tuts.ots.onetimeshop.repositires;

import com.tuts.ots.onetimeshop.entities.Report;

import com.tuts.ots.onetimeshop.entities.User;
import com.tuts.ots.onetimeshop.entities.Vender;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepo extends JpaRepository<Report,Integer> {
    List<Report> findByUser(User user);
}
