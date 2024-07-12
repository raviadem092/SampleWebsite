package com.recordent.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recordent.entity.EmployeeRecord;
import com.recordent.service.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<EmployeeRecord> getAllEmployees() {
        System.out.println("Employee: from get Employees Controller " + employeeService.getAllEmployees());
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeRecord> getEmployeeById(@PathVariable Long id) {
        System.out.println("Employee: from getEmployeeId Controller " + id);
        return employeeService.getEmployeeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public EmployeeRecord createEmployee(@RequestBody EmployeeRecord employee) {
        System.out.println("Employee: from create Controller " + employee.toString());
        return employeeService.createEmployee(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeRecord> updateEmployee(@PathVariable Long id, @RequestBody EmployeeRecord employeeDetails) {
        System.out.println("Employee: from Update Controller " + employeeDetails.toString());
        return ResponseEntity.ok(employeeService.updateEmployee(id, employeeDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        System.out.println("Employee: from deleteEmployeeId Controller " + id);
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
