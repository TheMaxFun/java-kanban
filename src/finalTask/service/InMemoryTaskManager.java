package finalTask.service;
import finalTask.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static finalTask.model.Managers.getDefaultHistory;

public class InMemoryTaskManager implements TaskManager {
    private HistoryManager historyManager = getDefaultHistory();
    private static int setId = 1;
    private HashMap<Integer,Epic> epics = new HashMap<>();
    private HashMap<Integer,Task> tasks = new HashMap<>();
    private HashMap<Integer,Subtask> subtasks = new HashMap<>();

    public Map<Integer, Epic> getAllTask(Epic epic) {
        return epics;
    }

    public Map<Integer, Subtask> getAllTask(Subtask subtask) {
        return subtasks;
    }

    @Override
    public Map<Integer, Task> getAllTask(Task task) {
        return tasks;
    }

    public void clearAllEpic(Epic epic) {
        epics.clear();
        subtasks.clear();
    }

    public void clearAllTask(Task task) {
        tasks.clear();
    }

    public void clearAllSubtask(Subtask subtask) {
        subtasks.clear();
        for (int i = 0; i < epics.size(); i++) {
            setEpicStatus(epics.get(i));
        }
    }

    public Epic getByIdEpic(Epic epic, int id) {
        historyManager.add(epics.get(id));
        return epics.get(id);
    }

    public Subtask getByIdSubtask(Subtask subtask, int id) {
        historyManager.add(subtasks.get(id));
        return subtasks.get(id);
    }

    @Override
    public Task getByIdTask (Task task, int id) {
        historyManager.add(tasks.get(id));
        return tasks.get(id);
    }

    public void createTask(Epic epic) {
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

    public void createTask(Subtask subtask) {
        subtask.setId(setId);
        setId++;
        subtasks.put(subtask.getId(), subtask);
        epics.get(subtask.getEpicId()).addIdSubtask(subtask.getId());
    }

    public void updateTask(Epic epic) {
        epics.get(epic.getId()).setTitle(epic.getTitle());
        epics.get(epic.getId()).setDescription(epic.getDescription());
    }

    public void updateTask(Subtask subtask) {
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
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    public void deleteByIdEpic(Epic epic, int id) {
        for (int i = 0; i < epics.get(id).getIdSubtasks().size(); i++) {
            subtasks.remove(epics.get(id).getIdSubtasks().get(i));
        }
        epics.remove(id);
    }

    public void deleteByIdSubtask(Subtask subtask, int id) {
        int idEpic = subtasks.get(id).getEpicId();
        epics.get(subtasks.get(id).getEpicId()).removeIdSubtask(id);
        subtasks.remove(id);
        setEpicStatus(epics.get(idEpic));
    }

    @Override
    public void deleteByIdTask(Task task, int id) {
        tasks.remove(id);
    }

    public List<Subtask> getSubtaskByEpic(Epic epic) {
        ArrayList<Subtask> subtasksByEpic = new ArrayList<>();
        for (int i = 0; i < epic.getIdSubtasks().size(); i++) {
            subtasksByEpic.add(subtasks.get(epic.getIdSubtasks().get(i)));
        }
        return subtasksByEpic;
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