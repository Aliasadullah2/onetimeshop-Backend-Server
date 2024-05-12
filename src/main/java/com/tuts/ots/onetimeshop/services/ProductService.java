package com.tuts.ots.onetimeshop.services;

import com.tuts.ots.onetimeshop.entities.ProductEnitity;
import com.tuts.ots.onetimeshop.payloads.CatagoryDto;
import com.tuts.ots.onetimeshop.payloads.ProductDto;
import com.tuts.ots.onetimeshop.payloads.ProductRespone;

import java.util.List;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto,Integer userId,Integer categoryId);

    ProductDto updateProductDto(ProductDto productDto,Integer productId);

    ProductDto getProductDtoById(Integer productId);

    ProductRespone getAllProductDto(Integer pageNumber, Integer pageSize,String sortBy);

    List<ProductDto> getAllProductDtosimple();

    void deleteProductDto(Integer productId);

    List<ProductDto> getProductByCategory(Integer categoryId);

    List<ProductDto> getProductByUser(Integer userId);

    List<ProductDto> searchProducts(String keywords);
}
