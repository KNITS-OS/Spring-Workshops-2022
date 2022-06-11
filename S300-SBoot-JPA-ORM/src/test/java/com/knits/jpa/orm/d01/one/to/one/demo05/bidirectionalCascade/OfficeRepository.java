package com.knits.jpa.orm.d01.one.to.one.demo05.bidirectionalCascade;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OfficeRepository extends JpaRepository<Office,Long> {
}
