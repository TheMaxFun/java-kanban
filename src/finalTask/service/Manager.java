package finalTask.service;
import finalTask.model.Epic;
import finalTask.model.Subtask;
import finalTask.model.Task;
import java.util.ArrayList;
import java.util.Scanner;

public class Manager {
    int setId = 1;
    ArrayList<Epic> epicTasks = new ArrayList<>();
    ArrayList<Task> usualTask = new ArrayList<>();
    ArrayList<Subtask> subtasks = new ArrayList<>();

    public void getAllEpic() {
        for (int i = 0; i < epicTasks.size(); i++) {
            System.out.println(" Название " + epicTasks.get(i).getTitle()
                    + " Описание " + epicTasks.get(i).getDescription()
                    + " Статус " + epicTasks.get(i).getStatus()
                    + " Id " + epicTasks.get(i).getId());
        }
    }

    public void getAllSubtasks() {
        for (int i = 0; i < subtasks.size(); i++) {
            System.out.println(" Название " + subtasks.get(i).getTitle()
                    + " Описание " + subtasks.get(i).getDescription()
                    + " Статус " + subtasks.get(i).getStatus()
                    + " Id " + subtasks.get(i).getId());
        }
    }

    public void getAllTask() {
        for (int i = 0; i < usualTask.size(); i++) {
            System.out.println(" Название " + usualTask.get(i).getTitle()
                    + " Описание " + usualTask.get(i).getDescription()
                    + " Статус " + usualTask.get(i).getStatus()
                    + " Id " + usualTask.get(i).getId());
        }
    }

    public void clearAllEpic() {
        epicTasks.clear();
        subtasks.clear();
    }

    public void clearAllTask() {
        usualTask.clear();
    }

    public void clearAllSubtask() {
        subtasks.clear();
        for (int i = 0; i < epicTasks.size(); i++) {
            epicTasks.get(i).setStatus("NEW");
        }
    }

    public void getById(int id) {
        for (int i = 0; i < epicTasks.size(); i++) {
            if (epicTasks.get(i).getId() == id) {
                System.out.println("Эпик, Название " + epicTasks.get(i).getTitle()
                        + " Описание " + epicTasks.get(i).getDescription()
                        + " Статус " + epicTasks.get(i).getStatus()
                        + " Id " + epicTasks.get(i).getId());
            }
        }
        for (int i = 0; i < subtasks.size(); i++) {
            if (subtasks.get(i).getId() == id) {
                System.out.println("Подзадача, Название " + subtasks.get(i).getTitle()
                        + " Описание " + subtasks.get(i).getDescription()
                        + " Статус " + subtasks.get(i).getStatus()
                        + " Id " + subtasks.get(i).getId());
            }
        }
        for (int i = 0; i < usualTask.size(); i++) {
            if (usualTask.get(i).getId() == id) {
                System.out.println("Задача, Название " + usualTask.get(i).getTitle()
                        + " Описание " + usualTask.get(i).getDescription()
                        + " Статус " + usualTask.get(i).getStatus()
                        + " Id " + usualTask.get(i).getId());
            }
        }
    }

