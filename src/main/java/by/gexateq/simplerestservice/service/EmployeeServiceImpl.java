package by.gexateq.simplerestservice.service;

import by.gexateq.simplerestservice.entity.Employee;
import by.gexateq.simplerestservice.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;

    @Override
    public Employee save(Employee employee) {
        return this.employeeRepository.save(employee);
    }

    @Override
    public Iterable<Employee> findAll() {
        return this.employeeRepository.findAll();
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
    public void delete(Employee employee) {
        this.employeeRepository.delete(employee);
    }
}
