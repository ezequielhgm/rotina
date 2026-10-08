package br.com.rotina.project.task.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record TaskRequest(
        @NotNull Integer userId,
        @NotBlank String name,
        String description,
        @NotNull LocalDate startDate,
        Boolean active
) {
}
