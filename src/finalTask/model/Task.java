package finalTask.model;

import finalTask.service.ManagerSaveException;

import static finalTask.model.Type.TASK;

public class Task {
    protected String title;
    protected String description;
    protected Status status;
    protected int id;

    public Task(String title, String description, Status status, int id) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescriptionTask(Type type) {
            String id = "" + getId();
            String status = "" + getStatus();
            String typeToString = "" + type;
            return String.join(",", id, typeToString, getTitle(), status, getDescription());
    }
}



