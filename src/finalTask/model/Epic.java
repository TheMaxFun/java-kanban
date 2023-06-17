package finalTask.model;

import java.util.ArrayList;

public class Epic extends Task {
    protected ArrayList <Integer> idSubtasks = new ArrayList<>();

    public Epic(String title, String description, Status status, int id) {
        super(title, description, status, id);
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

    @Override
    public String getDescriptionTask (Type type) {
        String str = super.getDescriptionTask(type);
        return String.join(",", str);
    }
}
