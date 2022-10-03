package com.knits.jpa.queries.dto;

import lombok.Data;



@Data
public class OfficeDto {

    private Long id;
    private String city;
    private String street;
    private CountryDto country;

}
