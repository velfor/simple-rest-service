package by.gexateq.simplerestservice.controller;

import by.gexateq.simplerestservice.dto.EmployeeDto;
import by.gexateq.simplerestservice.entity.Employee;
import by.gexateq.simplerestservice.entity.Review;
import by.gexateq.simplerestservice.entity.ReviewStatus;
import by.gexateq.simplerestservice.service.EmployeeService;
import by.gexateq.simplerestservice.utilities.EmployeeMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {
    @Mock
    EmployeeService service;
    @InjectMocks
    EmployeeController controller;

    @Test
    @DisplayName("GET /api/emplooyee/active возвращает HTTP-ответ со статусом 200 OK и списком EmployeeDto с пагинацией и сортировкой")
    void findActiveEmployees_ReturnsValidResponseEntity() throws Exception {
        // given
        var employee1 = new Employee(1L, "Иван","Иванов","ivanov@gmail.com", true);
        var employee2 = new Employee(2L, "Петр","Петров","petrov@gmail.com", true);
        var date1 = LocalDateTime.of(2022, 10, 31, 12, 30);
        var date2 = LocalDateTime.of(2023, 8, 1, 10, 00);
        var review1 = new Review(1L, ReviewStatus.DONE, employee1, date1);
        var review2 = new Review(2L, ReviewStatus.CANCELLED, employee2, date2);
        var reviews1 = new ArrayList<Review>();
        reviews1.add(review1);
        var reviews2 = new ArrayList<Review>();
        reviews2.add(review2);
        employee1.setReviews(reviews1);
        employee2.setReviews(reviews2);
        var activeEmployees =List.of(employee1,employee2);
        EmployeeMapper employeeMapper = new EmployeeMapper();
        var empDto1 = employeeMapper.toDto(employee1);
        var empDto2 = employeeMapper.toDto(employee2);
        var activeEmployeesDto =List.of(empDto1,empDto2);
        Pageable pageable = PageRequest.of(0,5, Sort.Direction.ASC, "lastName");
        doReturn(activeEmployees).when(this.service).findActiveEmployees(pageable);

        // when
        var responseEntity =
                this.controller.findActiveEmployees(0,5, "lastName","asc" );

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(activeEmployeesDto, responseEntity.getBody());
    }
}
