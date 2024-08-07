package com.recordent.entity;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@Entity
@Table(name = "employee")
@AllArgsConstructor
@ToString
public class EmployeeRecord implements Serializable {

    @Id
    @SequenceGenerator(name = "gen1", sequenceName = "USER_No_SEQ", initialValue = 1000, allocationSize = 1)
    @GeneratedValue(generator = "gen1", strategy = GenerationType.SEQUENCE)
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String dept;

    @NonNull
    private Double salary;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public EmployeeRecord(@NonNull String name, @NonNull String dept, @NonNull Double salary, User user) {
        super();
        this.name = name;
        this.dept = dept;
        this.salary = salary;
        this.user = user;
    }

    public EmployeeRecord() {
        super();
        System.out.println("EmployeeRecord::-0-param constructor");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
