package br.com.rotina.project.task_category.dto;

import jakarta.validation.constraints.NotBlank;

public record TaskCategoryRequest(
        @NotBlank String title,
        String color,
        Boolean active
) {
}
