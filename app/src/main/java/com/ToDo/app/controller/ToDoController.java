package com.ToDo.app.controller;

import com.ToDo.app.dto.request.ToDoCreateRequest;
import com.ToDo.app.dto.request.ToDoEnumsUpdate;
import com.ToDo.app.dto.request.ToDoUpdateRequest;
import com.ToDo.app.dto.response.ToDoResponse;
import com.ToDo.app.service.ToDoService;
import jakarta.validation.Valid;
import jdk.jshell.Snippet;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/todo")
@CrossOrigin
public class ToDoController {
    private final ToDoService toDoService;

    public ToDoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ToDoResponse>> getall(){
        return ResponseEntity.ok(toDoService.getAllToDos());
    }

    @PostMapping("/create")
    public ResponseEntity<ToDoResponse> createToDo(@Valid @RequestBody ToDoCreateRequest toDoCreateRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(toDoService.createToDo(toDoCreateRequest));
    }

    @PutMapping("/update")
    public ResponseEntity<ToDoResponse> updateToDo(@Valid @RequestBody ToDoUpdateRequest toDoUpdateRequest){
        return ResponseEntity.ok(toDoService.updateToDo(toDoUpdateRequest));
    }

    @PutMapping("/updateStatus")
    public ResponseEntity<ToDoResponse> updateStatus(@RequestBody ToDoEnumsUpdate toDoEnumsUpdate){
        return ResponseEntity.ok(toDoService.updateStatus(toDoEnumsUpdate));
    }

    @DeleteMapping("/delete/{id}")
    public String deleteToDo(@PathVariable Long id) {
        return toDoService.deleteToDo(id);
    }

}
