package exercise.controller;

import exercise.dto.TaskCreateDTO;
import exercise.dto.TaskDTO;
import exercise.dto.TaskUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.TaskMapper;
import exercise.repository.TaskRepository;
import exercise.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TasksController {
    // BEGIN
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskMapper taskMapper;

    @GetMapping(path = "")
    public List<TaskDTO> index() {

        var tasks = taskRepository.findAll();
        return tasks.stream()
                .map(p -> taskMapper.map(p))
                .toList();
    }

    @GetMapping(path = "/{id}")
    public TaskDTO show(@PathVariable long id) {

        var task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + id + " not found"));
        var taskDTO = taskMapper.map(task);
        return taskDTO;
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDTO create(@Valid @RequestBody TaskCreateDTO taskData) {

        var task = taskMapper.map(taskData);
        taskRepository.save(task);
        var taskDTO = taskMapper.map(task);
        return taskDTO;
    }

    @PutMapping(path = "/{id}")
    public TaskDTO update(@PathVariable long id,
                          @Valid @RequestBody TaskUpdateDTO taskData) {

        var task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + id + " not found"));
        var user = userRepository.findById(taskData.getAssigneeId())
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + taskData.getAssigneeId() + " not found"));
        taskMapper.update(taskData, task);
        task.setAssignee(user);
        taskRepository.save(task);
        var taskDTO = taskMapper.map(task);
        return taskDTO;
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable long id) {
        taskRepository.deleteById(id);
    }


    // END
}
