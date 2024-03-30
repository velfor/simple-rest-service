package by.gexateq.simplerestservice.dto;

import by.gexateq.simplerestservice.entity.Review;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
public class EmployeeDto {
    private Long id;
    private String firstName;
    private String surname;
    private String email;
    private List<Review> reviews; // TODO: перейти на ReviewDto, чтобы избавиться от циклической зависимости
}

