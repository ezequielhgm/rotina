package br.com.rotina.project.task.entity;

import br.com.rotina.project.user.entity.User;
import br.com.rotina.project.task_category.entity.TaskCategory;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "task", schema = "public")
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fk_user", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_category")
    private TaskCategory category;

    @Column(name = "task_title", nullable = false)
    private String title;

    @Column(name = "task_description", columnDefinition = "text")
    private String description;

    @Column(name = "task_start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "task_active", nullable = false)
    private boolean active = true;

}
