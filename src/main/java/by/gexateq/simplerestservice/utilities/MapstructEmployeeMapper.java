package by.gexateq.simplerestservice.utilities;

import by.gexateq.simplerestservice.dto.EmployeeDto;
import by.gexateq.simplerestservice.entity.Employee;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        uses = MapstructReviewListMapper.class,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface MapstructEmployeeMapper {
    @Mapping(target = "surname", source = "lastName")
    EmployeeDto toDto(Employee employee);

    @Mapping(target = "lastName", source = "surname")
    Employee toEntity(EmployeeDto employeeDto);
}
