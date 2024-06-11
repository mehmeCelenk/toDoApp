package com.ToDo.app.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.ToDo.app.model.enums.ToDoEnums;

@Entity
public class ToDo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;

    @Enumerated(EnumType.STRING)
    private ToDoEnums status;

    @PrePersist
    protected void onCreate() {
        createdDate = LocalDateTime.now();
        updateDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updateDate = LocalDateTime.now();
    }

    public ToDo() {
    }

    public ToDo(Long id, String description, LocalDateTime createdDate, LocalDateTime updateDate, ToDoEnums status) {
        this.id = id;
        this.description = description;
        this.createdDate = createdDate;
        this.updateDate = updateDate;
        this.status = status;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }


    public ToDoEnums getStatus() {
        return status;
    }

    public void setStatus(ToDoEnums status) {
        this.status = status;
    }
}
