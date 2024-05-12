package com.tuts.ots.onetimeshop.services;

import com.tuts.ots.onetimeshop.payloads.ReportDto;
import com.tuts.ots.onetimeshop.payloads.ReportResponse;
import com.tuts.ots.onetimeshop.payloads.VenderDto;
import com.tuts.ots.onetimeshop.payloads.VenderResponse;

import java.util.List;

public interface ReportService {
    ReportDto createReport(ReportDto reportDto, Integer userId);

    ReportDto updateReportDto(ReportDto reportDto,Integer repId);

    ReportDto getReportDtoById(Integer repId);

    ReportResponse getAllReportDto(Integer pageNumber, Integer pageSize, String sortBy);

    List<ReportDto> getAllReportDtosimple();

    void deleteReportDto(Integer repId);

    List<ReportDto> getReportsByUser(Integer userId);
}
