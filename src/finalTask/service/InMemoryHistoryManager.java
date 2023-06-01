package finalTask.service;

import finalTask.model.Task;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {
    private ModeLinkedList<Task> historyList = new ModeLinkedList<>();

    @Override
    public ArrayList<Task> getHistory() {
        ArrayList<Task> history = new ArrayList<>();
        return history;
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
