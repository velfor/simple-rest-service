package by.gexateq.simplerestservice.utilities;

import by.gexateq.simplerestservice.dto.EmployeeDto;
import by.gexateq.simplerestservice.entity.Employee;
import by.gexateq.simplerestservice.entity.Review;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class EmployeeMapper {
    public EmployeeDto toDto(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employee.getId());
        employeeDto.setFirstName(employee.getFirstName());
        employeeDto.setSurname(employee.getLastName());
        employeeDto.setEmail(employee.getEmail());
        List<Review> reviewsCopy = new ArrayList<>(employee.getReviews());
        employeeDto.setReviews(reviewsCopy);
        return employeeDto;
    }
}
