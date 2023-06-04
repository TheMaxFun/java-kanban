package finalTask.service;

import finalTask.model.Task;

import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final ModeLinkedList<Task> historyList = new ModeLinkedList<>();

    @Override
    public List<Task> getHistory() {
        return historyList.getTask();
    }

    @Override
    public void add(Task task) {
        historyList.addFirst(task, task.getId());
    }

    @Override
    public void remove (int id) {
        historyList.remove(id);
    }
}
