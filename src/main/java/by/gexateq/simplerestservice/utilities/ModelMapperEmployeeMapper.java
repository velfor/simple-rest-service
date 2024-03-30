package by.gexateq.simplerestservice.utilities;

import by.gexateq.simplerestservice.dto.EmployeeDto;
import by.gexateq.simplerestservice.entity.Employee;
import org.modelmapper.ModelMapper;

public class ModelMapperEmployeeMapper {
    private ModelMapper modelMapper;

    public EmployeeDto toDto(Employee employee) {
        return modelMapper.map(employee, EmployeeDto.class);
    }

    public Employee toEntity(EmployeeDto employeeDto) {
        return modelMapper.map(employeeDto, Employee.class);
    }
}
