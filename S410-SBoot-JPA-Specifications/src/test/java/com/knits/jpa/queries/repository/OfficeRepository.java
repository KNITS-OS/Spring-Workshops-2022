package com.knits.jpa.queries.repository;


import com.knits.jpa.queries.model.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfficeRepository extends JpaRepository<Office,Long> {

    @Query("SELECT o FROM Office o WHERE o.country.iso2 = ?1")
    List<Office> findByIso2(String iso2);
}
