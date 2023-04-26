package finalTask.service;

import finalTask.model.HistoryManager;
import finalTask.model.Task;

import java.util.List;
import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {

    private List<Task> historyList = new ArrayList<>();

    @Override
    public List<Task> getHistory() {
        return historyList;
    }

    @Override
    public void add(Task task) {
        if (historyList.size() >=10 ) {
            historyList.remove(historyList.size()-1);
        }
        historyList.add(task);
    }
}
