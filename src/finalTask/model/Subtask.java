package finalTask.model;

public class Subtask extends Task {
    private Type type = Type.SUBTASK;
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
    public String getDescriptionTask() {
        String str = super.getDescriptionTask();
        String epicId = "" + getEpicId();
        return String.join(",", str, epicId);
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public void setType(Type type) {
        this.type = type;
    }
}
