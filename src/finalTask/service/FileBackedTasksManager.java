package finalTask.service;

import finalTask.model.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static finalTask.model.Status.*;
import static finalTask.model.Status.NEW;
import static finalTask.model.Type.*;
import static finalTask.service.Managers.getDefaultHistory;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager {

    public static void main(String[] args) throws IOException, ManagerSaveException {

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

        manager.getByIdTask(q1.getId());
        manager.getByIdTask(q2.getId());
        manager.getByIdSubtask(x1.getId());

        manager.getByIdEpic(z1.getId());
        manager.getByIdEpic(z2.getId());
        manager.getByIdSubtask(x1.getId());

        manager.getByIdSubtask(x1.getId());
        manager.getByIdSubtask(x1.getId());
        manager.getByIdSubtask(x1.getId());
        manager.getByIdSubtask(x2.getId());
        manager.getByIdSubtask(x3.getId());
        manager.getByIdSubtask(x1.getId());

    }

    private HistoryManager historyManager = getDefaultHistory();

    public void save() throws ManagerSaveException {
        List<Integer> idsList = new ArrayList<>();
        String form = "id,type,name,status,description,epic\n";
        try (PrintWriter writer = new PrintWriter(new File("./resources/condition.txt"))) {
            writer.write(form);
            for (Task task : super.getAllTask()) {
                writer.write(task.getDescriptionTask(TASK));
                writer.write("\n");
            }
            for (Subtask subtask : super.getAllSubtasks()) {
                writer.write(subtask.getDescriptionTask(SUBTASK));
            }
            for (Epic epic : super.getAllEpic()) {
                writer.write(epic.getDescriptionTask(EPIC));
            }
            for (Task id : super.getHistory()) {
                idsList.add(id.getId());
            }
            writer.write("\n");
            writer.write(idsList.toString());
        } catch (FileNotFoundException e) {
            throw new ManagerSaveException("Ошибка при сохранении");
        }
    }

    private Task fromString(String value) throws ManagerSaveException, GetStatusException, GetTypeException {
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

    public int toInt(String str) {
        int integ = Integer.parseInt(str);
        return integ;
    }

    public Status getStatus(String str) throws GetStatusException {
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

    public Type getType(String str) throws GetTypeException {
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

    FileBackedTasksManager loadFromFile(File file) throws IOException, ManagerSaveException {
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
                    createSubtask((Subtask) task);
                } else if (task instanceof Epic) {
                    createEpicTask((Epic) task);
                } else {
                    createTask(task);
                }
            }
        }
        String[] split = lines.get(lines.size() - 1).split(", ");
        for (String line : split) {
            if (super.getByIdTask(toInt(line)) != null) {
                super.getByIdTask(toInt(line));
            } else if (super.getByIdSubtask(toInt(line)) != null) {
                super.getByIdSubtask(toInt(line));
            } else if (super.getByIdEpic(toInt(line)) != null) {
                super.getByIdEpic(toInt(line));
            }
        }
        for (Subtask subtask : super.getAllSubtasks()) {
            super.getAllEpic().get(subtask.getEpicId()).addIdSubtask(subtask.getId());
        }
        br.close();
        return new FileBackedTasksManager();
    }

    @Override
    public void createSubtask(Subtask subtask) {
        super.subtasks.put(subtask.getId(), subtask);
        super.epics.get(subtask.getEpicId()).addIdSubtask(subtask.getId());
        save();
    }

    @Override
    public void createTask(Task task) {
        super.tasks.put(task.getId(), task);
        save();
    }

    @Override
    public void createEpicTask(Epic epic) {
        super.epics.put(epic.getId(), epic);
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
        super.getByIdEpic(id);
        save();
        return super.getByIdEpic(id);
    }

    @Override
    public Subtask getByIdSubtask(int id) {
        super.getByIdSubtask(id);
        save();
        return super.getByIdSubtask(id);
    }

    @Override
    public Task getByIdTask(int id) {
        super.getByIdTask(id);
        save();
        return super.getByIdTask(id);
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
