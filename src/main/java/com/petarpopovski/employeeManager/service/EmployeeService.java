package com.petarpopovski.employeeManager.service;

import com.petarpopovski.employeeManager.exceptions.UserNotFoundException;
import com.petarpopovski.employeeManager.model.Employee;
import com.petarpopovski.employeeManager.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
//@EnableCaching
public class EmployeeService {

    private final EmployeeRepo employeeRepo;

    @Autowired
    public EmployeeService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }


    //  FIND  ALL EMPLOYEES
    @Cacheable(value="Employee")
    public List<Employee> findAllEmployees(){
        return employeeRepo.findAll();
    }


    // UPDATE EMPLOYEE
    @CachePut(value="Employee", key="#id")
    public Employee updateEmployee(Employee employee){
        return employeeRepo.save(employee);
    }

    // DELETE EMPLOYEE
    @CacheEvict(value="Employee", key="#id")
    public void deleteEmployee(Long id){
        this.employeeRepo.deleteEmployeeById(id);
    }

    // FIND EMPLOYEE BY ID
    @Cacheable(value="Employee", key="#id")
    public Employee findEmployeeById(Long id){
        return this.employeeRepo.findEmployeeById(id).orElseThrow(() -> new UserNotFoundException("User by id: " + id + " is not found"));
    }


    // ADD EMPLOYEE
    public Employee addEmployee(Employee employee){
        employee.setEmployeeCode(UUID.randomUUID().toString());
        return employeeRepo.save(employee);
    }


}
