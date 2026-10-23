package br.com.rotina.project.task_sequence.controller;

import br.com.rotina.project.task_sequence.dto.TaskSequenceRequest;
import br.com.rotina.project.task_sequence.dto.TaskSequenceResponse;
import br.com.rotina.project.task_sequence.service.TaskSequenceService;
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
@RequestMapping("/api/v1/task-sequence")
public class TaskSequenceController {

    private final TaskSequenceService taskSequenceService;

    public TaskSequenceController(TaskSequenceService taskSequenceService) {
        this.taskSequenceService = taskSequenceService;
    }

    @PostMapping
    public TaskSequenceResponse create(@Valid @RequestBody TaskSequenceRequest request) {
        return taskSequenceService.create(request);
    }

    @GetMapping
    public List<TaskSequenceResponse> findAll(@RequestParam(required = false) Integer taskId) {
        return taskSequenceService.findAll(taskId);
    }

    @GetMapping("/{id}")
    public TaskSequenceResponse findById(@PathVariable Integer id) {
        return taskSequenceService.findById(id);
    }

    @PutMapping("/{id}")
    public TaskSequenceResponse update(@PathVariable Integer id, @Valid @RequestBody TaskSequenceRequest request) {
        return taskSequenceService.update(id, request);
    }

    @PatchMapping("/{id}/deactivate")
    public TaskSequenceResponse deactivate(@PathVariable Integer id) {
        return taskSequenceService.deactivate(id);
    }
}
