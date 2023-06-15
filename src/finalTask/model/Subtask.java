package finalTask.model;


import finalTask.service.ManagerSaveException;

import static finalTask.model.Type.SUBTASK;

public class Subtask extends Task {
    private int epicId;
    public Subtask(String title, String description, Status status, int id, int epicId) {
        super(title, description, status, id);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

     public String getDescriptionSubtask(Subtask subtask) throws ManagerSaveException {
        try {
            String id = "" + subtask.getId();
            String status = "" + subtask.getStatus();
            String type = "" + SUBTASK;
            String epicId = "" + subtask.getEpicId();
            return String.join(",", id, type, subtask.getTitle(), status, subtask.getDescription(), epicId);
        } catch (Exception exception) {
            throw new ManagerSaveException();
        }
     }
}
