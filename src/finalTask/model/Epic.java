package finalTask.model;

import java.util.ArrayList;

public class Epic extends Task {
    private Type type = Type.EPIC;
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
    public Type getType() {
        return type;
    }

    @Override
    public void setType(Type type) {
        this.type = type;
    }
}
