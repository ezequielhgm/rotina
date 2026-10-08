package br.com.rotina.project.task.controller;

import br.com.rotina.project.task.dto.TaskCompletionRequest;
import br.com.rotina.project.task.dto.TaskCompletionResponse;
import br.com.rotina.project.task.dto.TaskHistoryResponse;
import br.com.rotina.project.task.dto.TaskRequest;
import br.com.rotina.project.task.dto.TaskResponse;
import br.com.rotina.project.task.service.TaskService;
import jakarta.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskResponse> create(@Valid @RequestBody TaskRequest request) {
        TaskResponse createdTask = taskService.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdTask.id())
                .toUri();
        return ResponseEntity.created(location).body(createdTask);
    }

    @GetMapping
    public List<TaskResponse> findAllByUserId(@RequestParam Integer userId) {
        return taskService.findAllByUserId(userId);
    }

    @GetMapping("/{id}")
    public TaskResponse findById(@PathVariable Integer id) {
        return taskService.findById(id);
    }

    @PutMapping("/{id}")
    public TaskResponse update(@PathVariable Integer id, @Valid @RequestBody TaskRequest request) {
        return taskService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/completion")
    public ResponseEntity<TaskCompletionResponse> complete(
            @PathVariable Integer id,
            @Valid @RequestBody TaskCompletionRequest request
    ) {
        TaskCompletionResponse completion = taskService.complete(id, request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{completionId}")
                .buildAndExpand(completion.id())
                .toUri();
        return ResponseEntity.created(location).body(completion);
    }

    @DeleteMapping("/{id}/completion")
    public ResponseEntity<Void> removeCompletion(
            @PathVariable Integer id,
            @RequestParam LocalDate completionDate
    ) {
        taskService.removeCompletion(id, completionDate);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/history")
    public TaskHistoryResponse getHistory(@PathVariable Integer id) {
        return taskService.getHistory(id);
    }
}
