package by.gexateq.simplerestservice.controller;

import by.gexateq.simplerestservice.dto.EmployeeDto;
import by.gexateq.simplerestservice.entity.Employee;
import by.gexateq.simplerestservice.entity.Review;
import by.gexateq.simplerestservice.entity.ReviewStatus;
import by.gexateq.simplerestservice.service.EmployeeService;
import by.gexateq.simplerestservice.utilities.HardCodeEmployeeMapper;
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
    HardCodeEmployeeMapper employeeMapper;

    @Test
    void findActiveEmployeesPageable() throws Exception {
        EmployeeDto dto = new EmployeeDto();
        dto.setId(1L);
        dto.setEmail("test@test.com");
        dto.setFirstName("First Name");
        dto.setSurname("Last Name");

        Review review = new Review();
        review.setId(2L);
        review.setStatus(ReviewStatus.DRAFT);
        review.setCreatedAt(LocalDateTime.of(2024, 3,27,0,0,0));
        dto.setReviews(Collections.singletonList(review));

        when(employeeService.findActiveEmployees(any())).thenReturn(Collections.singletonList(new Employee()));
        when(employeeMapper.toDto(any())).thenReturn(dto);

        ResultActions ra = mockMvc.perform(get("/api/employee?page=0&size=100"));
        ra.andExpect(status().isOk())
                .andExpect(jsonPath("$.*").value(hasSize(1)))
                .andExpect(jsonPath("$.[0].*").value(hasSize(5)))
                .andExpect(jsonPath("$.[0].id").value(equalTo(1)))
                .andExpect(jsonPath("$.[0].email").value(equalTo(dto.getEmail())))
                .andExpect(jsonPath("$.[0].surname").value(equalTo(dto.getSurname())))
                .andExpect(jsonPath("$.[0].firstName").value(equalTo(dto.getFirstName())))
                .andExpect(jsonPath("$.[0].reviews[0].id").value(equalTo(2)))
                .andExpect(jsonPath("$.[0].reviews[0].status").value(equalTo(review.getStatus().name())))
                .andExpect(jsonPath("$.[0].reviews[0].createdAt").value(equalTo("2024-03-27T00:00:00")));
    }
}

