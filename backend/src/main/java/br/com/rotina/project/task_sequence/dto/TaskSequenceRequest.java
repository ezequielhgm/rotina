package br.com.rotina.project.task_sequence.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDate;

public record TaskSequenceRequest(@NotNull Integer taskId, @NotNull @PastOrPresent LocalDate completionDate, Boolean active) {
}
