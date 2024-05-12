package com.tuts.ots.onetimeshop.services;

public interface DeleteService {
    void deleteCatagory(Integer catagoryId);

    void deleteroles(Integer id);
    void deletcomment (Integer comId);
    void deleteProductDto(Integer productId);
    void deleteReportDto(Integer repId);
    void deleteUser(Integer userId);
    void deleteVenderDto(Integer venId);
}
