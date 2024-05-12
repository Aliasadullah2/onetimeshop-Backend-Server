package com.tuts.ots.onetimeshop.services.impl;

import com.tuts.ots.onetimeshop.entities.Category;
import com.tuts.ots.onetimeshop.entities.User;
import com.tuts.ots.onetimeshop.execptions.ResourceNotFoundException;
import com.tuts.ots.onetimeshop.payloads.CatagoryDto;
import com.tuts.ots.onetimeshop.payloads.CategoryResponse;
import com.tuts.ots.onetimeshop.repositires.CategoryRepo;
import com.tuts.ots.onetimeshop.repositires.UserRepo;
import com.tuts.ots.onetimeshop.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CatagoryServiceImpl implements CategoryService {


    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Override
    public CatagoryDto createCatagory(CatagoryDto catagoryDto, Integer userId) {
        User user=this.userRepo.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User","User id",userId));

        Category cat = this.modelMapper.map(catagoryDto,Category.class);
        cat.setUser(user);
        Category addedcat= this.categoryRepo.save(cat);
        return this.modelMapper.map(addedcat,CatagoryDto.class);
    }

    @Override
    public CatagoryDto updateCatagory(CatagoryDto catagoryDto, Integer catagoryId) {
        Category cat = this.categoryRepo.findById(catagoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category","Category Id",catagoryId));
        cat.setCategorytitle(catagoryDto.getCategorytitle());


        Category updatedcat = this.categoryRepo.save(cat);


        return this.modelMapper.map(updatedcat,CatagoryDto.class);
    }

    @Override
    public CatagoryDto getCatagoryById(Integer catagoryId) {
        Category cat = this.categoryRepo.findById(catagoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category","Category Id",catagoryId));

        return this.modelMapper.map(cat,CatagoryDto.class);
    }

    @Override
    public CategoryResponse getAllCatagory(Integer pageNumber, Integer pageSize,String sortBy) {

        Pageable p = PageRequest.of(pageNumber,pageSize);

        Page<Category> pagecategory= this.categoryRepo.findAll(p);
        List<Category> allcategory =pagecategory.getContent();


        List<CatagoryDto> catDtos=allcategory.stream().map((cat)->this.modelMapper.map(cat,CatagoryDto.class)).collect(Collectors.toList());
        CategoryResponse categoryResponse = new CategoryResponse();

        categoryResponse.setContent(catDtos);
        categoryResponse.setPageNumber(pagecategory.getNumber());
        categoryResponse.setPageSize(pagecategory.getSize());
        categoryResponse.setTotalElement(pagecategory.getTotalElements());

        categoryResponse.setTotalPages(pagecategory.getTotalPages());
        categoryResponse.setLastPage(pagecategory.isLast());
        return categoryResponse ;
    }

    @Override
    public List<CatagoryDto> getAllSimpleCatagories() {
        List<Category> getCategory = this.categoryRepo.findAll();
        List<CatagoryDto> collect = getCategory.stream().map((cat) -> this.modelMapper.map(cat, CatagoryDto.class)).collect(Collectors.toList());
        return collect;
    }


    @Override
    public void deleteCatagory(Integer catagoryId) {
        Category cat = this.categoryRepo.findById(catagoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category","Category Id",catagoryId));
        this.categoryRepo.delete(cat);
    }
}
