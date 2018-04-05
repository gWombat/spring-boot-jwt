package fr.gwombat.springbootjwt.task;

import java.util.List;

public interface TaskService {

    Task getById(Long id);

    List<Task> getAll();

    Task create(Task task);

    void delete(Task task);
}
