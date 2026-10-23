package br.com.rotina.project.task_sequence.dto;

import java.time.LocalDate;

public record TaskSequenceResponse(Integer id, Integer taskId, LocalDate completionDate, boolean active) {
}
