package br.com.rotina.project.task.repository;

import br.com.rotina.project.task.entity.TaskCompletion;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskCompletionRepository extends JpaRepository<TaskCompletion, Integer> {

    Optional<TaskCompletion> findByTaskIdAndCompletionDate(Integer taskId, LocalDate completionDate);

    List<TaskCompletion> findAllByTaskIdOrderByCompletionDateDesc(Integer taskId);

    long countByTaskId(Integer taskId);
}
