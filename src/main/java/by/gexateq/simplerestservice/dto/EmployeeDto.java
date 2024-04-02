package by.gexateq.simplerestservice.dto;

import by.gexateq.simplerestservice.entity.Review;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class EmployeeDto {
    private Long id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String surname;
    @NotBlank
    private String email;
    @NotNull
    private List<ReviewDto> reviews;
}

