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

    public FileBackedTasksManager() {
        //конструктор для теста
    }

    public FileBackedTasksManager(Map<Integer, Task> tasks, Map<Integer, Subtask> subtasks, Map<Integer, Epic> epics) {
        super();
    }

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

        manager.deleteByIdTask(1);

        manager.deleteByIdEpic(3);

        manager = manager.loadFromFile(new File(("./resources/condition.txt")));
    }

    private HashMap<Integer,Epic> epics = new HashMap<>();
    private HashMap<Integer,Task> tasks = new HashMap<>();
    private HashMap<Integer,Subtask> subtasks = new HashMap<>();
    private HistoryManager historyManager = getDefaultHistory();

    public void save() throws ManagerSaveException {
        List<Integer> idsList = new ArrayList<>();
        String form = "id,type,name,status,description,epic\n";
        try (PrintWriter writer = new PrintWriter(new File("./resources/condition.txt"))) {
            writer.write(form);
            for (Task task : tasks.values()) {
                writer.write(saveTask(task));
                writer.write("\n");
            }
            for (Subtask subtask : subtasks.values()) {
                writer.write(saveSubtask(subtask));
                writer.write("\n");
            }
            for (Epic epic : epics.values()) {
                writer.write(saveEpic(epic));
                writer.write("\n");
            }
            writer.write("\n");
            for (Task id : historyManager.getHistory()) {
                idsList.add(id.getId());
            }
            writer.write(idsList.toString());
            writer.close();
        } catch (FileNotFoundException e) {
            throw new ManagerSaveException("Невозможно создать файл для сохранения. Возможно, директория недоступна или файл уже используется.");
        }
    }

    private String saveTask (Task task) throws ManagerSaveException {
        try {
            String id = "" + task.getId();
            String status = "" + task.getStatus();
            String type = "" + TASK;
            return String.join(",", id, type, task.getTitle(), status, task.getDescription());
        } catch (ExceptionInInitializerError exception) {
            throw new ManagerSaveException("Невозможно прочитать файл с задачами. Возможно файл не находится в нужной директории.");
        }
    }

    private String saveSubtask(Subtask subtask) throws ManagerSaveException {
        try {
            String id = "" + subtask.getId();
            String status = "" + subtask.getStatus();
            String type = "" + SUBTASK;
            String epicId = "" + subtask.getEpicId();
            return String.join(",", id, type, subtask.getTitle(), status, subtask.getDescription(), epicId);
        } catch (ExceptionInInitializerError exception) {
            throw new ManagerSaveException("Невозможно прочитать файл с задачами. Возможно файл не находится в нужной директории.");
        }
    }


    private String saveEpic(Epic epic) throws ManagerSaveException {
        try {
        String id = "" + epic.getId();
        String status = "" + epic.getStatus();
        String type = "" + EPIC;
        String str = epic.getIdSubtasks().toString();
        str = str.replaceAll(" ", ",");
        if (str == "[]") {
            return String.join(",", id, type, epic.getTitle(), status, epic.getDescription());
        } else {
            return String.join(",", id, type, epic.getTitle(), status, epic.getDescription(), str);
        }
        } catch (ExceptionInInitializerError exception) {
            throw new ManagerSaveException("Невозможно прочитать файл с задачами. Возможно файл не находится в нужной директории.");
        }
    }

    private Task fromString(String value) throws ManagerSaveException {
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
                if (split.length > 5) {
                    for (int i = 5; i < split.length; i++) {
                        epic.addIdSubtask(toInt(split[i]));
                    }
                }
                return epic;
            }
        } catch (GetStatusException e) {
            throw new RuntimeException(e);
        } catch (GetTypeException e) {
            throw new RuntimeException(e);
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
                throw new GetStatusException();
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
            throw new GetTypeException();
        }
    }

    FileBackedTasksManager loadFromFile(File file) throws IOException, ManagerSaveException {
        FileReader reader = new FileReader(file.getPath());
        BufferedReader br = new BufferedReader(reader);
        Map<Integer, Task> tasks = new HashMap<>();
        Map<Integer, Subtask> subtasks = new HashMap<>();
        Map<Integer, Epic> epics = new HashMap<>();
        List<String> lines = new ArrayList<>();
            while (br.ready()) {
                String line = br.readLine();
                lines.add(line);
            }
        for (int i = 1; i < lines.size() - 2; ++i) {
            if (!lines.get(i).isEmpty()) {
                System.out.println(lines.get(i));
                Task task;
                try {
                    task = fromString(lines.get(i));
                } catch (ManagerSaveException e) {
                    throw new ManagerSaveException("произошла ошибка при создании тасков из файла");
                }
                if (task instanceof Subtask) {
                    subtasks.put(task.getId(), (Subtask) task);
                } else if (task instanceof Epic) {
                    epics.put(task.getId(), (Epic) task);
                } else {
                    tasks.put(task.getId(), task);
                }
            }
        }
        System.out.println(tasks);
        br.close();
        return new FileBackedTasksManager(tasks, subtasks, epics);
    }

    @Override
    public void createSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        try {
            save();
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createTask(Task task) {
        tasks.put(task.getId(), task);
        try {
            save();
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createEpicTask(Epic epic) {
        epics.put(epic.getId(), epic);
        try {
            save();
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clearAllEpic() {
        for (int i = 0; i < epics.size() - 1; i++) {
            historyManager.remove(epics.get(i).getId());
        }
        for (int i = 0; i < subtasks.size() - 1; i++) {
            historyManager.remove(subtasks.get(i).getId());
        }
        epics.clear();
        subtasks.clear();
        try {
            save();
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clearAllTask() {
        for (int i = 0; i < subtasks.size() - 1; i++) {
            historyManager.remove(tasks.get(i).getId());
        }
        tasks.clear();
        try {
            save();
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clearAllSubtask() {
        for (int i = 0; i < subtasks.size() - 1; i++) {
            historyManager.remove(subtasks.get(i).getId());
        }
        subtasks.clear();
        for (int i = 0; i < epics.size(); i++) {
            setEpicStatus(epics.get(i));
        }
        try {
            save();
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Epic getByIdEpic(int id) {
        historyManager.add(epics.get(id));
        try {
            save();
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
        return epics.get(id);
    }

    @Override
    public Subtask getByIdSubtask(int id) {
        historyManager.add(subtasks.get(id));
        try {
            save();
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
        return subtasks.get(id);
    }

    @Override
    public Task getByIdTask (int id) {
        historyManager.add(tasks.get(id));
        try {
            save();
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
        return tasks.get(id);
    }

    @Override
    public void deleteByIdEpic(int id) {
        for (int i = 0; i < epics.get(id).getIdSubtasks().size(); i++) {
            historyManager.remove(epics.get(id).getIdSubtasks().get(i));
            subtasks.remove(epics.get(id).getIdSubtasks().get(i));
        }
        epics.remove(id);
        historyManager.remove(id);
        try {
            save();
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteByIdSubtask(int id) {
        int idEpic = subtasks.get(id).getEpicId();
        epics.get(subtasks.get(id).getEpicId()).removeIdSubtask(id);
        subtasks.remove(id);
        setEpicStatus(epics.get(idEpic));
        historyManager.remove(id);
        try {
            save();
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteByIdTask(int id) {
        historyManager.remove(id);
        tasks.remove(id);
        try {
            save();
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
    }
}

