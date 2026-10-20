package br.com.rotina.project.task.controller;

import br.com.rotina.project.task.dto.TaskRequest;
import br.com.rotina.project.task.dto.TaskResponse;
import br.com.rotina.project.task.service.TaskService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public TaskResponse create(@Valid @RequestBody TaskRequest request) {
        return taskService.create(request);
    }

    @GetMapping
    public List<TaskResponse> findAll(@RequestParam(required = false) Integer userId) {
        return taskService.findAll(userId);
    }

    @GetMapping("/{id}")
    public TaskResponse findById(@PathVariable Integer id) {
        return taskService.findById(id);
    }

    @PutMapping("/{id}")
    public TaskResponse update(@PathVariable Integer id, @Valid @RequestBody TaskRequest request) {
        return taskService.update(id, request);
    }

    @PatchMapping("/{id}/deactivate")
    public TaskResponse deactivate(@PathVariable Integer id) {
        return taskService.deactivate(id);
    }
}
