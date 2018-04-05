package fr.gwombat.springbootjwt.task;

import fr.gwombat.springbootjwt.task.Task;
import fr.gwombat.springbootjwt.task.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Retrieves all stored tasks
     *
     * @return the list of existing tasks
     */
    @GetMapping
    public List<Task> listAll() {
        return taskService.getAll();
    }

    /**
     * Retrives one task by its unique id
     *
     * @param id the unique id
     * @return The corresponding task, or {@code null}
     */
    @GetMapping("/{id}")
    public Task getOneTaskById(@PathVariable("id") final Long id) {
        return taskService.getById(id);
    }

    /**
     * Create and store a new task
     *
     * @param task the task to create
     * @return the newly created task
     */
    @PostMapping
    public ResponseEntity<Task> addTask(@RequestBody @Valid final Task task) {
        final Task createdTask = taskService.create(task);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    /**
     * Delete a task by its id
     *
     * @param id the unique id of the task to delete
     */
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable("id") final Long id) {
        final Task task = taskService.getById(id);
        taskService.delete(task);
    }
}
