package br.com.rotina.project.task_category.controller;

import br.com.rotina.project.task_category.dto.TaskCategoryRequest;
import br.com.rotina.project.task_category.dto.TaskCategoryResponse;
import br.com.rotina.project.task_category.service.TaskCategoryService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/task-category")
public class TaskCategoryController {

    private final TaskCategoryService taskCategoryService;

    public TaskCategoryController(TaskCategoryService taskCategoryService) {
        this.taskCategoryService = taskCategoryService;
    }

    @PostMapping
    public TaskCategoryResponse create(@Valid @RequestBody TaskCategoryRequest request) {
        return taskCategoryService.create(request);
    }

    @GetMapping
    public List<TaskCategoryResponse> findAll() {
        return taskCategoryService.findAll();
    }

    @GetMapping("/{id}")
    public TaskCategoryResponse findById(@PathVariable Integer id) {
        return taskCategoryService.findById(id);
    }

    @PutMapping("/{id}")
    public TaskCategoryResponse update(@PathVariable Integer id, @Valid @RequestBody TaskCategoryRequest request) {
        return taskCategoryService.update(id, request);
    }

    @PatchMapping("/{id}/deactivate")
    public TaskCategoryResponse deactivate(@PathVariable Integer id) {
        return taskCategoryService.deactivate(id);
    }
}
