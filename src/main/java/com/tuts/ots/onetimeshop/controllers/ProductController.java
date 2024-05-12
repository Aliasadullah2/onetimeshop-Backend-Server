package com.tuts.ots.onetimeshop.controllers;

import com.tuts.ots.onetimeshop.configs.AppConstants;
import com.tuts.ots.onetimeshop.entities.ProductEnitity;
import com.tuts.ots.onetimeshop.payloads.ApiResponse;
import com.tuts.ots.onetimeshop.payloads.CatagoryDto;
import com.tuts.ots.onetimeshop.payloads.ProductDto;
import com.tuts.ots.onetimeshop.payloads.ProductRespone;
import com.tuts.ots.onetimeshop.services.FileService;
import com.tuts.ots.onetimeshop.services.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    //create Product api
    @PostMapping("/user/{userId}/catagory/{catagoryId}/products")
    public ResponseEntity<ProductDto> createProduct( @RequestBody ProductDto productDto, @PathVariable Integer userId,@PathVariable Integer catagoryId  ){

        ProductDto createProductDto = this.productService.createProduct(productDto,userId,catagoryId);

        return new ResponseEntity<ProductDto>(createProductDto, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}/products")
    public ResponseEntity<List<ProductDto>> getProductByUser(@PathVariable Integer userId){
        List<ProductDto> productDtos =this.productService.getProductByUser(userId);
        return new ResponseEntity<List<ProductDto>>(productDtos,HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}/products")
    public ResponseEntity<List<ProductDto>> getProductByCategory(@PathVariable Integer categoryId){
        List<ProductDto> productDtos =this.productService.getProductByCategory(categoryId);
        return new ResponseEntity<List<ProductDto>>(productDtos,HttpStatus.OK);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductDto> getProductDtoById(@PathVariable Integer productId){
        ProductDto productDtos =this.productService.getProductDtoById(productId);
        return new ResponseEntity<ProductDto>(productDtos,HttpStatus.OK);
    }

    @GetMapping("/products")
    public ResponseEntity<ProductRespone> getAllProductDto(
            @RequestParam(value="pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy
    ){
        ProductRespone productRespone =this.productService.getAllProductDto(pageNumber,pageSize, String.valueOf(Sort.by(sortBy)));

        return new ResponseEntity<ProductRespone>(productRespone,HttpStatus.OK);
    }

    @GetMapping("/all/products")
    public ResponseEntity<List<ProductDto>> getAllProductDtosimple(){
        List<ProductDto> productDtos =this.productService.getAllProductDtosimple();
        return new ResponseEntity<List<ProductDto>>(productDtos,HttpStatus.OK);
    }


    //Delete User api
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<ApiResponse> deleteProductDto(@PathVariable("productId") Integer productId){
        this.productService.deleteProductDto(productId);
        return new ResponseEntity(new ApiResponse("Product Deleted Successfully",true), HttpStatus.OK);
    }

    //updated user api
    @PutMapping("/products/{productId}")
    public ResponseEntity<ProductDto> updateProductDto(@Valid @RequestBody ProductDto productDto,@PathVariable("productId") Integer productId){
        ProductDto updateProductDto =this.productService.updateProductDto(productDto,productId);
        return new ResponseEntity<ProductDto>(updateProductDto, HttpStatus.OK);
    }

    //Searching product
    @GetMapping("/products/search/{keywords}")
    public ResponseEntity<List<ProductDto>> searchProducts(
            @PathVariable("keywords") String keywords
    ){
        List<ProductDto> result=this.productService.searchProducts(keywords);
        return new ResponseEntity<List<ProductDto>>(result,HttpStatus.OK);
    }

    //PostImage Upload
    @PostMapping("/product/image/upload/{productId}")
    public ResponseEntity<ProductDto> uploadPostImage(
            @RequestParam("image") MultipartFile  image,
            @PathVariable("productId") Integer productId


            ) throws IOException {
        ProductDto productDto =this.productService.getProductDtoById(productId);

       String fileName= this.fileService.uploadImage(path,image);

       productDto.setProdImg(fileName);

       ProductDto updatedProdut = this.productService.updateProductDto(productDto,productId);
       return  new ResponseEntity<ProductDto>(updatedProdut,HttpStatus.OK);
    }

    @GetMapping(value = "/product/image/{imagename}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("imagename") String imagename,
            HttpServletResponse response
    ) throws IOException {
        InputStream resource = this.fileService.getResource(path,imagename);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }


}
