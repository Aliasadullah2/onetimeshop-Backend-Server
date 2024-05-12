package com.tuts.ots.onetimeshop.services.impl;

import com.tuts.ots.onetimeshop.entities.*;
import com.tuts.ots.onetimeshop.execptions.ResourceNotFoundException;
import com.tuts.ots.onetimeshop.repositires.*;
import com.tuts.ots.onetimeshop.services.DeleteService;
import com.tuts.ots.onetimeshop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class DeleteServiceImpl implements DeleteService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private ReportRepo reportRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private VenderRepo venderRepo;

    @Override
    public void deleteUser(Integer userId) {
        User user = this.userRepo.findById(userId).orElse(null);
        if (user != null) {

                    List<Role> roles = new ArrayList<>(user.getRoles());
                    for (Role role : roles) {
                        this.deleteroles(role.getId());
                    }
                    user.getRoles().clear();

                    List<ProductEnitity> productEnitities = new ArrayList<>(user.getProductEnitities());
                    for (ProductEnitity productEnitity : productEnitities) {
                        this.deleteProductDto(productEnitity.getProdId());
                    }
                    user.getProductEnitities().clear();


                    List<Comment> comments = new ArrayList<>(user.getComments());
                    for (Comment commentDto : comments) {
                        this.deletcomment(commentDto.getComId());
                    }
                    user.getComments().clear();


                    List<Category> categories = new ArrayList<>(user.getCategories());
                    for (Category categorys : categories) {
                        this.deletcomment(categorys.getId());
                    }

                    List<Vender> venders = new ArrayList<>(user.getVenders());
                    for (Vender vender : venders) {
                        this.deleteVenderDto(vender.getVenId());
                    }
                    List<Report> reports = new ArrayList<>(user.getReports());
                    for (Report report : reports) {
                        this.deleteReportDto(report.getRepId());
                    }



                    user.getCategories().clear();
                    user.getVenders().clear();
                    user.getReports().clear();

            this.userRepo.delete(user);
        }
    }


    @Override
    public void deleteCatagory(Integer catagoryId) {
        Category cat = this.categoryRepo.findById(catagoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category","Category Id",catagoryId));
            if (cat != null) {
                List<ProductEnitity> productEnitities = new ArrayList<>(cat.getProductEnitities());
                for (ProductEnitity productEnitity1 : productEnitities) {
                    this.deleteProductDto(productEnitity1.getProdId());
                }
                cat.getProductEnitities().clear();

            User user = cat.getUser();
            user.getProductEnitities().remove(cat);
            this.userRepo.save(user);
            this.categoryRepo.delete(cat);
        }

    }

    @Override
    public void deleteroles(Integer id) {
        Role role = this.roleRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Roles"," roles Id",id));
        this.roleRepo.delete(role);
    }

    @Override
    public void deletcomment(Integer comId) {
        Comment comment=this.commentRepo.findById(comId)
                .orElseThrow(()-> new ResourceNotFoundException("Comment","Comment id",comId));
        User user=comment.getUser();
        user.getComments().remove(comment);
        this.userRepo.save(user);
        ProductEnitity productEnitity1=comment.getProductEnitity();
        productEnitity1.getComments().remove(comment);
        this.productRepo.save(productEnitity1);
        this.commentRepo.delete(comment);
    }

    @Override
    public void deleteProductDto(Integer productId) {
        ProductEnitity productEnitity=this.productRepo.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("ProductEnitity","product id",productId));
        if (productEnitity != null) {
            List<Comment> comments = new ArrayList<>(productEnitity.getComments());
            for (Comment comment1 : comments) {
                this.deletcomment(comment1.getComId());
            }
            productEnitity.getComments().clear();
            Category category=productEnitity.getCategory();
            category.getProductEnitities().remove(productEnitity);
            User user = productEnitity.getUser();
            user.getProductEnitities().remove(productEnitity);
            this.categoryRepo.save(category);
            this.userRepo.save(user);
        }


    }

    @Override
    public void deleteReportDto(Integer repId) {
        Report report=this.reportRepo.findById(repId)
                .orElseThrow(()-> new ResourceNotFoundException("Report","rep id",repId));


        if (report != null) {

            User user = report.getUser();
            user.getReports().remove(report);
            this.userRepo.save(user);
            this.reportRepo.delete(report);
        }
    }



    @Override
    public void deleteVenderDto(Integer venId) {
        Vender vender=this.venderRepo.findById(venId)
                .orElseThrow(()-> new ResourceNotFoundException("Vender","venn id",venId));

        if (vender != null) {
            Category category=vender.getCategory();
            category.getProductEnitities().remove(category);
            User user = vender.getUser();
            user.getVenders().remove(vender);
            this.categoryRepo.save(category);
            this.userRepo.save(user);
            this.venderRepo.delete(vender);
        }
    }
}
