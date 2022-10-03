package com.knits.jpa.queries.service;

import com.knits.jpa.queries.dto.EmployeeDto;
import com.knits.jpa.queries.dto.search.EmployeeSearchDto;
import com.knits.jpa.queries.mappers.EmployeeMapper;
import com.knits.jpa.queries.model.Employee;
import com.knits.jpa.queries.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public Page<EmployeeDto> search(EmployeeSearchDto employeeSearch) {
        Page<Employee> employeesPage = employeeRepository.findAll(employeeSearch.getSpecification(), employeeSearch.getPageable());
        List<EmployeeDto> employeeDtos = employeeMapper.toDtoList(employeesPage.getContent());
        return new PageImpl<>(employeeDtos, employeeSearch.getPageable(), employeesPage.getTotalElements());
    }

}
