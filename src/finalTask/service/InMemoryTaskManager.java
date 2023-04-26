package finalTask.service;
import finalTask.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static finalTask.service.Managers.getDefaultHistory;

public class InMemoryTaskManager implements TaskManager {
    private HistoryManager historyManager = getDefaultHistory();
    private static int setId = 1;
    private HashMap<Integer,Epic> epics = new HashMap<>();
    private HashMap<Integer,Task> tasks = new HashMap<>();
    private HashMap<Integer,Subtask> subtasks = new HashMap<>();

    @Override
    public Map<Integer, Epic> getAllEpic() {
        return epics;
    }

    @Override
    public Map<Integer, Subtask> getAllSubtasks() {
        return subtasks;
    }

    @Override
    public Map<Integer, Task> getAllTask() {
        return tasks;
    }

    @Override
    public void clearAllEpic() {
        epics.clear();
        subtasks.clear();
    }

    @Override
    public void clearAllTask() {
        tasks.clear();
    }

    @Override
    public void clearAllSubtask() {
        subtasks.clear();
        for (int i = 0; i < epics.size(); i++) {
            setEpicStatus(epics.get(i));
        }
    }

    @Override
    public Epic getByIdEpic(int id) {
        historyManager.add(subtasks.get(id));
        return epics.get(id);
    }

    @Override
    public Subtask getByIdSubtask(int id) {
        historyManager.add(subtasks.get(id));
        return subtasks.get(id);
    }

    @Override
    public Task getByIdTask (int id) {
        historyManager.add(subtasks.get(id));
        return tasks.get(id);
    }

    @Override
    public void createEpicTask(Epic epic) {
        epic.setId(setId);
        setId++;
        epics.put(epic.getId(), epic);
    }

    @Override
    public void createTask(Task task) {
        task.setId(setId);
        setId++;
        tasks.put(task.getId(), task);
    }

    @Override
    public void createSubtask(Subtask subtask) {
        subtask.setId(setId);
        setId++;
        subtasks.put(subtask.getId(), subtask);
        epics.get(subtask.getEpicId()).addIdSubtask(subtask.getId());
    }

    @Override
    public void updateEpicTask(Epic epic) {
        epics.get(epic.getId()).setTitle(epic.getTitle());
        epics.get(epic.getId()).setDescription(epic.getDescription());
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        subtasks.get(subtask.getId()).setTitle(subtask.getTitle());
        subtasks.get(subtask.getId()).setDescription(subtask.getDescription());
        subtasks.get(subtask.getId()).setStatus(subtask.getStatus());
        setEpicStatus(epics.get(subtask.getEpicId()));
    }

    @Override
    public void updateTask(Task task) {
        tasks.get(task.getId()).setTitle(task.getTitle());
        tasks.get(task.getId()).setDescription(task.getDescription());
        tasks.get(task.getId()).setStatus(task.getStatus());
    }

    @Override
    public void deleteByIdEpic(int id) {
        for (int i = 0; i < epics.get(id).getIdSubtasks().size(); i++) {
            subtasks.remove(epics.get(id).getIdSubtasks().get(i));
        }
        epics.remove(id);
    }

    @Override
    public void deleteByIdSubtask(int id) {
        int idEpic = subtasks.get(id).getEpicId();
        epics.get(subtasks.get(id).getEpicId()).removeIdSubtask(id);
        subtasks.remove(id);
        setEpicStatus(epics.get(idEpic));
    }

    @Override
    public void deleteByIdTask(int id) {
        tasks.remove(id);
    }

    public List<Subtask> getSubtaskByEpic(Epic epic) {
        ArrayList<Subtask> subtasksByEpic = new ArrayList<>();
        for (int i = 0; i < epic.getIdSubtasks().size(); i++) {
            subtasksByEpic.add(subtasks.get(epic.getIdSubtasks().get(i)));
        }
        return subtasksByEpic;
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    private void setEpicStatus(Epic epic) {
        int doneSubtask = 0;
        int inProgressSubtask = 0;
        List<Subtask> subtasksByEpic = new ArrayList<>();
        subtasksByEpic = getSubtaskByEpic(epic);
        for (int i = 0; i < subtasksByEpic.size(); i++) {
            if (subtasksByEpic.get(i).getStatus() == Status.IN_PROGRESS) {
                inProgressSubtask += 1;
            } else if (subtasksByEpic.get(i).getStatus() == Status.DONE) {
                doneSubtask += 1;
            }
        }
        if (doneSubtask == subtasksByEpic.size()) {
            epic.setStatus(Status.DONE);
        } else if (inProgressSubtask > 0) {
            epic.setStatus(Status.IN_PROGRESS);
        } else if (doneSubtask > 0 && doneSubtask != subtasksByEpic.size()) {
            epic.setStatus(Status.IN_PROGRESS);
        } else if (doneSubtask == 0 && inProgressSubtask == 0) {
            epic.setStatus(Status.DONE);
        }
    }

}