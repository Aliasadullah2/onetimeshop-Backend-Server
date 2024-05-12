package com.tuts.ots.onetimeshop.controllers;

import com.tuts.ots.onetimeshop.configs.AppConstants;
import com.tuts.ots.onetimeshop.payloads.*;
import com.tuts.ots.onetimeshop.services.ReportService;
import com.tuts.ots.onetimeshop.services.VenderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reports/")
public class ReportControllers {
    @Autowired
    private ReportService reportService;

    //create Report api
    @PostMapping("/user/{userId}/report")
    public ResponseEntity<ReportDto> createReport(@RequestBody ReportDto reportDto, @PathVariable Integer userId ){

        ReportDto createReportDto = this.reportService.createReport(reportDto,userId);

        return new ResponseEntity<ReportDto>(createReportDto, HttpStatus.CREATED);
    }

    @GetMapping("/report/{repId}")
    public ResponseEntity<ReportDto> getReportDtoById(@PathVariable Integer repId){
        ReportDto reportDto =this.reportService.getReportDtoById(repId);
        return new ResponseEntity<ReportDto>(reportDto,HttpStatus.OK);
    }

    @GetMapping("/report")
    public ResponseEntity<ReportResponse> getAllReportDto(
            @RequestParam(value="pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy
    ){
        ReportResponse reportResponse =this.reportService.getAllReportDto(pageNumber,pageSize, String.valueOf(Sort.by(sortBy)));

        return new ResponseEntity<ReportResponse>(reportResponse,HttpStatus.OK);
    }

    @GetMapping("/all/report")
    public ResponseEntity<List<ReportDto>> getAllReportDtosimple(){
        List<ReportDto> reportDtos =this.reportService.getAllReportDtosimple();
        return new ResponseEntity<List<ReportDto>>(reportDtos,HttpStatus.OK);
    }

    //Delete Report api
    @DeleteMapping("/report/{repId}")
    public ResponseEntity<ApiResponse> deleteReportDto(@PathVariable("repId") Integer repId){
        this.reportService.deleteReportDto(repId);
        return new ResponseEntity(new ApiResponse("Report Deleted Successfully",true), HttpStatus.OK);
    }

    //updated Vender api
    @PutMapping("/report/{repId}")
    public ResponseEntity<ReportDto> updateReportDto(@Valid @RequestBody ReportDto reportDto, @PathVariable("repId") Integer repId){
        ReportDto updateReportDto =this.reportService.updateReportDto(reportDto,repId);
        return new ResponseEntity<ReportDto>(updateReportDto, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/reports")
    public ResponseEntity<List<ReportDto>> getReportByUser(@PathVariable Integer userId){
        List<ReportDto> reportDtos =this.reportService.getReportsByUser(userId);
        return new ResponseEntity<List<ReportDto>>(reportDtos,HttpStatus.OK);
    }
}
