package finalTask.service;

import finalTask.model.HistoryManager;
import finalTask.model.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryHistoryManager implements HistoryManager {

    private static int setNumber = 0;

    private ArrayList<Task> historyList = new ArrayList<>();
    private HashMap<Integer, Task> history = new HashMap<>();

    @Override
    public ArrayList<Task> getHistory() {
        for (int i = 0; i < history.size(); i++) {
            historyList.add(history.get(i));
        }
        return historyList;
    }

    @Override
    public void add(Task task) {
        putInHistory(task);
    }
    private void checkNumber() {
        if (setNumber >= 10) {
            setNumber = 0;
        }
    }

    public void putInHistory(Task task) {
        if (history.containsKey(setNumber)) {
            history.remove(setNumber);
            history.put(setNumber, task);
        } else {
            history.put(setNumber, task);
        }
        setNumber++;
        checkNumber();
    }
}
