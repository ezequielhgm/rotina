package br.com.rotina.project.task_category.repository;

import br.com.rotina.project.task_category.entity.TaskCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskCategoryRepository extends JpaRepository<TaskCategory, Integer> {
}
