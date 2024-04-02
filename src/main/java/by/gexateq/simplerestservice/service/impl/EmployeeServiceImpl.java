package by.gexateq.simplerestservice.service.impl;

import by.gexateq.simplerestservice.entity.Employee;
import by.gexateq.simplerestservice.repository.EmployeeRepository;
import by.gexateq.simplerestservice.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Override
    public Employee save(Employee employee) {
        return this.employeeRepository.save(employee);
    }

    @Override
    public Optional<Employee> findById(Long id) {
        return this.employeeRepository.findById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return this.employeeRepository.existsById(id);
    }

    @Override
    public boolean deleteById(Long id) {
        if (existsById(id)) {
            this.employeeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Long id, Employee employee) {
        if (existsById(id)) {
            employee.setId(id);
            employeeRepository.save(employee);
            return true;
        }
        return false;
    }

    public List<Employee> findActiveEmployees(Pageable pageable) {
        return employeeRepository.findByIsActive(true, pageable);
    }
}
