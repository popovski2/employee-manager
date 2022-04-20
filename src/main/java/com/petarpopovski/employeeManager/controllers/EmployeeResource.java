package com.petarpopovski.employeeManager.controllers;

import com.petarpopovski.employeeManager.model.Employee;
import com.petarpopovski.employeeManager.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.slf4j.LoggerFactory;

/************************************************************ DISCLAIMER: ************************************************************

 TO PROVE THAT REDIS IS CACHING PROPERLY, IN THE CONSOLE I LOG THE DATABASE SQL CALLS
 FOR THE FIRST CALL OF SOME FUNCTION WE CAN SEE THE EXACT SQL QUERY THAT IS MADE IN THE DATABASE, BUT IF WE CALL THE SAME FUNCTION
 (FOR EXAMPLE WITH POSTMAN) IN THE LOGGING SECTION WE CAN NOT SEE SQL QUERY THAT IS MADE IN THE DATABASE

 THIS IS BECAUSE REDIS IS CACHING THE RESULTS FROM THE FIRST CALL

****************************************************************************************************************************************/
@RestController
@RequestMapping("/employee")
public class EmployeeResource {

    //logger
    private final Logger logger =  LoggerFactory.getLogger(EmployeeResource.class);


    private final EmployeeService employeeService;

    public EmployeeResource(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @GetMapping("/all")
    public ResponseEntity<List<Employee>> getAllEmployees(){
        //logger.debug("Employee controller: /employee/all :");
        List<Employee> employees = this.employeeService.findAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }


    @GetMapping("/find/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Long id){
        logger.debug("Employee controller: /employee/find/id :");

        Employee employee = this.employeeService.findEmployeeById(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }


    @PostMapping("/add")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee){
        Employee newEmployee = this.employeeService.addEmployee(employee);
        return new ResponseEntity<>(newEmployee,HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee){
        Employee updatedEmployee = this.employeeService.updateEmployee(employee);
        return new ResponseEntity<>(updatedEmployee,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") Long id){
        this.employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
