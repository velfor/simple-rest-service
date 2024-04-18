package by.gexateq.simplerestservice.repository;

import by.gexateq.simplerestservice.entity.Employee;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByIsActive(boolean isActive, Pageable pageable);

    @Query(value = "SELECT DISTINCT e.* FROM employees AS e " +
            "WHERE NOT EXISTS (SELECT 1 FROM reviews AS r WHERE r.employee_id = e.id AND r.status <> 'CANCELLED')",
            nativeQuery = true)
    List<Employee> findEmployeesWithAllReviewsCancelled();
}