package com.knits.jpa.queries.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String internationalName;
    private String email;
    private LocalDate birthDate;
    private String companyPhone;
    private LocalDate startDate;
    private LocalDate endDate;
    private OfficeDto office;

}
