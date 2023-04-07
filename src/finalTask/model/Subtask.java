package finalTask.model;


public class Subtask extends Task {
    private int epicId;
    public Subtask(String title, String description, String status, int id, int epicId) {
        super(title, description, status, id);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }
}
