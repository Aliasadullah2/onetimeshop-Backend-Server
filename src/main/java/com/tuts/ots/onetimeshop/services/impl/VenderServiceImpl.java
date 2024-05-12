package com.tuts.ots.onetimeshop.services.impl;

import com.tuts.ots.onetimeshop.entities.Category;
import com.tuts.ots.onetimeshop.entities.ProductEnitity;
import com.tuts.ots.onetimeshop.entities.User;
import com.tuts.ots.onetimeshop.entities.Vender;
import com.tuts.ots.onetimeshop.execptions.ResourceNotFoundException;
import com.tuts.ots.onetimeshop.payloads.ProductDto;
import com.tuts.ots.onetimeshop.payloads.ProductRespone;
import com.tuts.ots.onetimeshop.payloads.VenderDto;
import com.tuts.ots.onetimeshop.payloads.VenderResponse;
import com.tuts.ots.onetimeshop.repositires.CategoryRepo;
import com.tuts.ots.onetimeshop.repositires.UserRepo;
import com.tuts.ots.onetimeshop.repositires.VenderRepo;
import com.tuts.ots.onetimeshop.services.VenderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VenderServiceImpl implements VenderService {

    @Autowired
    private VenderRepo venderRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;


    @Override
    public VenderDto createVender(VenderDto venderDto, Integer userId, Integer categoryId) {
        User user=this.userRepo.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User","User id",userId));

        Category category=this.categoryRepo.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category","category id",categoryId));

        Vender vender = this.modelMapper.map(venderDto, Vender.class);
        vender.setVenImg("default.png");
        vender.setAddedDate(new Date());
        vender.setUser(user);
        vender.setCategory(category);

        Vender newVender = this.venderRepo.save(vender);

        return this.modelMapper.map( newVender, VenderDto.class);
    }

    @Override
    public VenderDto updateVenderDto(VenderDto venderDto, Integer venId) {
        Vender vender=this.venderRepo.findById(venId)
                .orElseThrow(()-> new ResourceNotFoundException("Vender","ven id",venId));

        Category category=this.categoryRepo.findById(venderDto.getCategory().getId()).get();

        vender.setName(venderDto.getName());
        vender.setTitle(venderDto.getTitle());
        vender.setAbout(venderDto.getAbout());
        vender.setPrice(venderDto.getPrice());
        vender.setPhonenumber(venderDto.getPhonenumber());
        vender.setVenImg(venderDto.getVenImg());
        vender.setCategory(category);

        Vender upatededVender=this.venderRepo.save(vender);
        return this.modelMapper.map(upatededVender,VenderDto.class);
    }

    @Override
    public VenderDto getVenderDtoById(Integer venId) {
        Vender vender=this.venderRepo.findById(venId)
                .orElseThrow(()-> new ResourceNotFoundException("Vender","ven id",venId));
        return this.modelMapper.map(vender,VenderDto.class);
    }

    @Override
    public VenderResponse getAllVenderDto(Integer pageNumber, Integer pageSize, String sortBy) {
        Pageable p = PageRequest.of(pageNumber,pageSize);

        Page<Vender> pageVender= this.venderRepo.findAll(p);
        List<Vender> allVender =pageVender.getContent();

        List<VenderDto> venderDtos=allVender.stream()
                .map((vender  )->this.modelMapper.map(vender,VenderDto.class)).collect(Collectors.toList());

        VenderResponse venderResponse=new VenderResponse();

        venderResponse.setContent(venderDtos);
        venderResponse.setPageNumber(pageVender.getNumber());
        venderResponse.setPageSize(pageVender.getSize());
        venderResponse.setTotalElement(pageVender.getTotalElements());
        venderResponse.setTotalPages(pageVender.getTotalPages());
        venderResponse.setLastPage(pageVender.isLast());
        return venderResponse;
    }

    @Override
    public List<VenderDto> getAllVenderDtosimple() {
        List<Vender> allVenders = this.venderRepo.findAll();
        List<VenderDto> venderDtos = allVenders.stream().map((vender) -> this.modelMapper.map(vender, VenderDto.class)).collect(Collectors.toList());
        return venderDtos;
    }

    @Override
    public void deleteVenderDto(Integer venId) {
        Vender vender=this.venderRepo.findById(venId)
                .orElseThrow(()-> new ResourceNotFoundException("Vender","venn id",venId));
        this.venderRepo.delete(vender);
    }

    @Override
    public List<VenderDto> getVenderByCategory(Integer categoryId) {
        Category cat = this.categoryRepo.findById(categoryId).
                orElseThrow(()->new ResourceNotFoundException("Category","category id",categoryId));

        List<Vender> venderds=this.venderRepo.findByCategory(cat);

        List<VenderDto> venderDto =venderds.stream()
                .map((vender)-> this.modelMapper.map(vender,VenderDto.class)).collect(Collectors.toList());

        return venderDto;
    }

    @Override
    public List<VenderDto> getVenderByUser(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User","user id",userId));

        List<Vender> venders =  this.venderRepo.findByUser(user);

        List<VenderDto> venderDtos=venders.stream()
                .map((vender)-> this.modelMapper.map(vender,VenderDto.class)).collect(Collectors.toList());

        return venderDtos;
    }
}
