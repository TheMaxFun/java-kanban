package finalTask.service;

import finalTask.model.Epic;
import finalTask.model.Subtask;
import finalTask.model.Task;

import java.util.List;
import java.util.Map;

public interface TaskManager {

    public List<Epic> getAllEpic();

    public List<Subtask> getAllSubtasks();

    public List<Task> getAllTask();

    public void clearAllEpic();

    public void clearAllTask();

    public void clearAllSubtask();

    public Epic getByIdEpic(int id);

    public Subtask getByIdSubtask(int id);

    public Task getByIdTask(int id);

    public void createEpicTask(Epic task);

    public void createTask(Task task);

    public void createSubtask(Subtask task);

    public void updateEpicTask(Epic task);

    public void updateSubtask(Subtask task);

    public void updateTask(Task task);

    public void deleteByIdEpic(int id);

    public void deleteByIdSubtask(int id);

    public void deleteByIdTask(int id);

    public List<Task> getHistory();
}
