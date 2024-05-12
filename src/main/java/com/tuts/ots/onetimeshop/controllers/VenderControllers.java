package com.tuts.ots.onetimeshop.controllers;

import com.tuts.ots.onetimeshop.configs.AppConstants;
import com.tuts.ots.onetimeshop.payloads.*;
import com.tuts.ots.onetimeshop.services.FileService;
import com.tuts.ots.onetimeshop.services.ProductService;
import com.tuts.ots.onetimeshop.services.VenderService;
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
@RequestMapping("/api/v1/vender/")
public class VenderControllers {

    @Autowired
    private VenderService venderService;

    @Autowired
    private FileService fileService;

    @Value("${project.image.profile}")
    private String path;

    //create Vender api
    @PostMapping("/user/{userId}/catagory/{catagoryId}/venders")
    public ResponseEntity<VenderDto> createVender(@RequestBody VenderDto venderDto, @PathVariable Integer userId, @PathVariable Integer catagoryId  ){

        VenderDto createVenderDto = this.venderService.createVender(venderDto,userId,catagoryId);

        return new ResponseEntity<VenderDto>(createVenderDto, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}/venders")
    public ResponseEntity<List<VenderDto>> getVenderByUser(@PathVariable Integer userId){
        List<VenderDto> venderDtos =this.venderService.getVenderByUser(userId);
        return new ResponseEntity<List<VenderDto>>(venderDtos,HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}/venders")
    public ResponseEntity<List<VenderDto>> getVenderByCategory(@PathVariable Integer categoryId){
        List<VenderDto> venderdtos =this.venderService.getVenderByCategory(categoryId);
        return new ResponseEntity<List<VenderDto>>(venderdtos,HttpStatus.OK);
    }

    @GetMapping("/venders/{venId}")
    public ResponseEntity<VenderDto> getVenderDtoById(@PathVariable Integer venId){
        VenderDto venderDto =this.venderService.getVenderDtoById(venId);
        return new ResponseEntity<VenderDto>(venderDto,HttpStatus.OK);
    }

    @GetMapping("/venders")
    public ResponseEntity<VenderResponse> getAllVenderDto(
            @RequestParam(value="pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy
    ){
        VenderResponse venderResponse =this.venderService.getAllVenderDto(pageNumber,pageSize, String.valueOf(Sort.by(sortBy)));

        return new ResponseEntity<VenderResponse>(venderResponse,HttpStatus.OK);
    }

    @GetMapping("/all/venders")
    public ResponseEntity<List<VenderDto>> getAllVenderDtosimple(){
        List<VenderDto> venderDtos =this.venderService.getAllVenderDtosimple();
        return new ResponseEntity<List<VenderDto>>(venderDtos,HttpStatus.OK);
    }

    //Delete Vender api
    @DeleteMapping("/venders/{venId}")
    public ResponseEntity<ApiResponse> deleteVenderDto(@PathVariable("venId") Integer venId){
        this.venderService.deleteVenderDto(venId);
        return new ResponseEntity(new ApiResponse("Vender Deleted Successfully",true), HttpStatus.OK);
    }

    //updated Vender api
    @PutMapping("/vender/{venId}")
    public ResponseEntity<VenderDto> updateVenderDto(@Valid @RequestBody VenderDto venderDto, @PathVariable("venId") Integer venId){
        VenderDto updateVenderDto =this.venderService.updateVenderDto(venderDto,venId);
        return new ResponseEntity<VenderDto>(updateVenderDto, HttpStatus.OK);
    }

    //PostImage Upload
    @PostMapping("/vender/image/upload/{venId}")
    public ResponseEntity<VenderDto> uploadPostImage(
            @RequestParam("image") MultipartFile image,
            @PathVariable("venId") Integer venId


    ) throws IOException {
        VenderDto venderDto =this.venderService.getVenderDtoById(venId);

        String fileName= this.fileService.uploadImage(path,image);

        venderDto.setVenImg(fileName);

        VenderDto updatedVender = this.venderService.updateVenderDto(venderDto,venId);
        return  new ResponseEntity<VenderDto>(updatedVender,HttpStatus.OK);
    }

    @GetMapping(value = "/vender/image/{imagename}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("imagename") String imagename,
            HttpServletResponse response
    ) throws IOException {
        InputStream resource = this.fileService.getResource(path,imagename);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }

}
