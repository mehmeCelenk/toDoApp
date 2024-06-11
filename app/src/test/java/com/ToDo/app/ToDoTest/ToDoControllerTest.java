package com.ToDo.app.ToDoTest;

import com.ToDo.app.controller.ToDoController;
import com.ToDo.app.dto.request.ToDoCreateRequest;
import com.ToDo.app.dto.request.ToDoUpdateRequest;
import com.ToDo.app.dto.response.ToDoResponse;
import com.ToDo.app.model.enums.ToDoEnums;
import com.ToDo.app.service.ToDoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
public class ToDoControllerTest {
    private MockMvc mockMvc;

    @Mock
    private ToDoService toDoService;

    @InjectMocks
    private ToDoController toDoController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(toDoController).build();
    }

    @Test
    public void getAllToDosTest() throws Exception {
        // given
        Long id = 1L; // Örnek bir id değeri
        LocalDateTime now = LocalDateTime.now();
        ToDoResponse toDoResponse = new ToDoResponse(id, "Test description", now, now, ToDoEnums.PENDING.getValue());
        List<ToDoResponse> allToDos = Collections.singletonList(toDoResponse);
        given(toDoService.getAllToDos()).willReturn(allToDos);

        // when & then
        mockMvc.perform(get("/v1/todo/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description").value(toDoResponse.description()))
                .andExpect(jsonPath("$[0].status").value(toDoResponse.status()));
    }

    @Test
    public void createToDoTest() throws Exception {
        // given
        LocalDateTime now = LocalDateTime.now();
        Long id = 1L; // Örnek bir ID değeri
        ToDoCreateRequest toDoCreateRequest = new ToDoCreateRequest("New description", ToDoEnums.COMPLETED);
        ToDoResponse toDoResponse = new ToDoResponse(id, "New description", now, now, ToDoEnums.COMPLETED.getValue());
        given(toDoService.createToDo(toDoCreateRequest)).willReturn(toDoResponse);

        // when & then
        mockMvc.perform(post("/v1/todo/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\":\"New description\",\"status\":\"COMPLETED\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description").value("New description"))
                .andExpect(jsonPath("$.status").value(ToDoEnums.COMPLETED.getValue()));
    }

    @Test
    public void updateToDoTest() throws Exception {
        // given
        Long id = 1L;
        LocalDateTime now = LocalDateTime.now();
        ToDoUpdateRequest toDoUpdateRequest = new ToDoUpdateRequest(1L, "Updated description");
        ToDoResponse toDoResponse = new ToDoResponse(id,"Updated description", now, now, ToDoEnums.COMPLETED.getValue());
        given(toDoService.updateToDo(toDoUpdateRequest)).willReturn(toDoResponse);

        // when & then
        mockMvc.perform(put("/v1/todo/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"description\":\"Updated description\",\"updateDate\":\"" + now + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value(toDoResponse.description()))
                .andExpect(jsonPath("$.status").value(toDoResponse.status()));
    }

    @Test
    public void deleteToDoTest() throws Exception {
        // Given
        Long id = 1L;
        // Do nothing when deleteToDo() method is called
        doReturn("successfully deleted").when(toDoService).deleteToDo(id);

        // When & Then
        mockMvc.perform(delete("/v1/todo/delete/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string("successfully deleted"));
    }
}
