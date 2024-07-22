package com.recordent.service;

import com.recordent.entity.EmployeeRecord;
import com.recordent.entity.User;
import com.recordent.repository.EmployeeRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRecordRepository employeeRepository;

    public List<EmployeeRecord> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<EmployeeRecord> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public EmployeeRecord createEmployee(EmployeeRecord employee) {
        return employeeRepository.save(employee);
    }

    public EmployeeRecord updateEmployee(Long id, EmployeeRecord employeeDetails) {
        EmployeeRecord employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        employee.setName(employeeDetails.getName());
        employee.setDept(employeeDetails.getDept());
        employee.setSalary(employeeDetails.getSalary());
        employee.setUser(employeeDetails.getUser());
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long userId, Long employeeId) {
        Optional<EmployeeRecord> employee = employeeRepository.findByUserIdAndId(userId, employeeId);
        employee.ifPresent(employeeRepository::delete);
    }

    public List<EmployeeRecord> getEmployeesByUserId(Long userId) {
        return employeeRepository.findByUserId(userId);
    }

    public Optional<EmployeeRecord> getEmployeeByUserIdAndEmployeeId(Long userId, Long employeeId) {
        return employeeRepository.findByUserIdAndId(userId, employeeId);
    }

    @Transactional
    public void saveAll(List<EmployeeRecord> employees) {
        employeeRepository.saveAll(employees);
    }

}
