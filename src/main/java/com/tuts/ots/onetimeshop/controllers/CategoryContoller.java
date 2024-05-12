package com.tuts.ots.onetimeshop.controllers;

import com.tuts.ots.onetimeshop.configs.AppConstants;
import com.tuts.ots.onetimeshop.entities.User;
import com.tuts.ots.onetimeshop.execptions.ResourceNotFoundException;
import com.tuts.ots.onetimeshop.payloads.*;
import com.tuts.ots.onetimeshop.services.CategoryService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryContoller {
    @Autowired
    private CategoryService categoryService;

    //create cetagory api
    @PostMapping("/user/{userId}")
    public ResponseEntity<CatagoryDto> createCatagory(@Valid @RequestBody CatagoryDto catagoryDto, @PathVariable Integer userId){

        CatagoryDto createCatagoryDto = this.categoryService.createCatagory(catagoryDto,userId);
        return new ResponseEntity<CatagoryDto>(createCatagoryDto, HttpStatus.CREATED);
    }

    //updated cetagory api
    @PutMapping("/{catagoryId}")
    public ResponseEntity<CatagoryDto> updateCatagory(@Valid @RequestBody CatagoryDto catagoryDto,@PathVariable("catagoryId") Integer catId){
        CatagoryDto updateCatagory =this.categoryService.updateCatagory(catagoryDto,catId);
        return new ResponseEntity<CatagoryDto>(updateCatagory, HttpStatus.OK);
    }

    //Delete cetagory api
    @DeleteMapping("/{catagoryId}")
    public ResponseEntity<ApiResponse> deleteCatagory(@PathVariable("catagoryId") Integer catId){
        this.categoryService.deleteCatagory(catId);
        return new ResponseEntity(new ApiResponse("Category Deleted Successfully",true), HttpStatus.OK);
    }

    //Get all cetagory api
    @GetMapping("/")
    public ResponseEntity<CategoryResponse> getAllCatagory(
            @RequestParam(value="pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue = "id",required = false) String sortBy
    ){
//        List<CatagoryDto> catagories = this.categoryService.getAllCatagory();
//        return  new ResponseEntity(catagories, HttpStatus.OK);
        CategoryResponse categoryResponse =this.categoryService.getAllCatagory(pageNumber,pageSize,String.valueOf(Sort.by(sortBy)));

        return new ResponseEntity<CategoryResponse>(categoryResponse,HttpStatus.OK);
    }
    //simple category
    @GetMapping("/all")
    public ResponseEntity<List<CatagoryDto>> getAllSimpleCatagories(){
                List<CatagoryDto> catagories = this.categoryService.getAllSimpleCatagories();
        return  new ResponseEntity<List<CatagoryDto>>(catagories, HttpStatus.OK);
    }
    //singal cetagory api
    @GetMapping("/{catagoryId}")
    public ResponseEntity<CatagoryDto> getSingleUsers(@PathVariable("catagoryId") Integer catId){
        CatagoryDto catagoryDto = this.categoryService.getCatagoryById(catId);
        return new ResponseEntity<CatagoryDto>(catagoryDto, HttpStatus.OK);
    }


}
