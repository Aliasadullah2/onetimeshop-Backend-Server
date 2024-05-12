package com.tuts.ots.onetimeshop.services.impl;

import com.tuts.ots.onetimeshop.entities.Category;
import com.tuts.ots.onetimeshop.entities.ProductEnitity;
import com.tuts.ots.onetimeshop.entities.User;
import com.tuts.ots.onetimeshop.execptions.ResourceNotFoundException;
import com.tuts.ots.onetimeshop.payloads.ProductDto;
import com.tuts.ots.onetimeshop.payloads.ProductRespone;
import com.tuts.ots.onetimeshop.repositires.CategoryRepo;
import com.tuts.ots.onetimeshop.repositires.ProductRepo;
import com.tuts.ots.onetimeshop.repositires.UserRepo;
import com.tuts.ots.onetimeshop.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;




    @Override
    public ProductDto createProduct(ProductDto productDto,Integer userId,Integer categoryId) {

        User user=this.userRepo.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User","User id",userId));

        Category category=this.categoryRepo.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category","category id",categoryId));

        ProductEnitity productEnitity = this.modelMapper.map(productDto,ProductEnitity.class);
        productEnitity.setProdImg("default.png");
        productEnitity.setAddedDate(new Date());
        productEnitity.setUser(user);
        productEnitity.setCategory(category);

        ProductEnitity newProduct = this.productRepo.save(productEnitity);

        return this.modelMapper.map( newProduct, ProductDto.class);
    }

    @Override
    public ProductDto updateProductDto(ProductDto productDto, Integer productId) {
        ProductEnitity productEnitity=this.productRepo.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("ProductEnitity","product id",productId));

        Category category=this.categoryRepo.findById(productDto.getCategory().getId()).get();

        productEnitity.setProdName(productDto.getProdName());
        productEnitity.setProdDesc(productDto.getProdDesc());
        productEnitity.setProdPrice(productDto.getProdPrice());
        productEnitity.setProdImg(productDto.getProdImg());
        productEnitity.setCategory(category);

        ProductEnitity upatededProduct=this.productRepo.save(productEnitity);
        return this.modelMapper.map(upatededProduct,ProductDto.class);
    }

    @Override
    public ProductDto getProductDtoById(Integer productId) {
        ProductEnitity productEnitity=this.productRepo.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("ProductEnitity","product id",productId));
        return this.modelMapper.map(productEnitity,ProductDto.class);
    }

    @Override
    public ProductRespone getAllProductDto(Integer pageNumber, Integer pageSize,String sortBy) {


        Pageable p = PageRequest.of(pageNumber,pageSize);

        Page<ProductEnitity> pageProduct= this.productRepo.findAll(p);
        List<ProductEnitity> allProducts =pageProduct.getContent();

        List<ProductDto> productDto=allProducts.stream()
                .map((productEnitity  )->this.modelMapper.map(productEnitity,ProductDto.class)).collect(Collectors.toList());

        ProductRespone productRespone = new ProductRespone();

        productRespone.setContent(productDto);
        productRespone.setPageNumber(pageProduct.getNumber());
        productRespone.setPageSize(pageProduct.getSize());
        productRespone.setTotalElement(pageProduct.getTotalElements());

        productRespone.setTotalPages(pageProduct.getTotalPages());
        productRespone.setLastPage(pageProduct.isLast());
        return productRespone;
    }

    @Override
    public List<ProductDto> getAllProductDtosimple() {
        List<ProductEnitity> allproducts = this.productRepo.findAll();
        List<ProductDto> productDtoStream = allproducts.stream().map((product) -> this.modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());
        return productDtoStream;
    }


    @Override
    public void deleteProductDto(Integer productId) {
        ProductEnitity productEnitity=this.productRepo.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("ProductEnitity","product id",productId));
        this.productRepo.delete(productEnitity);
    }

    @Override
    public List<ProductDto> getProductByCategory(Integer categoryId) {
        Category cat = this.categoryRepo.findById(categoryId).
                orElseThrow(()->new ResourceNotFoundException("Category","category id",categoryId));
        List<ProductEnitity> product =  this.productRepo.findByCategory(cat);

        List<ProductDto> productDto =product.stream().map((productEnitity)-> this.modelMapper.map(productEnitity,ProductDto.class)).collect(Collectors.toList());

        return productDto;
    }

    @Override
    public List<ProductDto> getProductByUser(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User","user id",userId));
        List<ProductEnitity> product =  this.productRepo.findByUser(user);

        List<ProductDto> productDto=product.stream().map((productEnitity)-> this.modelMapper.map(productEnitity,ProductDto.class)).collect(Collectors.toList());

        return productDto;
    }

    @Override
    public List<ProductDto> searchProducts(String keywords) {
        List<ProductEnitity> productEnitities=this.productRepo.findByProdNameContaining(keywords);
        List<ProductDto> productDto=productEnitities.stream()
                .map((productEnitity)-> this.modelMapper.map(productEnitity,ProductDto.class)).collect(Collectors.toList());
        return productDto;
    }
}
