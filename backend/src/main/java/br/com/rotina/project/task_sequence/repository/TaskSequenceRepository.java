package br.com.rotina.project.task_sequence.repository;

import br.com.rotina.project.task_sequence.entity.TaskSequence;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskSequenceRepository extends JpaRepository<TaskSequence, Integer> {
    List<TaskSequence> findAllByTaskIdOrderByCompletionDateDesc(Integer taskId);
}
