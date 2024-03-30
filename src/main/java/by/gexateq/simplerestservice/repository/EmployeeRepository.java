package by.gexateq.simplerestservice.repository;

import by.gexateq.simplerestservice.entity.Employee;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByIsActive(boolean isActive, Pageable pageable);
}