package by.gexateq.simplerestservice.service;

import by.gexateq.simplerestservice.entity.Employee;

import java.util.Optional;

public interface EmployeeService {
    Employee save(Employee employee);

    Optional<Employee> findById(Long id);

    boolean existsById(Long id);

    void deleteById(Long id);

    boolean update(Employee employee, Long id);
}
