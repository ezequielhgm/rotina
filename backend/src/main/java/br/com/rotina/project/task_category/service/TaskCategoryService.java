package br.com.rotina.project.task_category.service;

import br.com.rotina.project.task_category.dto.TaskCategoryRequest;
import br.com.rotina.project.task_category.dto.TaskCategoryResponse;
import br.com.rotina.project.task_category.entity.TaskCategory;
import br.com.rotina.project.task_category.repository.TaskCategoryRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TaskCategoryService {

    private final TaskCategoryRepository categoryRepository;

    public TaskCategoryService(TaskCategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public TaskCategoryResponse create(TaskCategoryRequest request) {
        TaskCategory category = new TaskCategory();
        applyRequest(category, request);

        return toResponse(categoryRepository.save(category));
    }

    @Transactional(readOnly = true)
    public List<TaskCategoryResponse> findAll() {
        return categoryRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public TaskCategoryResponse findById(Integer id) {
        return toResponse(findEntityById(id));
    }

    @Transactional
    public TaskCategoryResponse update(Integer id, TaskCategoryRequest request) {
        TaskCategory category = findEntityById(id);
        applyRequest(category, request);

        return toResponse(categoryRepository.save(category));
    }

    @Transactional
    public TaskCategoryResponse deactivate(Integer id) {
        TaskCategory category = findEntityById(id);
        category.setActive(false);

        return toResponse(categoryRepository.save(category));
    }

    private TaskCategory findEntityById(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task category not found"));
    }

    private void applyRequest(TaskCategory category, TaskCategoryRequest request) {
        category.setTitle(request.title());
        category.setColor(request.color());
        category.setActive(request.active() == null || request.active());
    }

    private TaskCategoryResponse toResponse(TaskCategory category) {
        return new TaskCategoryResponse(
                category.getId(),
                category.getTitle(),
                category.getColor(),
                category.isActive()
        );
    }
}
