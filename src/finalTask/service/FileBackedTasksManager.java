package finalTask.service;

import finalTask.model.*;

import java.io.*;
import java.util.*;

import java.nio.file.Files;
import java.nio.file.Path;

import static finalTask.model.Status.*;
import static finalTask.model.Status.NEW;
import static finalTask.model.Type.*;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager {

    public static void main(String[] args) throws ManagerSaveException {

        Task q1 = new Task("q", "q", NEW, 1);
        Task q2 = new Task("w", "w", DONE, 2);

        Epic z1 = new Epic("sa", "vz", IN_PROGRESS, 3);
        Epic z2 = new Epic("sq", "vf", NEW, 7);

        Subtask x1 = new Subtask("zvs", "wiqw", IN_PROGRESS, 4, 3);
        Subtask x2 = new Subtask("qwe", "sasaf", NEW, 5, 3);
        Subtask x3 = new Subtask("wer", "saf", NEW, 6, 3);

        FileBackedTasksManager manager = new FileBackedTasksManager();

        manager.createTask(q1);
        manager.createTask(q2);

        manager.createEpicTask(z1);
        manager.createEpicTask(z2);

        manager.createSubtask(x1);
        manager.createSubtask(x2);
        manager.createSubtask(x3);

        manager.getByIdTask(q2.getId());
        manager.getByIdTask(q1.getId());
        manager.getByIdSubtask(x1.getId());

        manager.getByIdEpic(z2.getId());
        manager.getByIdEpic(z1.getId());
        manager.getByIdSubtask(x1.getId());

        manager.getByIdSubtask(x3.getId());
        manager.getByIdSubtask(x1.getId());
        manager.getByIdSubtask(x2.getId());
        manager.getByIdSubtask(x2.getId());
        manager.getByIdSubtask(x3.getId());
        manager.getByIdSubtask(x2.getId());

    }

    public void save() {
        String str = "";
        String form = "id,type,name,status,description,epic\n";
        try (PrintWriter writer = new PrintWriter(new File("./resources/condition.txt"))) {
            writer.write(form);
            for (Task task : super.getAllTask()) {
                writer.write(task.getDescriptionTask());
                writer.write("\n");
            }
            for (Subtask subtask : super.getAllSubtasks()) {
                writer.write(subtask.getDescriptionTask());
                writer.write("\n");
            }
            for (Epic epic : super.getAllEpic()) {
                writer.write(epic.getDescriptionTask());
                writer.write("\n");
            }
            for (Task id : super.getHistory()) {
                str =  String.join(",", str, "" + id.getId());
                System.out.println(str);
            }
            if (str.length() > 2) {
                str = str.substring(1);
            }
            writer.write("\n");
            writer.write(str);
        } catch (FileNotFoundException e) {
            throw new ManagerSaveException("Ошибка при сохранении");
        }
    }

    private static Task fromString(String value) throws ManagerSaveException, GetStatusException, GetTypeException {
        try {
            String[] split = value.split(",");
            Status status = getStatus(split[3]);
            Type type = getType(split[1]);
            if (type.equals(TASK)) {
                Task task = new Task(split[2], split[4], status, toInt(split[0]));
                return task;
            } else if (type.equals(SUBTASK)) {
                Subtask subtask = new Subtask(split[2], split[4], status, toInt(split[0]), toInt(split[5]));
                return subtask;
            } else if (type.equals(EPIC)) {
                Epic epic = new Epic(split[2], split[4], status, toInt(split[0]));
                return epic;
            }
        } catch (GetStatusException e) {
            throw new GetStatusException("Ошибка, неправильный статус");
        } catch (GetTypeException e) {
            throw new GetTypeException("Ошибка, некорректный тип");
        }
        return null;
    }

    public static int toInt(String str) {
        return Integer.parseInt(str);
    }

    public static Status getStatus(String str) throws GetStatusException {
        if (str.equals("NEW")) {
            return Status.NEW;
        } else if (str.equals("IN_PROGRESS")) {
            return Status.IN_PROGRESS;
        } else if (str.equals("DONE")) {
            return Status.DONE;
        } else {
            throw new GetStatusException("Ошибка, неправильный статус");
        }
    }

    public static Type getType(String str) throws GetTypeException {
        if (str.equals("TASK")) {
            return Type.TASK;
        } else if (str.equals("SUBTASK")) {
            return Type.SUBTASK;
        } else if (str.equals("EPIC")) {
            return Type.EPIC;
        } else {
            throw new GetTypeException("Ошибка, некорректный тип");
        }
    }

    public static FileBackedTasksManager loadFromFile(File file) throws IOException, ManagerSaveException {

        FileBackedTasksManager manager = new FileBackedTasksManager();
        HashMap<Integer,Epic> epics = new HashMap<>();
        HashMap<Integer,Task> tasks = new HashMap<>();
        HashMap<Integer,Subtask> subtasks = new HashMap<>();

        FileReader reader = new FileReader(file.getPath());
        BufferedReader br = new BufferedReader(reader);
        List<String> lines = new ArrayList<>();
        while (br.ready()) {
            String line = br.readLine();
            lines.add(line);
        }
        for (int i = 1; i < lines.size() - 2; ++i) {
            if (!lines.get(i).isEmpty()) {
                Task task;
                try {
                    task = fromString(lines.get(i));
                } catch (ManagerSaveException | GetStatusException | GetTypeException e) {
                    throw new ManagerSaveException("Ошибка при загрузке");
                }
                if (task instanceof Subtask) {
                    Subtask subtask = (Subtask) task;
                    manager.subtasks.put(subtask.getId(), subtask);
                } else if (task instanceof Epic) {
                    Epic epic = (Epic) task;
                    manager.epics.put(epic.getId(), epic);
                } else {
                    manager.tasks.put(task.getId(), task);
                }
            }
        }
        String[] split = lines.get(lines.size() - 1).split(",");
        for (String line : split) {
            int inter = Integer.parseInt(line);
            if (manager.getByIdTask(inter) != null) {
                manager.getByIdTask(inter);
            } else if (manager.getByIdSubtask(inter) != null) {
                manager.getByIdSubtask(inter);
            } else if (manager.getByIdEpic(inter) != null) {
                manager.getByIdEpic(inter);
            }
        }
        br.close();
        return manager;
    }

        @Override
    public void createSubtask(Subtask subtask) {
        super.createSubtask(subtask);
        save();
    }

    @Override
    public void createTask(Task task) {
        super.createTask(task);
        save();
    }

    @Override
    public void createEpicTask(Epic epic) {
        super.createEpicTask(epic);
        save();
    }

    @Override
    public void clearAllEpic() {
        super.clearAllEpic();
        save();
    }

    @Override
    public void clearAllTask() {
        super.clearAllTask();
        save();
    }

    @Override
    public void clearAllSubtask() {
        super.clearAllSubtask();
        save();
    }

    @Override
    public Epic getByIdEpic(int id) {
        Epic epic = super.getByIdEpic(id);
        save();
        return epic;
    }

    @Override
    public Subtask getByIdSubtask(int id) {
        Subtask subtask = super.getByIdSubtask(id);
        save();
        return subtask;
    }

    @Override
    public Task getByIdTask(int id) {
        Task task = super.getByIdTask(id);
        save();
        return task;
    }

    @Override
    public void deleteByIdEpic(int id) {
        super.deleteByIdEpic(id);
        save();
    }

    @Override
    public void deleteByIdSubtask(int id) {
        super.deleteByIdSubtask(id);
        save();
    }

    @Override
    public void deleteByIdTask(int id) {
        super.deleteByIdTask(id);
        save();
    }
}
