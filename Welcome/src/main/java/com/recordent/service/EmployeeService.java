package com.recordent.service;

import com.recordent.entity.EmployeeRecord;
import com.recordent.repository.EmployeeRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRecordRepository employeeRepository;

    public List<EmployeeRecord> getAllEmployees() {
        System.out.println("Employee: Service " + employeeRepository.findAll());
        return employeeRepository.findAll();
    }

    public Optional<EmployeeRecord> getEmployeeById(Long id) {
        System.out.println("Employee: getBy Id Service " + id);
        return employeeRepository.findById(id);
    }

    public EmployeeRecord createEmployee(EmployeeRecord employee) {
        System.out.println("Employee: Create Service " + employee);
        return employeeRepository.save(employee);
    }

    public EmployeeRecord updateEmployee(Long id, EmployeeRecord employeeDetails) {
        System.out.println("Employee: service from employeeeDetails " + employeeDetails.toString());
        EmployeeRecord employee = employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));
        System.out.println("Employee: Service " + employee.toString());
        employee.setName(employeeDetails.getName());
        employee.setDept(employeeDetails.getDept());
        employee.setSalary(employeeDetails.getSalary());
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        System.out.println("Employee: Service delete " + id);
        employeeRepository.deleteById(id);
    }
}
