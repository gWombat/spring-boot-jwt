package fr.gwombat.springbootjwt.task;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task getById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    @Override
    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    @Override
    public Task create(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public void delete(Task task) {
        taskRepository.delete(task);
    }
}
