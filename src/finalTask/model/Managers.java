package finalTask.model;

import finalTask.service.InMemoryHistoryManager;
import finalTask.service.InMemoryTaskManager;

public class Managers{

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
