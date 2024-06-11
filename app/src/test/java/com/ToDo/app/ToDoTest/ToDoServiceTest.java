package com.ToDo.app.ToDoTest;

import com.ToDo.app.dto.request.ToDoCreateRequest;
import com.ToDo.app.dto.request.ToDoUpdateRequest;
import com.ToDo.app.dto.response.ToDoResponse;
import com.ToDo.app.exception.NotFoundException;
import com.ToDo.app.model.entity.ToDo;
import com.ToDo.app.model.enums.ToDoEnums;
import com.ToDo.app.repository.ToDoRepository;
import com.ToDo.app.service.ToDoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ToDoServiceTest {

    @Mock
    private ToDoRepository toDoRepository;

    @InjectMocks
    private ToDoService toDoService;

    private ToDo toDo;

    @BeforeEach
    void setUp() {
        toDo = new ToDo();
        toDo.setId(1L);
        toDo.setDescription("Test description");
        toDo.setStatus(ToDoEnums.valueOf("PENDING"));
    }

    @Test
    void getAllToDosTest() {
        when(toDoRepository.findAll()).thenReturn(Arrays.asList(toDo));

        List<ToDoResponse> result = toDoService.getAllToDos();

        assertFalse(result.isEmpty());
        assertEquals("Test description", result.get(0).description());
    }

    @Test
    void createToDoTest() {
        ToDoCreateRequest request = new ToDoCreateRequest("New ToDo", ToDoEnums.valueOf("PENDING"));

        when(toDoRepository.save(any(ToDo.class))).thenReturn(toDo);

        ToDoResponse result = toDoService.createToDo(request);

        assertNotNull(result);
        assertEquals("Test description", result.description());
    }

    @Test
    void updateToDoTest() {
        ToDoUpdateRequest request = new ToDoUpdateRequest(1L, "Updated description");

        when(toDoRepository.findById(1L)).thenReturn(Optional.of(toDo));
        when(toDoRepository.save(any(ToDo.class))).thenReturn(toDo);

        ToDoResponse result = toDoService.updateToDo(request);

        assertNotNull(result);
        assertEquals("Updated description", result.description());
    }

    @Test
    void updateToDoNotFoundTest() {
        ToDoUpdateRequest request = new ToDoUpdateRequest(2L, "Updated description");

        when(toDoRepository.findById(2L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class, () -> {
            toDoService.updateToDo(request);
        });

        assertEquals("Not found ToDo with id: 2", exception.getMessage());
    }

    @Test
    void deleteToDoTest() {
        when(toDoRepository.findById(1L)).thenReturn(Optional.of(toDo));
        doNothing().when(toDoRepository).deleteById(1L);

        String result = toDoService.deleteToDo(1L);

        assertEquals("successfully deleted", result);
    }
}
