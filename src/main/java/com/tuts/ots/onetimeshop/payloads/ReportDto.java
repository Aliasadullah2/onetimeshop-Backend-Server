package com.tuts.ots.onetimeshop.payloads;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class ReportDto {

    private int repId;

    @NotEmpty
    private String title;

    @NotEmpty
    @Size(min = 1000,message = "About must be 1000 Characters")
    private String about;

    private Date addedDate;

    private UserDto user;
}
