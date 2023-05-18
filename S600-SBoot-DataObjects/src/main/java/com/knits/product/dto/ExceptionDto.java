package com.knits.product.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.knits.product.dto.views.Views;
import com.knits.product.exception.AppException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonView(Views.Public.class)
public class ExceptionDto {

    private int code;
    private String message;

    public ExceptionDto(AppException e){
        setCode(e.getCode());
        setMessage(e.getMessage());
    }

}
