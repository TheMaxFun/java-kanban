package finalTask.service;
import finalTask.model.Epic;
import finalTask.model.Subtask;
import finalTask.model.Task;
import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    private int setId = 1;
    private HashMap<Integer,Epic> epics = new HashMap<>();
    private HashMap<Integer,Task> tasks = new HashMap<>();
    private HashMap<Integer,Subtask> subtasks = new HashMap<>();

    public HashMap<Integer, Epic> getAllEpic() {
        return epics;
    }

    public HashMap<Integer, Subtask> getAllSubtasks() {
        return subtasks;
    }

    public HashMap<Integer, Task> getAllTask() {
        return tasks;
    }

    public void clearAllEpic() {
        epics.clear();
        subtasks.clear();
    }

    public void clearAllTask() {
        tasks.clear();
    }

    public void clearAllSubtask() {
        subtasks.clear();
        for (int i = 0; i < epics.size(); i++) {
            setEpicStatus(epics.get(i));
        }
    }

    public Epic getByIdEpic(int id) {
        return epics.get(id);
    }

    public Subtask getByIdSubtask(int id) {
        return subtasks.get(id);
    }

    public Task getByIdTask (int id) {
        return tasks.get(id);
    }

    public void createEpicTask(Epic epic) {
        epic.setId(setId);
        setId++;
        epics.put(epic.getId(), epic);
    }

    public void createTask(Task task) {
        task.setId(setId);
        setId++;
        tasks.put(task.getId(), task);
    }

    public void createSubtask(Subtask subtask) {
        subtask.setId(setId);
        setId++;
        subtasks.put(subtask.getId(), subtask);
        epics.get(subtask.getEpicId()).addIdSubtask(subtask.getId());
    }

    public void updateEpicTask(Epic epic) {
        epics.get(epic.getId()).setTitle(epic.getTitle());
        epics.get(epic.getId()).setDescription(epic.getDescription());
    }

    public void updateSubtask(Subtask subtask) {
        subtasks.get(subtask.getId()).setTitle(subtask.getTitle());
        subtasks.get(subtask.getId()).setDescription(subtask.getDescription());
        subtasks.get(subtask.getId()).setStatus(subtask.getStatus());
        setEpicStatus(epics.get(subtask.getEpicId()));
    }

    public void updateTask(Task task) {
        tasks.get(task.getId()).setTitle(task.getTitle());
        tasks.get(task.getId()).setDescription(task.getDescription());
        tasks.get(task.getId()).setStatus(task.getStatus());
    }

    public void deleteByIdEpic(int id) {
        for (int i = 0; i < epics.get(id).getIdSubtasks().size(); i++) {
            subtasks.remove(epics.get(id).getIdSubtasks().get(i));
        }
        epics.remove(id);
    }

    public void deleteByIdSubtask(int id) {
        int idEpic = subtasks.get(id).getEpicId();
        epics.get(subtasks.get(id).getEpicId()).removeIdSubtask(id);
        subtasks.remove(id);
        setEpicStatus(epics.get(idEpic));
    }

    public void deleteByIdTask(int id) {
        tasks.remove(id);
    }

    public ArrayList<Subtask> getSubtaskByEpic(Epic epic) {
        ArrayList<Subtask> subtasksByEpic = new ArrayList<>();
        for (int i = 0; i < epic.getIdSubtasks().size(); i++) {
            subtasksByEpic.add(subtasks.get(epic.getIdSubtasks().get(i)));
        }
        return subtasksByEpic;
    }

    private void setEpicStatus(Epic epic) {
        int doneSubtask = 0;
        int inProgressSubtask = 0;
        ArrayList<Subtask> subtasksByEpic = new ArrayList<>();
        subtasksByEpic = getSubtaskByEpic(epic);
        for (int i = 0; i < subtasksByEpic.size(); i++) {
            if (subtasksByEpic.get(i).getStatus() == "IN_PROGRESS") {
                inProgressSubtask += 1;
            } else if (subtasksByEpic.get(i).getStatus() == "DONE") {
                doneSubtask += 1;
            }
        }
        if (doneSubtask == subtasksByEpic.size()) {
            epic.setStatus("DONE");
        } else if (inProgressSubtask > 0) {
            epic.setStatus("IN_PROGRESS");
        } else if (doneSubtask > 0 && doneSubtask != subtasksByEpic.size()) {
            epic.setStatus("IN_PROGRESS");
        } else if (doneSubtask == 0 && inProgressSubtask == 0) {
            epic.setStatus("NEW");
        }
    }
}