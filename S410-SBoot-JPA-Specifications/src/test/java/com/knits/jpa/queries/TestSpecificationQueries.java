package com.knits.jpa.queries;

import static org.assertj.core.api.Assertions.assertThat;

import com.knits.jpa.queries.dto.EmployeeDto;
import com.knits.jpa.queries.dto.search.EmployeeSearchDto;
import com.knits.jpa.queries.model.Country;
import com.knits.jpa.queries.model.Employee;
import com.knits.jpa.queries.model.Office;
import com.knits.jpa.queries.repository.CountryRepository;
import com.knits.jpa.queries.repository.EmployeeRepository;
import com.knits.jpa.queries.repository.OfficeRepository;
import com.knits.jpa.queries.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@EntityScan("com.knits.jpa.queries.model") //otherwise finds all other entities in subpackages
@EnableJpaRepositories("com.knits.jpa.queries.repository") //otherwise doesnt create jpa repositories instances
@ComponentScan(basePackages = "com.knits.jpa.queries")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=update",
        "spring.datasource.url=jdbc:postgresql://localhost:5432/JPA-ORM-05"
})
@Slf4j
public class TestSpecificationQueries {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private OfficeRepository officeRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private EmployeeService employeeService;

    //@Test
    @Rollback(value = false)
    public void initDatabase(){

        Country countryEE = saveNewCountry("Estonia","EE");
        Country countryIT = saveNewCountry("Italy","IT");
        Country countryFR =saveNewCountry("France","FR");
        Country countryDE =saveNewCountry("Germany","DE");

        Office officeInEE =saveNewOffice(countryEE, "Tallinn");
        Office officeInIT =saveNewOffice(countryIT,"Napoli");
        Office officeInFR =saveNewOffice(countryFR,"Paris");
        Office officeInDE =saveNewOffice(countryDE,"Hamburg");

        Employee employee = new Employee();
        employee.setFirstName("Stefano");
        employee.setLastName("Fiorenza");
        employee.setBirthDate(LocalDate.of(1980,11,17));
        employee.setEmail("stefano.fiorenza@email.it");
        employee.setCompanyPhone("243827492362");
        employee.setInternationalName("Stefano Fiorenza");
        employee.setStartDate(LocalDate.of(2010,12,12));
        employee.setEndDate(null);
        employee.setOffice(officeInEE);
        employeeRepository.save(employee);

    }

    @Test
    @Rollback(value = false)
    @Sql("/data/employees.sql")
    public void testWithSqlInput(){


        connectEmployeesWithOffices();

        EmployeeSearchDto searchByLastName = EmployeeSearchDto.builder()
                .lastName("Foltin")
                .build();
        testTemplate(1,searchByLastName);

        List<Office> officesInTallinn =officeRepository.findByIso2("EE");
        Long countryId =officesInTallinn.get(0).getCountry().getId();
        EmployeeSearchDto searchByCountryId = EmployeeSearchDto.builder()
                .countryId(countryId)
                .build();
        testTemplate(250,searchByCountryId);//according to connectEmployeesWithOffices() 1000/4=250

    }


    private void testTemplate (int expectedResultsetSize, EmployeeSearchDto employeeSearch) {
        Page<EmployeeDto> pageOfEmployees = employeeService.search(employeeSearch);
        assertThat(pageOfEmployees.getContent().size()).isEqualTo(expectedResultsetSize);
    }

    private void connectEmployeesWithOffices(){

        Country countryEE = saveNewCountry("Estonia","EE");
        Country countryIT = saveNewCountry("Italy","IT");
        Country countryFR =saveNewCountry("France","FR");
        Country countryDE =saveNewCountry("Germany","DE");

        Office officeInEE =saveNewOffice(countryEE, "Tallinn");
        Office officeInIT =saveNewOffice(countryIT,"Napoli");
        Office officeInFR =saveNewOffice(countryFR,"Paris");
        Office officeInDE =saveNewOffice(countryDE,"Hamburg");

        List<Country>  countries = List.of(countryEE,countryIT,countryFR,countryDE);
        List<Office>  offices = List.of(officeInEE,officeInIT,officeInFR,officeInDE);

        List<Employee> allEmployees= new ArrayList<>();
        employeeRepository.findAll().iterator().forEachRemaining( (employee) ->allEmployees.add(employee));

        int counter=0;

        for (int i=0; i<allEmployees.size(); i++){
            if (counter==offices.size()){
                counter=0;
            }
            allEmployees.get(i).setOffice(offices.get(counter));
            counter++;
        }

        employeeRepository.saveAll(allEmployees);
    }



    private void verifyResultsetAndLogFirst(List<Employee> employees){
        assertThat(employees.size()).isGreaterThan(0);
        log.info(employees.get(0).toString());
    }


    private Country saveNewCountry(String countryName, String iso2){
        Country country = new Country();
        country.setName(countryName);
        country.setIso2(iso2);
        return countryRepository.save(country);
    }

    private Office saveNewOffice(Country country, String city){
        Office office = new Office();
        office.setCity(city);
        office.setStreet("A Mock Street");
        office.setCountry(country);
        return officeRepository.save(office);
    }

}
