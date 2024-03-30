package by.gexateq.simplerestservice.utilities;

import by.gexateq.simplerestservice.dto.EmployeeDto;
import by.gexateq.simplerestservice.entity.Employee;
import by.gexateq.simplerestservice.entity.Review;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public class HardCodeEmployeeMapper {
    public static EmployeeDto toDto(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employee.getId());
        employeeDto.setFirstName(employee.getFirstName());
        employeeDto.setSurname(employee.getLastName());
        employeeDto.setEmail(employee.getEmail());
        if (employee.getReviews() != null) {
            List<Review> reviewsCopy = new ArrayList<>(employee.getReviews());
            employeeDto.setReviews(reviewsCopy);
        }
        return employeeDto;
    }


}

