package by.gexateq.simplerestservice.utilities;

import by.gexateq.simplerestservice.dto.EmployeeDto;
import by.gexateq.simplerestservice.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface MapstructEmployeeMapper {
    @Mapping(target = "surname", source = "lastName")
    EmployeeDto toDto(Employee employee);
}
