package com.knits.jpa.queries.mappers;

import com.knits.jpa.queries.dto.EmployeeDto;
import com.knits.jpa.queries.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN)
  //      uses = {OfficeMapper.class, BusinessUnitMapper.class, JobTitleMapper.class, ManagementGroupMapper.class, CostCenterMapper.class})
public interface EmployeeMapper extends EntityMapper<Employee, EmployeeDto> {


}
