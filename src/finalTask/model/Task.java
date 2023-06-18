package finalTask.model;

public class Task {
    protected String title;
    protected String description;
    private Status status;
    protected int id;
    private Type type = Type.TASK;

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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }


    public String getDescriptionTask() {
            String id = "" + getId();
            String status = "" + getStatus();
            String typeToString = "" + getType();
            return String.join(",", id, typeToString, getTitle(), status, getDescription());
    }
}



