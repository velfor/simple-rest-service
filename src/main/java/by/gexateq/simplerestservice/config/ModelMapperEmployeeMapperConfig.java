package by.gexateq.simplerestservice.config;

import by.gexateq.simplerestservice.dto.EmployeeDto;
import by.gexateq.simplerestservice.entity.Employee;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;
//import org.modelmapper.config.Configuration;
import org.modelmapper.convention.NameTokenizers;
import org.springframework.context.annotation.Bean;


@Configuration
public class ModelMapperEmployeeMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setSourceNameTokenizer(NameTokenizers.CAMEL_CASE)
                .setDestinationNameTokenizer(NameTokenizers.UNDERSCORE);

        modelMapper.typeMap(Employee.class, EmployeeDto.class)
                .addMapping(Employee::getLastName, EmployeeDto::setSurname);

        return modelMapper;
    }
}
