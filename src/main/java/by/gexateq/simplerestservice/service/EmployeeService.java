package by.gexateq.simplerestservice.service;

import by.gexateq.simplerestservice.entity.Employee;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Employee save(Employee employee);
    Optional<Employee> findById(Long id);
    boolean existsById(Long id);
    boolean deleteById(Long id);
    boolean update(Long id, Employee employee);
    List<Employee> findActiveEmployees(Pageable pageable);

}
