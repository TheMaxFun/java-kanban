package finalTask.model;

import java.util.ArrayList;
import java.util.HashMap;

public interface TaskManager <T extends Task> {

    public HashMap<Integer, T> getAllTask(T task);

    public void clearAllTask(T task);

    public T getByIdTask(T task, int id);

    public void createTask(T task);

    public void updateTask(T task);

    public void deleteByIdTask(T task, int id);

}
