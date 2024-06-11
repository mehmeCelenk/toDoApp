package com.ToDo.app.dto.request;

import com.ToDo.app.model.enums.ToDoEnums;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record ToDoCreateRequest(
    @NotBlank
    String description,
    ToDoEnums status
) {

}
