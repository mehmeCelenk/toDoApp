package com.ToDo.app.dto.response;

import com.ToDo.app.model.entity.ToDo;
import java.time.LocalDateTime;

public record ToDoResponse(
        long id,
        String description,
        LocalDateTime createdDate,
        LocalDateTime updateDate,
        String status
) {
    public static ToDoResponse convert(ToDo toDo) {
        return new ToDoResponse(
                toDo.getId(),
                toDo.getDescription(),
                toDo.getCreatedDate(),
                toDo.getUpdateDate(),
                toDo.getStatus().getValue()
        );
    }
}
