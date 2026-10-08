package br.com.rotina.project.task.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDate;

public record TaskCompletionRequest(
        @NotNull @PastOrPresent LocalDate completionDate
) {
}