    public void createEpicTask() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите название эпика");
        String title = scanner.next();
        System.out.println("Введите описание");
        String description = scanner.next();
        int id = setId;
        String status = "NEW";
        setId++;
        Epic epic = new Epic(title, description, status, id);
        epicTasks.add(epic);
    }

    public void createTask() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите название задачи");
        String title = scanner.next();
        System.out.println("Введите описание");
        String description = scanner.next();
        int id = setId;
        String status = "NEW";
        setId++;
        Task task = new Task(title, description, status, id);
        usualTask.add(task);
    }

    public void createSubtask() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите название подзадачи");
        String title = scanner.next();
        System.out.println("Введите описание");
        String description = scanner.next();
        System.out.println("Введите id эпика");
        int epicId = scanner.nextInt();
        int id = setId;
        String status = "NEW";
        setId++;
        Subtask subtask = new Subtask(title, description, status, id, epicId);
        subtasks.add(subtask);
        for (int i = 0; i < epicTasks.size(); i++) {
            if (epicTasks.get(i).getId() == epicId) {
                epicTasks.get(i).addIdSubtask(id);
            }
        }
    }

    public void updateEpicTask(int id) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите название эпика");
        String title = scanner.next();
        System.out.println("Введите описание");
        String description = scanner.next();
        for (int i = 0; i < epicTasks.size(); i++) {
            if (epicTasks.get(i).getId() == id) {
                if (title != null) {
                    epicTasks.get(i).setTitle(title);
                }
                if (description != null) {
                    epicTasks.get(i).setDescription(description);
                }
            }
        }
    }

    public void updateSubtask(int id) {
        Scanner scanner = new Scanner(System.in);
        int numberOfStatus;
        System.out.println("Введите название подзадачи");
        String title = scanner.next();
        System.out.println("Введите описание");
        String description = scanner.next();
        System.out.println("Введите номер статуса, 1 - NEW, 2 - IN_PROGRESS, 3 - DONE");
        numberOfStatus = scanner.nextInt();
        String status = setStatus(numberOfStatus);
        for (int i = 0; i < subtasks.size(); i++) {
            if (subtasks.get(i).getId() == id) {
                if (title != null) {
                    subtasks.get(i).setTitle(title);
                }
                if (description != null) {
                    subtasks.get(i).setDescription(description);
                }
                if (status != null) {
                    subtasks.get(i).setStatus(status);
                    setEpicStatus(subtasks.get(i).getEpicId(), subtasks.get(i).getId());
                }
            }
        }
    }

    public void updateTask(int id) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите название задачи");
        String title = scanner.next();
        System.out.println("Введите описание");
        String description = scanner.next();
        System.out.println("Введите номер статуса, 1 - NEW, 2 - IN_PROGRESS, 3 - DONE");
        int numberOfStatus = scanner.nextInt();
        String status = setStatus(numberOfStatus);
        for (int i = 0; i < usualTask.size(); i++) {
            if (usualTask.get(i).getId() == id) {
                if (title != null) {
                    usualTask.get(i).setTitle(title);
                }
                if (description != null) {
                    usualTask.get(i).setDescription(description);
                }
            }
            if (status != null) {
                usualTask.get(i).setStatus(status);
                }
            }
        }

    public void deleteById(int id) {
        for (int i = 0; i < epicTasks.size(); i++) {
            if (epicTasks.get(i).getId() == id) {
                for (int j = 0; j < subtasks.size(); j++) {
                    ArrayList<Integer> ids = epicTasks.get(i).getIdSubtasks();
                    for (int k = 0; k < ids.size(); k++) {
                        for (int z = 0; z < subtasks.size(); z++) {
                            if (ids.get(k) == subtasks.get(z).getId()) {
                                subtasks.remove(z);
                            }
                        }
                    }
                }
                epicTasks.remove(i);
            }
        }
        for (int j = 0; j < subtasks.size(); j++) {
            if (subtasks.get(j).getId() == id) {
                subtasks.remove(j);
            }
        }
        for (int i = 0; i < usualTask.size(); i++) {
            if (usualTask.get(i).getId() == id) {
                usualTask.remove(i);
            }
        }
    }

    public void getSubtaskByEpic(int id) {
        for (int i = 0; i < epicTasks.size(); i++) {
            if (epicTasks.get(i).getId() == id) {
                ArrayList<Integer> ids = epicTasks.get(i).getIdSubtasks();
                for (int j = 0; j < ids.size(); j++) {
                    for (int k = 0; k < subtasks.size(); k++) {
                        if (ids.get(j) == subtasks.get(k).getId()) {
                            System.out.println("Задача, Название " + subtasks.get(k).getTitle()
                                    + " Описание " + subtasks.get(k).getDescription()
                                    + " Статус " + subtasks.get(k).getStatus()
                                    + " Id " + subtasks.get(k).getId());
                        }
                    }
                }
            }
        }
    }

    public void setEpicStatus(int epicId, int subtaskId) {
        int inProgressSubtask = 0;
        int doneSubtask = 0;
        ArrayList<Subtask> supportList = new ArrayList<>();
        for (int i = 0; i < epicTasks.size(); i++) {
            for (int j = 0; j < epicTasks.get(i).getIdSubtasks().size(); j++) {
                if (epicTasks.get(i).getIdSubtasks().get(j) == subtaskId) {
                    epicId = epicTasks.get(i).getId();
                    for (int k = 0; k < subtasks.size(); k++) {
                        if (subtasks.get(k).getId() == epicTasks.get(i).getIdSubtasks().get(j)) {
                            supportList.add(subtasks.get(k));
                        }
                    }
                }
            }
        }
        for (int i = 0; i < supportList.size(); i++) {
            if (supportList.get(i).getStatus() == "DONE") {
                doneSubtask += 1;
            } else if (supportList.get(i).getStatus() == "IN_PROGRESS") {
                inProgressSubtask += 1;
            }
        }
        if (inProgressSubtask > 0) {
            for (int i = 0; i < epicTasks.size(); i++) {
                if (epicTasks.get(i).getId() == epicId) {
                    epicTasks.get(i).setStatus("IN_PROGRESS");
                }
            }
        } else if (doneSubtask == supportList.size()) {
            for (int i = 0; i < epicTasks.size(); i++) {
                if (epicTasks.get(i).getId() == epicId) {
                    epicTasks.get(i).setStatus("DONE");
                }
            }
        } else if (inProgressSubtask > 0 && doneSubtask > 0) {
            for (int i = 0; i < epicTasks.size(); i++) {
                if (epicTasks.get(i).getId() == epicId) {
                    epicTasks.get(i).setStatus("IN_PROGRESS");
                }
            }
        } else if (doneSubtask == 0 && inProgressSubtask == 0) {
            for (int i = 0; i < epicTasks.size(); i++) {
                if (epicTasks.get(i).getId() == epicId) {
                    epicTasks.get(i).setStatus("NEW");
                }
            }
        }
    }

    protected String setStatus (int number) {
        String status = null;
        if (number == 1) {
            status = "NEW";
        } else if (number == 2) {
            status = "IN_PROGRESS";
        } else if (number == 3) {
            status = "DONE";
        } else {
            System.out.println("Ошибка");
        }
        return status;
    }
}