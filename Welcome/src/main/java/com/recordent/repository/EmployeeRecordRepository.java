package com.recordent.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.recordent.entity.EmployeeRecord;

@Repository
public interface EmployeeRecordRepository extends JpaRepository<EmployeeRecord, Long> {

	List<EmployeeRecord> findByUserId(Long userId);

	void deleteByUserIdAndId(Long userId, Long id);

	Optional<EmployeeRecord> findByUserIdAndId(Long userId, Long employeeId);
}