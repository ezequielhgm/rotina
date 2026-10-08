package br.com.rotina.project.task.dto;

import java.time.LocalDate;
import java.util.List;

public record TaskHistoryResponse(
        Integer taskId,
        int currentStreak,
        long totalCompletedDays,
        List<LocalDate> completedDates
) {
}
