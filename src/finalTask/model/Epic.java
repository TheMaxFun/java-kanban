package finalTask.model;

import java.util.ArrayList;

public class Epic extends Task {
    protected ArrayList <Integer> idSubtasks = new ArrayList<>();

    public Epic(String title, String description, String status, int id) {
        super(title, description, status, id);
        this.idSubtasks = idSubtasks;
    }

    public ArrayList<Integer> getIdSubtasks() {
        return idSubtasks;
    }

    public void addIdSubtask(int id) {
        idSubtasks.add(id);
    }
}
