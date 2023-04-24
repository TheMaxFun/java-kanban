package finalTask.model;

import finalTask.service.InMemoryHistoryManager;
import finalTask.service.InMemoryTaskManager;

public class Managers extends InMemoryTaskManager {

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static InMemoryHistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
