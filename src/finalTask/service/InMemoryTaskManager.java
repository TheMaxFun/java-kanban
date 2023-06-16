package finalTask.service;
import finalTask.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static finalTask.service.Managers.getDefaultHistory;

public class InMemoryTaskManager implements TaskManager {
    private HistoryManager historyManager = getDefaultHistory();
    private static int setId = 1;
    HashMap<Integer,Epic> epics = new HashMap<>();
    HashMap<Integer,Task> tasks = new HashMap<>();
    HashMap<Integer,Subtask> subtasks = new HashMap<>();

    @Override
    public List<Epic> getAllEpic() {
        List<Epic> supportList = new ArrayList<>();
        for (Epic epic : epics.values()) {
            supportList.add(epic);
        }
        return supportList;
    }

    @Override
    public List<Subtask> getAllSubtasks() {
        List<Subtask> supportList = new ArrayList<>();
        for (Subtask subtask : subtasks.values()) {
            supportList.add(subtask);
        }
        return supportList;
    }

    @Override
    public List<Task> getAllTask() {
        List<Task> supportList = new ArrayList<>();
        for (Task task : tasks.values()) {
            supportList.add(task);
        }
        return supportList;
    }

    @Override
    public void clearAllEpic() {
        for (int i = 0; i < epics.size() - 1; i++) {
            historyManager.remove(epics.get(i).getId());
        }
        for (int i = 0; i < subtasks.size() - 1; i++) {
            historyManager.remove(subtasks.get(i).getId());
        }
        epics.clear();
        subtasks.clear();
    }

    @Override
    public void clearAllTask() {
        for (int i = 0; i < subtasks.size() - 1; i++) {
            historyManager.remove(tasks.get(i).getId());
        }
        tasks.clear();
    }

    @Override
    public void clearAllSubtask() {
        for (int i = 0; i < subtasks.size() - 1; i++) {
            historyManager.remove(subtasks.get(i).getId());
        }
        for (int i = 0; i < epics.size(); i++) {
            epics.get(i).removeAllSubtask();
            setEpicStatus(epics.get(i));
        }
        subtasks.clear();
    }

    @Override
    public Epic getByIdEpic(int id) {
        historyManager.add(epics.get(id));
        return epics.get(id);
    }

    @Override
    public Subtask getByIdSubtask(int id) {
        historyManager.add(subtasks.get(id));
        return subtasks.get(id);
    }

    @Override
    public Task getByIdTask (int id) {
        historyManager.add(tasks.get(id));
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
        update(epics.get(epic.getId()));
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        update(subtasks.get(subtask.getId()));
        setEpicStatus(epics.get(subtask.getEpicId()));
    }

    @Override
    public void updateTask(Task task) {
        update(tasks.get(task.getId()));
        tasks.get(task.getId()).setStatus(task.getStatus());
    }

    public void update(Task task) {
        task.setTitle(task.getTitle());
        task.setDescription(task.getDescription());
    }

    @Override
    public void deleteByIdEpic(int id) {
        for (int i = 0; i < epics.get(id).getIdSubtasks().size(); i++) {
            historyManager.remove(epics.get(id).getIdSubtasks().get(i));
            subtasks.remove(epics.get(id).getIdSubtasks().get(i));
        }
        epics.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void deleteByIdSubtask(int id) {
        int idEpic = subtasks.get(id).getEpicId();
        epics.get(subtasks.get(id).getEpicId()).removeIdSubtask(id);
        subtasks.remove(id);
        setEpicStatus(epics.get(idEpic));
        historyManager.remove(id);
    }

    @Override
    public void deleteByIdTask(int id) {
        historyManager.remove(id);
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

    public void setEpicStatus(Epic epic) {
        int doneSubtask = 0;
        int inProgressSubtask = 0;
        int newSubtask = 0;
        List<Subtask> subtasksByEpic = getSubtaskByEpic(epic);
        for (int i = 0; i < subtasksByEpic.size(); i++) {
            if (subtasksByEpic.get(i).getStatus() == Status.IN_PROGRESS) {
                inProgressSubtask += 1;
            } else if (subtasksByEpic.get(i).getStatus() == Status.DONE) {
                doneSubtask += 1;
            } else if (subtasksByEpic.get(i).getStatus() == Status.NEW) {
                newSubtask += 1;
            }
        }
        if (subtasksByEpic.size() == 0) {
            epic.setStatus(Status.NEW);
        } else if (doneSubtask == subtasksByEpic.size() || doneSubtask != 0) {
            epic.setStatus(Status.DONE);
        } else if (inProgressSubtask > 0) {
            epic.setStatus(Status.IN_PROGRESS);
        } else if (newSubtask == subtasksByEpic.size()) {
            epic.setStatus(Status.NEW);
        }
    }

}