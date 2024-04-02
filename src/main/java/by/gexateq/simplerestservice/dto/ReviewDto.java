package by.gexateq.simplerestservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ReviewDto {
    private Long id;
    private String status;
    private LocalDateTime created;
}
