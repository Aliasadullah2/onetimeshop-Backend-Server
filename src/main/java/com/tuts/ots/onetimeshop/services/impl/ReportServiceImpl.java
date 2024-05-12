package com.tuts.ots.onetimeshop.services.impl;

import com.tuts.ots.onetimeshop.entities.Category;
import com.tuts.ots.onetimeshop.entities.Report;
import com.tuts.ots.onetimeshop.entities.User;
import com.tuts.ots.onetimeshop.entities.Vender;
import com.tuts.ots.onetimeshop.execptions.ResourceNotFoundException;
import com.tuts.ots.onetimeshop.payloads.ReportDto;
import com.tuts.ots.onetimeshop.payloads.ReportResponse;
import com.tuts.ots.onetimeshop.payloads.VenderDto;
import com.tuts.ots.onetimeshop.payloads.VenderResponse;
import com.tuts.ots.onetimeshop.repositires.ReportRepo;
import com.tuts.ots.onetimeshop.repositires.UserRepo;
import com.tuts.ots.onetimeshop.repositires.VenderRepo;
import com.tuts.ots.onetimeshop.services.ReportService;
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
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportRepo reportRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Override
    public ReportDto createReport(ReportDto reportDto, Integer userId) {
        User user=this.userRepo.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User","User id",userId));

        Report report = this.modelMapper.map(reportDto, Report.class);


        report.setAddedDate(new Date());
        report.setUser(user);


        Report report1 = this.reportRepo.save(report);

        return this.modelMapper.map( report1, ReportDto.class);
    }

    @Override
    public ReportDto updateReportDto(ReportDto reportDto, Integer repId) {
        Report report=this.reportRepo.findById(repId)
                .orElseThrow(()-> new ResourceNotFoundException("Report","rep id",repId));


        report.setTitle(reportDto.getTitle());
        report.setAbout(reportDto.getAbout());

        Report upatededReport=this.reportRepo.save(report);
        return this.modelMapper.map(upatededReport,ReportDto.class);
    }

    @Override
    public ReportDto getReportDtoById(Integer repId) {
        Report report=this.reportRepo.findById(repId)
                .orElseThrow(()-> new ResourceNotFoundException("Report","rep id",repId));
        return this.modelMapper.map(report,ReportDto.class);
    }

    @Override
    public ReportResponse getAllReportDto(Integer pageNumber, Integer pageSize, String sortBy) {
        Pageable p = PageRequest.of(pageNumber,pageSize);

        Page<Report> pageReport= this.reportRepo.findAll(p);
        List<Report> allReport =pageReport.getContent();

        List<ReportDto> reportDtos=allReport.stream()
                .map((report  )->this.modelMapper.map(report,ReportDto.class)).collect(Collectors.toList());

        ReportResponse reportResponse=new ReportResponse();

        reportResponse.setContent(reportDtos);
        reportResponse.setPageNumber(pageReport.getNumber());
        reportResponse.setPageSize(pageReport.getSize());
        reportResponse.setTotalElement(pageReport.getTotalElements());
        reportResponse.setTotalPages(pageReport.getTotalPages());
        reportResponse.setLastPage(pageReport.isLast());
        return reportResponse;
    }

    @Override
    public List<ReportDto> getAllReportDtosimple() {
        List<Report> allReports = this.reportRepo.findAll();
        List<ReportDto> reportDtos = allReports.stream()
                .map((report) -> this.modelMapper.map(report, ReportDto.class)).collect(Collectors.toList());
        return reportDtos;
    }

    @Override
    public void deleteReportDto(Integer repId) {
        Report report=this.reportRepo.findById(repId)
                .orElseThrow(()-> new ResourceNotFoundException("Report","rep id",repId));
        this.reportRepo.delete(report);
    }

    @Override
    public List<ReportDto> getReportsByUser(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User","user id",userId));

        List<Report> reports =  this.reportRepo.findByUser(user);

        List<ReportDto> reportDtos=reports.stream()
                .map((report)-> this.modelMapper.map(report,ReportDto.class)).collect(Collectors.toList());

        return reportDtos;
    }
}
