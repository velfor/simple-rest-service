package by.gexateq.simplerestservice.service;

import by.gexateq.simplerestservice.entity.Employee;
import by.gexateq.simplerestservice.repository.EmployeeRepository;
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
    public void deleteById(Long id) {
        this.employeeRepository.deleteById(id);
    }

    @Override
    public boolean update(Employee employee, Long id) {
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
