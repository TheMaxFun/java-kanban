package finalTask.model;

import java.util.List;
import java.util.Map;

public interface TaskManager <T extends Task> {

    public Map<Integer, T> getAllTask(T task);

    public void clearAllTask(T task);

    public T getByIdTask(T task, int id);

    public void createTask(T task);

    public void updateTask(T task);

    public void deleteByIdTask(T task, int id);

    public List<Task> getHistory();
}
