package com.tuts.ots.onetimeshop.services;

import com.tuts.ots.onetimeshop.payloads.CatagoryDto;
import com.tuts.ots.onetimeshop.payloads.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CatagoryDto createCatagory(CatagoryDto catagoryDto, Integer userId);

    CatagoryDto updateCatagory(CatagoryDto catagoryDto,Integer catagoryId);

    CatagoryDto getCatagoryById(Integer catagoryId);

    CategoryResponse getAllCatagory(Integer pageNumber, Integer pageSize,String sortBy);

    List<CatagoryDto> getAllSimpleCatagories();

    void deleteCatagory(Integer catagoryId);
}
