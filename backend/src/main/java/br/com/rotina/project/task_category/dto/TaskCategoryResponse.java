package br.com.rotina.project.task_category.dto;

public record TaskCategoryResponse(
        Integer id,
        String title,
        String color,
        boolean active
) {
}
