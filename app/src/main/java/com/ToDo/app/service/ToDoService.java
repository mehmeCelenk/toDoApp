package com.ToDo.app.service;

import com.ToDo.app.dto.request.ToDoCreateRequest;
import com.ToDo.app.dto.request.ToDoEnumsUpdate;
import com.ToDo.app.dto.request.ToDoUpdateRequest;
import com.ToDo.app.dto.response.ToDoResponse;
import com.ToDo.app.exception.NotFoundException;
import com.ToDo.app.model.entity.ToDo;
import com.ToDo.app.repository.ToDoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ToDoService {
    private final ToDoRepository toDoRepository;

    public ToDoService(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }


    public List<ToDoResponse> getAllToDos() {
        List<ToDo> allToDos = toDoRepository.findAll();
        return allToDos.stream()
                .map(ToDoResponse::convert)
                .collect(Collectors.toList());
    }

    public ToDoResponse createToDo(ToDoCreateRequest toDoCreateRequest){
        ToDo toDo = new ToDo();

        toDo.setDescription(toDoCreateRequest.description());
        toDo.setStatus(toDoCreateRequest.status());

        toDo = toDoRepository.save(toDo);

        return ToDoResponse.convert(toDo);
    }

    public ToDoResponse updateToDo(ToDoUpdateRequest toDoUpdateRequest){
        ToDo toDo = findByToDoId(toDoUpdateRequest.id());

        if(toDo != null){
            toDo.setDescription(toDoUpdateRequest.description());


            toDo = toDoRepository.save(toDo);
            return ToDoResponse.convert(toDo);
        }

        throw new NotFoundException("ToDo not found");
    }

    public ToDoResponse updateStatus(ToDoEnumsUpdate toDoEnumsUpdate){
        ToDo toDo = findByToDoId(toDoEnumsUpdate.id());

        if(toDo != null){
            toDo.setStatus(toDoEnumsUpdate.status());
            toDo = toDoRepository.save(toDo);
            return ToDoResponse.convert(toDo);
        }
        throw new NotFoundException("ToDo not found");
    }

    public String deleteToDo(long id){
        findByToDoId(id);
        toDoRepository.deleteById(id);
        return  "successfully deleted";
    }


    private ToDo findByToDoId(Long id){
        return toDoRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found ToDo with id: " + id));
    }

}
