package com.recordent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.recordent.entity.EmployeeRecord;

@Repository
public interface EmployeeRecordRepository extends JpaRepository<EmployeeRecord, Long> {
}
