package br.com.rotina.project.task_sequence.entity;

import br.com.rotina.project.task.entity.Task;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "task_sequence", schema = "public", uniqueConstraints = @UniqueConstraint(name = "task_sequence_date_uk", columnNames = {"fk_task", "task_seq_completion_date"}))
@Getter
@Setter
public class TaskSequence {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "task_seq")
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "fk_task", nullable = false)
    private Task task;
    @Column(name = "task_seq_completion_date", nullable = false)
    private LocalDate completionDate;
    @Column(name = "task_seq_active", nullable = false)
    private boolean active = true;
}
