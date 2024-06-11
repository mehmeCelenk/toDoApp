package com.ToDo.app.dto.request;

import com.ToDo.app.model.enums.ToDoEnums;
import jakarta.validation.constraints.NotBlank;


public record ToDoUpdateRequest(
        Long id,
        @NotBlank
        String description

) {
}
