package br.com.rotina.project.task.dto;

import java.time.LocalDate;

public record TaskResponse(
        Integer id,
        Integer userId,
        String name,
        String description,
        LocalDate startDate,
        boolean active
) {
}
