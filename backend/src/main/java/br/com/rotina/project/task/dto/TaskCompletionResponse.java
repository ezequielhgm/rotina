package br.com.rotina.project.task.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record TaskCompletionResponse(
        Integer id,
        Integer taskId,
        LocalDate completionDate,
        LocalDateTime createdAt
) {
}
