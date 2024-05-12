package com.tuts.ots.onetimeshop.services;

import com.tuts.ots.onetimeshop.payloads.ProductDto;
import com.tuts.ots.onetimeshop.payloads.ProductRespone;
import com.tuts.ots.onetimeshop.payloads.VenderDto;
import com.tuts.ots.onetimeshop.payloads.VenderResponse;

import java.util.List;

public interface VenderService {

    VenderDto createVender(VenderDto venderDto, Integer userId, Integer categoryId);

    VenderDto updateVenderDto(VenderDto venderDto,Integer venId);

    VenderDto getVenderDtoById(Integer venId);

    VenderResponse getAllVenderDto(Integer pageNumber, Integer pageSize, String sortBy);

    List<VenderDto> getAllVenderDtosimple();

    void deleteVenderDto(Integer venId);

    List<VenderDto> getVenderByCategory(Integer categoryId);

    List<VenderDto> getVenderByUser(Integer userId);
}
