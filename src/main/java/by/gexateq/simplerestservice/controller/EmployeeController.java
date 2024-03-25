package by.gexateq.simplerestservice.controller;

import by.gexateq.simplerestservice.entity.Employee;
import by.gexateq.simplerestservice.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class EmployeeController {
    private final EmployeeService employeeService;

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
