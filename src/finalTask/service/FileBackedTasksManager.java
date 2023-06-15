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
                writer.write(task.getDescriptionTask(task));
                writer.write("\n");
            }
            for (Subtask subtask : super.getAllSubtasks()) {
                writer.write(subtask.getDescriptionSubtask(subtask));
                writer.write("\n");
            }
            for (Epic epic : super.getAllEpic()) {
                writer.write(epic.getDescriptionEpic(epic));
                writer.write("\n");
            }
            writer.write("\n");
            for (Task id : super.getHistory()) {
                idsList.add(id.getId());
            }
            writer.write(idsList.toString());
        } catch (FileNotFoundException e) {
            throw new ManagerSaveException();
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
    public int toInt (String str) {
        int integ = Integer.parseInt(str);
        return integ;
    }

    public Status getStatus (String str) throws GetStatusException {
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

    public Type getType (String str) throws GetTypeException {
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
                    throw new ManagerSaveException();
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
        br.close();
        return new FileBackedTasksManager();
    }

    @Override
    public void createSubtask(Subtask subtask) {
        try {
            super.createSubtask(subtask);
            save();
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createTask(Task task) {
        try {
            super.createTask(task);
            save();
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createEpicTask(Epic epic) {
        try {
            super.createEpicTask(epic);
            save();
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clearAllEpic() {
        try {
            super.clearAllEpic();
            save();
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clearAllTask() {
        try {
            super.clearAllTask();
            save();
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clearAllSubtask() {
        try {
            super.clearAllSubtask();
            save();
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Epic getByIdEpic(int id) {
        super.getByIdEpic(id);
            try {
                save();
            } catch (ManagerSaveException e) {
                throw new RuntimeException(e);
            }
            return super.getByIdEpic(id);
        }

    @Override
    public Subtask getByIdSubtask(int id) {
        super.getByIdSubtask(id);
            try {
                save();
            } catch (ManagerSaveException e) {
                throw new RuntimeException(e);
            }
            return super.getByIdSubtask(id);
        }

    @Override
    public Task getByIdTask (int id) {
        super.getByIdTask(id);
            try {
                save();
            } catch (ManagerSaveException e) {
                throw new RuntimeException(e);
            }
            return super.getByIdTask(id);
        }

    @Override
    public void deleteByIdEpic(int id) {
        try {
            super.deleteByIdEpic(id);
            save();
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteByIdSubtask(int id) {
        try {
            super.deleteByIdSubtask(id);
            save();
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteByIdTask(int id) {
        try {
            super.deleteByIdTask(id);
            save();
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
    }
}

