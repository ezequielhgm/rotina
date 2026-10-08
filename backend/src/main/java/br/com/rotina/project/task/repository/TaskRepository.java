package br.com.rotina.project.task.repository;

import br.com.rotina.project.task.entity.Task;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    List<Task> findAllByUserIdOrderByIdDesc(Integer userId);
}
