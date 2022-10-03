package com.knits.jpa.queries.dto.search;

import com.knits.jpa.queries.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.Column;
import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeSearchDto extends AbstractSearchableDto<Employee> {

    private String lastName;
    private String email;
    private Long countryId;
    private Long officeId;
    private String startDateFrom;

    @Override
    public Specification<Employee> getSpecification() {

        return (root, query, criteriaBuilder) -> { // see Specification.toPredicate

            query.distinct(true); //otherwise will list a Employee for every matching row in join
            Predicate noFiltersApplied = criteriaBuilder.conjunction(); //default to no filters
            List<Predicate> filters = new ArrayList<>();
            filters.add(noFiltersApplied); //support list all for empty search dto

            if (Strings.isNotBlank(lastName)) {
                Predicate lastNameAsPredicate = criteriaBuilder.like(root.get("lastName"), "%" + lastName + "%");
                filters.add(lastNameAsPredicate);
            }

            if (Strings.isNotBlank(email)) {
                Predicate lastNameAsPredicate = criteriaBuilder.like(root.get("email"), "%" + email + "%");
                filters.add(lastNameAsPredicate);
            }

            if (countryId != null) {
                Predicate countryIdAsPredicate = criteriaBuilder.equal(root.get("office").get("country").get("id"), countryId);
                filters.add(countryIdAsPredicate);
            }

            if (officeId != null) {
                Predicate officeIdAsPredicate = criteriaBuilder.equal(root.get("office").get("id"), officeId);
                filters.add(officeIdAsPredicate);
            }

            if (Strings.isNotBlank(startDateFrom)) {
                LocalDate from = LocalDate.parse(startDateFrom, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                Predicate hiringDateFromAsPredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), from);
                filters.add(hiringDateFromAsPredicate);
            }

            return criteriaBuilder.and(filters.toArray(new Predicate[filters.size()]));
        };
    }

}
