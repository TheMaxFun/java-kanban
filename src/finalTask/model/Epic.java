package finalTask.model;

import finalTask.service.ManagerSaveException;

import java.util.ArrayList;

import static finalTask.model.Type.EPIC;

public class Epic extends Task {
    protected ArrayList <Integer> idSubtasks = new ArrayList<>();

    public Epic(String title, String description, Status status, int id) {
        super(title, description, status, id);
        this.idSubtasks = idSubtasks;
    }

    public ArrayList<Integer> getIdSubtasks() {
        return idSubtasks;
    }

    public void addIdSubtask(int id) {
        idSubtasks.add(id);
    }

    public void removeIdSubtask(int id) { idSubtasks.remove(id); }

    public void removeAllSubtask() {
        idSubtasks = null;
    }

    public String getDescriptionEpic (Epic epic) throws ManagerSaveException {
        try {
            String id = "" + epic.getId();
            String status = "" + epic.getStatus();
            String type = "" + EPIC;
            return String.join(",", id, type, epic.getTitle(), status, epic.getDescription());
        } catch (Exception exception) {
            throw new ManagerSaveException();
        }
    }
}
