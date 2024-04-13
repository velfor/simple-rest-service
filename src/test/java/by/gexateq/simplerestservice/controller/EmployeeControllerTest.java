package by.gexateq.simplerestservice.controller;

import by.gexateq.simplerestservice.dto.EmployeeDto;
import by.gexateq.simplerestservice.dto.ReviewDto;
import by.gexateq.simplerestservice.entity.Employee;
import by.gexateq.simplerestservice.entity.Review;
import by.gexateq.simplerestservice.entity.ReviewStatus;
import by.gexateq.simplerestservice.service.EmployeeService;
import by.gexateq.simplerestservice.utilities.EmployeeMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {
    @Mock
    private EmployeeMapper mapper;
    @Mock
    private EmployeeService service;
    @InjectMocks
    private EmployeeController controller;
    private Employee employee1;
    private Employee employee2;
    private EmployeeDto employeeDto1;

    @BeforeEach
    void setUp() {
        employee1 = Employee.builder().
                id(1L).firstName("Иван").lastName("Иванов").email("ivanov@gmail.com").isActive(true).build();
        employee2 = Employee.builder().
                id(2L).firstName("Петр").lastName("Петров").email("petrov@gmail.com").isActive(true).build();
        var date1 = LocalDateTime.of(2022, 10, 31, 12, 30);
        var date2 = LocalDateTime.of(2023, 8, 1, 10, 00);
        var review1 = new Review(1L, ReviewStatus.DONE, employee1, date1);
        var review2 = new Review(2L, ReviewStatus.CANCELLED, employee2, date2);
        var reviews1 = new ArrayList<>(List.of(review1));
        var reviews2 = new ArrayList<>(List.of(review2));
        employee1.setReviews(reviews1);
        employee2.setReviews(reviews2);

        employeeDto1 = new EmployeeDto();
        employeeDto1.setId(1L);
        employeeDto1.setFirstName("First Name");
        employeeDto1.setSurname("Last Name");
        employeeDto1.setEmail("test@test.com");
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(2L);
        reviewDto.setStatus("DRAFT");
        reviewDto.setCreated(LocalDateTime.of(2024, 3, 27, 0, 0, 0));
        employeeDto1.setReviews(Collections.singletonList(reviewDto));
    }

    @Test
    void whenCreateEmployee_thenStatusIsCreated() {
        //Given
        when(mapper.toEntity(any(EmployeeDto.class))).thenReturn(employee1);
        when(service.save(any(Employee.class))).thenReturn(employee1);
        // When
        var response = controller.create(employeeDto1);
        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(service).save(any(Employee.class));
    }

    @Test
    void whenFindById_thenEmployeeShouldBeFound() {
        Employee employeeFound = employee1;
        when(service.findById(1L)).thenReturn(Optional.of(employeeFound));

        var response = controller.findById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employeeFound, ((Optional<Employee>) response.getBody()).get());
        assertEquals(employeeFound.getFirstName(), ((Optional<Employee>) response.getBody()).get().getFirstName());
        verify(service).findById(1L);
    }

    @Test
    void whenFindById_thenEmployeeShouldNotBeFound() {
        when(service.findById(1L)).thenReturn(Optional.empty());

        var response = controller.findById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(service).findById(1L);
    }

    @Test
    void whenUpdateEmployee_thenEmployeeShouldBeUpdated() {
        when(mapper.toEntity(any(EmployeeDto.class))).thenReturn(employee1);
        when(service.update(anyLong(), any(Employee.class))).thenReturn(true);

        var response = controller.update(1L, employeeDto1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void whenUpdateEmployee_thenEmployeeShouldNotBeUpdated() {
        when(mapper.toEntity(any(EmployeeDto.class))).thenReturn(employee1);
        when(service.update(anyLong(), any(Employee.class))).thenReturn(false);

        var response = controller.update(1L, employeeDto1);

        assertEquals(HttpStatus.NOT_MODIFIED, response.getStatusCode());
    }

    @Test
    void whenDeleteEmployee_thenEmployeeShouldBeDeleted() {
        when(service.deleteById(1L)).thenReturn(true);

        var response = controller.delete(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void whenDeleteEmployee_thenEmployeeShouldNotBeDeleted() {
        when((service).deleteById(12L)).thenReturn(false);

        var response = controller.delete(12L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("GET /api/emplooyee/active возвращает HTTP-ответ со статусом 200 OK и списком EmployeeDto с пагинацией" +
            " и сортировкой")
    void findActiveEmployees_ReturnsValidResponseEntity() throws Exception {
        // given
        var activeEmployees = List.of(employee1, employee2);
        var activeEmployeesDto = List.of(employeeDto1, employeeDto1);
        Pageable pageable = PageRequest.of(0, 5, Sort.Direction.ASC, "lastName");
        // when
        doReturn(activeEmployees).when(service).findActiveEmployees(pageable);
        when(mapper.toDto(any(Employee.class))).thenReturn(employeeDto1);
        var responseEntity =
                this.controller.findActiveEmployees(0, 5, "lastName", "asc");

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(activeEmployeesDto, responseEntity.getBody());
        assertEquals(employeeDto1.getFirstName(), responseEntity.getBody().get(0).getFirstName());
        assertEquals(employeeDto1.getSurname(), responseEntity.getBody().get(0).getSurname());
        assertEquals(employeeDto1.getEmail(), responseEntity.getBody().get(0).getEmail());
        assertEquals(employeeDto1.getReviews().get(0).getId(), responseEntity.getBody().get(0).getReviews().get(0).getId());
    }
}
