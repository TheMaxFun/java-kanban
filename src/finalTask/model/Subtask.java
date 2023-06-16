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

    @Override
    public String getDescriptionTask(Type type) {
        String str = super.getDescriptionTask(type);
        String epicId = "" + getEpicId();
        return String.join(",", str, epicId, "\n");
    }
}
