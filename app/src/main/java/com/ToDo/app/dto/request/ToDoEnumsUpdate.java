package com.ToDo.app.dto.request;

import com.ToDo.app.model.enums.ToDoEnums;

public record ToDoEnumsUpdate(
        long id,
        ToDoEnums status
) {

}
