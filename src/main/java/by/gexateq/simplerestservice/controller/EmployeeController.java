package by.gexateq.simplerestservice.controller;

import by.gexateq.simplerestservice.dto.EmployeeDto;
import by.gexateq.simplerestservice.entity.Employee;
import by.gexateq.simplerestservice.service.EmployeeService;
import by.gexateq.simplerestservice.utilities.EmployeeMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    @PostMapping(value = "/employee")
    public ResponseEntity<?> create(@RequestBody Employee employee) {
        this.employeeService.save(employee);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/employee/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        final Optional<Employee> employee = employeeService.findById(id);
        if (employee.isPresent())
            return new ResponseEntity<>(employee, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping(value = "/employee/active")
    public ResponseEntity<List<EmployeeDto>> findActiveEmployees(@RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "5") int size,
                                                                 @RequestParam(required = false) String sortBy,
                                                                 @RequestParam(defaultValue = "asc") String sortDirection) {
        Pageable pageable;
        if (sortBy != null) {
            Sort.Direction direction = sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
            pageable = PageRequest.of(page, size, direction, sortBy);
        } else {
            pageable = PageRequest.of(page, size);
        }


        List<Employee> activeUsers = employeeService.findActiveEmployees(pageable);
        var activeUsersDto = activeUsers.stream().map(employeeMapper::toDto).toList();
        return ResponseEntity.ok().body(activeUsersDto);

    }
    @PutMapping(value = "/employee/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Employee employee) {
        final boolean updated = employeeService.update(employee, id);

        if (updated)
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/employee/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        employeeService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}