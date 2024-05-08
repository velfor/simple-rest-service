package by.gexateq.simplerestservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

