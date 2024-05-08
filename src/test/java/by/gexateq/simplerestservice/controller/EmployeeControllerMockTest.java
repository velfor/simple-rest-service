package by.gexateq.simplerestservice.controller;

import by.gexateq.simplerestservice.dto.EmployeeDto;
import by.gexateq.simplerestservice.dto.ReviewDto;
import by.gexateq.simplerestservice.entity.Employee;
import by.gexateq.simplerestservice.service.EmployeeService;
import by.gexateq.simplerestservice.utilities.EmployeeMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerMockTest {

    @SuppressWarnings("unused")
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    EmployeeService employeeService;
    @MockBean
    EmployeeMapper employeeMapper;

    @Test
    void findActiveEmployeesPageable() throws Exception {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(1L);
        employeeDto.setFirstName("First Name");
        employeeDto.setSurname("Last Name");
        employeeDto.setEmail("test@test.com");


        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(2L);
        reviewDto.setStatus("DRAFT");
        reviewDto.setCreated(LocalDateTime.of(2024, 3,27,0,0,0));
        employeeDto.setReviews(Collections.singletonList(reviewDto));

        when(employeeService.findActiveEmployees(any())).thenReturn(Collections.singletonList(new Employee()));
        when(employeeMapper.toDto(any())).thenReturn(employeeDto);

        ResultActions ra = mockMvc.perform(get("/api/employee?page=0&size=100"));
        ra.andExpect(status().isOk())
                .andExpect(jsonPath("$.*").value(hasSize(1)))
                .andExpect(jsonPath("$.[0].*").value(hasSize(5)))
                .andExpect(jsonPath("$.[0].id").value(equalTo(1)))
                .andExpect(jsonPath("$.[0].email").value(equalTo(employeeDto.getEmail())))
                .andExpect(jsonPath("$.[0].surname").value(equalTo(employeeDto.getSurname())))
                .andExpect(jsonPath("$.[0].firstName").value(equalTo(employeeDto.getFirstName())))
                .andExpect(jsonPath("$.[0].reviews[0].id").value(equalTo(2)))
                .andExpect(jsonPath("$.[0].reviews[0].status").value(equalTo(reviewDto.getStatus())))
                .andExpect(jsonPath("$.[0].reviews[0].created").value(equalTo("2024-03-27T00:00:00")));
    }
}

