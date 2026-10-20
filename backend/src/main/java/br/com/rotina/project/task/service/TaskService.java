package br.com.rotina.project.task.service;

import br.com.rotina.project.task.dto.TaskRequest;
import br.com.rotina.project.task.dto.TaskResponse;
import br.com.rotina.project.task.entity.Task;
import br.com.rotina.project.task.repository.TaskRepository;
import br.com.rotina.project.task_category.entity.TaskCategory;
import br.com.rotina.project.task_category.repository.TaskCategoryRepository;
import br.com.rotina.project.user.entity.User;
import br.com.rotina.project.user.repository.UserRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskCategoryRepository categoryRepository;

    public TaskService(
            TaskRepository taskRepository,
            UserRepository userRepository,
            TaskCategoryRepository categoryRepository
    ) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public TaskResponse create(TaskRequest request) {
        Task task = new Task();
        applyRequest(task, request);

        return toResponse(taskRepository.save(task));
    }

    @Transactional(readOnly = true)
    public List<TaskResponse> findAll(Integer userId) {
        List<Task> tasks = userId == null
                ? taskRepository.findAll()
                : taskRepository.findAllByUserIdOrderByIdDesc(userId);

        return tasks.stream()
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
    public TaskResponse deactivate(Integer id) {
        Task task = findEntityById(id);
        task.setActive(false);

        return toResponse(taskRepository.save(task));
    }

    private Task findEntityById(Integer id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
    }

    private User findUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    private TaskCategory findCategoryById(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task category not found"));
    }

    private void applyRequest(Task task, TaskRequest request) {
        task.setUser(findUserById(request.userId()));
        task.setCategory(request.categoryId() == null ? null : findCategoryById(request.categoryId()));
        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setStartDate(request.startDate());
        task.setActive(request.active() == null || request.active());
    }

    private TaskResponse toResponse(Task task) {
        Integer categoryId = task.getCategory() == null ? null : task.getCategory().getId();

        return new TaskResponse(
                task.getId(),
                task.getUser().getId(),
                categoryId,
                task.getTitle(),
                task.getDescription(),
                task.getStartDate(),
                task.isActive()
        );
    }
}
