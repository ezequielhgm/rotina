package br.com.rotina.project.task_sequence.service;

import br.com.rotina.project.task.entity.Task;
import br.com.rotina.project.task.repository.TaskRepository;
import br.com.rotina.project.task_sequence.dto.TaskSequenceRequest;
import br.com.rotina.project.task_sequence.dto.TaskSequenceResponse;
import br.com.rotina.project.task_sequence.entity.TaskSequence;
import br.com.rotina.project.task_sequence.repository.TaskSequenceRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TaskSequenceService {

    private final TaskSequenceRepository sequenceRepository;
    private final TaskRepository taskRepository;

    public TaskSequenceService(
            TaskSequenceRepository sequenceRepository,
            TaskRepository taskRepository
    ) {
        this.sequenceRepository = sequenceRepository;
        this.taskRepository = taskRepository;
    }

    @Transactional
    public TaskSequenceResponse create(TaskSequenceRequest request) {
        TaskSequence sequence = new TaskSequence();
        applyRequest(sequence, request);

        return toResponse(sequenceRepository.save(sequence));
    }

    @Transactional(readOnly = true)
    public List<TaskSequenceResponse> findAll(Integer taskId) {
        List<TaskSequence> sequences = taskId == null
                ? sequenceRepository.findAll()
                : sequenceRepository.findAllByTaskIdOrderByCompletionDateDesc(taskId);

        return sequences.stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public TaskSequenceResponse findById(Integer id) {
        return toResponse(findEntityById(id));
    }

    @Transactional
    public TaskSequenceResponse update(Integer id, TaskSequenceRequest request) {
        TaskSequence sequence = findEntityById(id);
        applyRequest(sequence, request);

        return toResponse(sequenceRepository.save(sequence));
    }

    @Transactional
    public TaskSequenceResponse deactivate(Integer id) {
        TaskSequence sequence = findEntityById(id);
        sequence.setActive(false);

        return toResponse(sequenceRepository.save(sequence));
    }

    private TaskSequence findEntityById(Integer id) {
        return sequenceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task sequence not found"));
    }

    private Task findTaskById(Integer id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
    }

    private void applyRequest(TaskSequence sequence, TaskSequenceRequest request) {
        sequence.setTask(findTaskById(request.taskId()));
        sequence.setCompletionDate(request.completionDate());
        sequence.setActive(request.active() == null || request.active());
    }

    private TaskSequenceResponse toResponse(TaskSequence sequence) {
        return new TaskSequenceResponse(
                sequence.getId(),
                sequence.getTask().getId(),
                sequence.getCompletionDate(),
                sequence.isActive()
        );
    }
}
