package br.com.rotina.project.task.service;

import br.com.rotina.project.task.dto.TaskCompletionRequest;
import br.com.rotina.project.task.dto.TaskCompletionResponse;
import br.com.rotina.project.task.dto.TaskHistoryResponse;
import br.com.rotina.project.task.dto.TaskRequest;
import br.com.rotina.project.task.dto.TaskResponse;
import br.com.rotina.project.task.entity.Task;
import br.com.rotina.project.task.entity.TaskCompletion;
import br.com.rotina.project.task.repository.TaskCompletionRepository;
import br.com.rotina.project.task.repository.TaskRepository;
import br.com.rotina.project.user.entity.User;
import br.com.rotina.project.user.repository.UserRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskCompletionRepository taskCompletionRepository;
    private final UserRepository userRepository;

    public TaskService(
            TaskRepository taskRepository,
            TaskCompletionRepository taskCompletionRepository,
            UserRepository userRepository
    ) {
        this.taskRepository = taskRepository;
        this.taskCompletionRepository = taskCompletionRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public TaskResponse create(TaskRequest request) {
        Task task = new Task();
        applyRequest(task, request);
        return toResponse(taskRepository.save(task));
    }

    @Transactional(readOnly = true)
    public List<TaskResponse> findAllByUserId(Integer userId) {
        return taskRepository.findAllByUserIdOrderByIdDesc(userId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public TaskResponse findById(Integer id) {
        return toResponse(findEntityById(id));
    }

    @Transactional
    public TaskResponse update(Integer id, TaskRequest request) {
        Task task = findEntityById(id);
        applyRequest(task, request);
        return toResponse(taskRepository.save(task));
    }

    @Transactional
    public void delete(Integer id) {
        taskRepository.delete(findEntityById(id));
    }

    @Transactional
    public TaskCompletionResponse complete(Integer taskId, TaskCompletionRequest request) {
        Task task = findEntityById(taskId);
        taskCompletionRepository.findByTaskIdAndCompletionDate(taskId, request.completionDate())
                .ifPresent(completion -> {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Task already completed on this date");
                });

        TaskCompletion completion = new TaskCompletion();
        completion.setTask(task);
        completion.setCompletionDate(request.completionDate());
        return toResponse(taskCompletionRepository.save(completion));
    }

    @Transactional
    public void removeCompletion(Integer taskId, LocalDate completionDate) {
        TaskCompletion completion = taskCompletionRepository
                .findByTaskIdAndCompletionDate(taskId, completionDate)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Completion not found"));
        taskCompletionRepository.delete(completion);
    }

    @Transactional(readOnly = true)
    public TaskHistoryResponse getHistory(Integer taskId) {
        findEntityById(taskId);
        List<TaskCompletion> completions = taskCompletionRepository
                .findAllByTaskIdOrderByCompletionDateDesc(taskId);
        List<LocalDate> completedDates = completions.stream()
                .map(TaskCompletion::getCompletionDate)
                .toList();

        return new TaskHistoryResponse(
                taskId,
                calculateCurrentStreak(completedDates),
                taskCompletionRepository.countByTaskId(taskId),
                completedDates
        );
    }

    private Task findEntityById(Integer id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
    }

    private User findUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    private void applyRequest(Task task, TaskRequest request) {
        task.setUser(findUserById(request.userId()));
        task.setName(request.name());
        task.setDescription(request.description());
        task.setStartDate(request.startDate());
        task.setActive(request.active() == null || request.active());
    }

    private TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getUser().getId(),
                task.getName(),
                task.getDescription(),
                task.getStartDate(),
                task.isActive()
        );
    }

    private int calculateCurrentStreak(List<LocalDate> completedDates) {
        Set<LocalDate> dates = completedDates.stream().collect(Collectors.toSet());
        LocalDate expectedDate = LocalDate.now();

        if (!dates.contains(expectedDate)) {
            expectedDate = expectedDate.minusDays(1);
        }

        int streak = 0;
        while (dates.contains(expectedDate)) {
            streak++;
            expectedDate = expectedDate.minusDays(1);
        }
        return streak;
    }

    private TaskCompletionResponse toResponse(TaskCompletion completion) {
        return new TaskCompletionResponse(
                completion.getId(),
                completion.getTask().getId(),
                completion.getCompletionDate(),
                completion.getCreatedAt()
        );
    }
}
