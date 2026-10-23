package br.com.rotina.project.task_category.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "task_category", schema = "public")
@Getter
@Setter
public class TaskCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_cat_id")
    private Integer id;

    @Column(name = "task_cat_title", nullable = false)
    private String title;

    @Column(name = "task_cat_color")
    private String color;

    @Column(name = "task_cat_active", nullable = false)
    private boolean active = true;
}
