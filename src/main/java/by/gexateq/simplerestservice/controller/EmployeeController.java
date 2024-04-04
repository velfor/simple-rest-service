package by.gexateq.simplerestservice.controller;

import by.gexateq.simplerestservice.dto.EmployeeDto;
import by.gexateq.simplerestservice.entity.Employee;
import by.gexateq.simplerestservice.service.EmployeeService;
import by.gexateq.simplerestservice.utilities.EmployeeMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
@Validated
public class EmployeeController {
    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    @SuppressWarnings("unused")
    @PostMapping(value = "/employee")
    public ResponseEntity<?> create(@Valid @RequestBody EmployeeDto employeeDto) {
        Employee employee = employeeMapper.toEntity(employeeDto);
        employee.setIsActive(true);
        employee.getReviews().stream().forEach(review -> review.setEmployee(employee));
        this.employeeService.save(employee);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @SuppressWarnings("unused")
    @GetMapping(value = "/employee/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        final Optional<Employee> employee = employeeService.findById(id);
        if (employee.isPresent()) {
            return ResponseEntity.ok().body(employee);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @SuppressWarnings("unused")
    @GetMapping(value = "/employee/active")
    public ResponseEntity<List<EmployeeDto>> findActiveEmployees(
            @RequestParam(defaultValue = "0") int page,
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

    @SuppressWarnings("unused")
    @GetMapping(value = "/employee")
    public ResponseEntity<List<EmployeeDto>> findActiveEmployeesPageable(
            @PageableDefault(size = 100)
            @SortDefault.SortDefaults({@SortDefault(sort = "lastName", direction = Sort.Direction.DESC)})
            @ParameterObject
            Pageable pageable) {

        List<Employee> activeUsers = employeeService.findActiveEmployees(pageable);
        var activeUsersDto = activeUsers.stream().map(employeeMapper::toDto).toList();
        return ResponseEntity.ok().body(activeUsersDto);
    }

    @SuppressWarnings("unused")
    @PutMapping(value = "/employee/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestBody @Valid EmployeeDto employeeDto) {
        Employee employee = employeeMapper.toEntity(employeeDto);
        employee.setIsActive(true);
        employee.getReviews().stream().forEach(review -> review.setEmployee(employee));
        final boolean updated = employeeService.update(id, employee);

        if (updated)
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
    }

    @SuppressWarnings("unused")
    @DeleteMapping(value = "/employee/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (employeeService.deleteById(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
