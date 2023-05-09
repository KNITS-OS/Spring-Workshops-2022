package com.knits.jpa.orm.d01.one.to.one.demo02.sharedpk;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfficeRepository extends JpaRepository<Office,Long> {
}
