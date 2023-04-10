import finalTask.model.Epic;
import finalTask.model.Subtask;
import finalTask.model.Task;
import finalTask.service.Manager;

import java.util.HashMap;
import java.util.Scanner;

public class Main {

    Manager manager = new Manager();
    Scanner scanner = new Scanner(System.in);

    public void main(String[] args) {
        while (true) {
            printMenu();
            int command = scanner.nextInt();
            int subcommand;
            if (command == 1) {
                printList();
                subcommand = scanner.nextInt();
                if (subcommand == 1) {
                    readEpic(manager.getAllEpic());
                } else if (subcommand == 2) {
                    readSub(manager.getAllSubtasks());
                } else if (subcommand == 3) {
                    readTask(manager.getAllTask());
                } else {
                    System.out.println("Извините, такой команды пока нет.");
                }
            } else if (command == 2) {
                printDelete();
                subcommand = scanner.nextInt();
                if (subcommand == 1) {
                    manager.clearAllEpic();
                } else if (subcommand == 2) {
                    manager.clearAllSubtask();
                } else if (subcommand == 3) {
                    manager.clearAllTask();
                } else {
                    System.out.println("Извините, такой команды пока нет.");
                }
            } else if (command == 3) {
                fildById();
            } else if (command == 4) {
                printCreate();
                subcommand = scanner.nextInt();
                if (subcommand == 1) {
                    createEpic();
                } else if (subcommand == 2) {
                    createSubtask();
                } else if (subcommand == 3) {
                    createTask();
                } else {
                    System.out.println("Извините, такой команды пока нет.");
                }
            } else if (command == 5) {
                printUpdate();
                subcommand = scanner.nextInt();
                if (subcommand == 1) {
                    upDataEpic();
                } else if (subcommand == 2) {
                    upDataSub();
                } else if (subcommand == 3) {
                    upDataTask();
                } else {
                    System.out.println("Извините, такой команды пока нет.");
                }
            } else if (command == 6) {
                deleteById();
            } else if (command == 7) {
                getSubtaskByEpic();
            } else if (command == 0) {
                System.out.println("Выход");
                break;
            } else {
                System.out.println("Извините, такой команды пока нет.");
            }
        }
    }

    public static void printMenu() {
        System.out.println("Что вы хотите сделать?");
        System.out.println("1 - Получение списка задач");
        System.out.println("2 - удаление всех задач");
        System.out.println("3 - получение задачи по id");
        System.out.println("4 - создание задачи");
        System.out.println("5 - обновление задачи");
        System.out.println("6 - удаление задачи по id");
        System.out.println("7 - получение списка всех подзадач определённого эпика");
        System.out.println("0 - выход");
    }

    public static void printList() {
        System.out.println("Выберете какой тип задач вы ходите получить");
        System.out.println("1 - эпики");
        System.out.println("2 - подзадачи");
        System.out.println("3 - обычные задачи");
    }

    public static void printDelete() {
        System.out.println("1 - удалить эпики");
        System.out.println("2 - удалить подзадачи");
        System.out.println("3 - удалить обычные задачи");
    }

    public static void printCreate() {
        System.out.println("1 - создать эпик");
        System.out.println("2 - создать подзадачу");
        System.out.println("3 - создать обычную задачу");
    }

    public static void printUpdate() {
        System.out.println("1 - обновить эпик");
        System.out.println("2 - обновить подзадачу");
        System.out.println("3 - обновить обычную задачу");
    }

    public void readEpic(HashMap<Integer, Epic> epics) {
        for (int i = 0; i < epics.size(); i++) {
            System.out.println(" Название " + epics.get(i).getTitle()
                    + " Описание " + epics.get(i).getDescription()
                    + " Статус " + epics.get(i).getStatus()
                    + " Id " + epics.get(i).getId());
        }
    }

    public void readSub(HashMap<Integer, Subtask> subs) {
        for (int i = 0; i < subs.size(); i++) {
            System.out.println(" Название " + subs.get(i).getTitle()
                    + " Описание " + subs.get(i).getDescription()
                    + " Статус " + subs.get(i).getStatus()
                    + " Id " + subs.get(i).getId()
                    + " EpicId " + subs.get(i).getEpicId());
        }
    }

    public void readTask(HashMap<Integer, Task> tasks) {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(" Название " + tasks.get(i).getTitle()
                    + " Описание " + tasks.get(i).getDescription()
                    + " Статус " + tasks.get(i).getStatus()
                    + " Id " + tasks.get(i).getId());
        }
    }

    public void fildById() {
        System.out.println("Введите id элемента, который хотите получить");
        int id = scanner.nextInt();
        if (manager.getByIdEpic(id) != null) {
            System.out.println(" Название " + manager.getByIdEpic(id).getTitle()
                    + " Описание " + manager.getByIdEpic(id).getDescription()
                    + " Статус " + manager.getByIdEpic(id).getStatus()
                    + " Id " + manager.getByIdEpic(id).getId());
        } else if (manager.getByIdSubtask(id) != null) {
            System.out.println(" Название " + manager.getByIdSubtask(id).getTitle()
                    + " Описание " + manager.getByIdSubtask(id).getDescription()
                    + " Статус " + manager.getByIdSubtask(id).getStatus()
                    + " Id " + manager.getByIdSubtask(id).getId());
        } else if (manager.getByIdTask(id) != null) {
            System.out.println(" Название " + manager.getByIdTask(id).getTitle()
                    + " Описание " + manager.getByIdTask(id).getDescription()
                    + " Статус " + manager.getByIdTask(id).getStatus()
                    + " Id " + manager.getByIdTask(id).getId());
        }
    }

    public void createEpic() {
        System.out.println("Введите название эпика");
        String title = scanner.next();
        System.out.println("Введите описание");
        String description = scanner.next();
        String status = "NEW";
        int id = 0;
        Epic epic = new Epic(title, description, status, id);
        manager.createEpicTask(epic);
    }

    public void createSubtask() {
        System.out.println("Введите название sub");
        String title = scanner.next();
        System.out.println("Введите описание");
        String description = scanner.next();
        System.out.println("Введите epicId");
        int epicId = scanner.nextInt();
        String status = "NEW";
        int id = 0;
        Subtask subtask = new Subtask(title, description, status, id, epicId);
        manager.createSubtask(subtask);
    }

    public void createTask() {
        System.out.println("Введите название task");
        String title = scanner.next();
        System.out.println("Введите описание");
        String description = scanner.next();
        String status = "NEW";
        int id = 0;
        Task task = new Task (title, description, status, id);
        manager.createTask(task);
    }

    public void upDataEpic() {
        System.out.println("Введите id элемента которых ходите изменить");
        int id = scanner.nextInt();
        Epic epic = manager.getByIdEpic(id);
        System.out.println("Введите название эпика");
        String title = scanner.next();
        System.out.println("Введите описание");
        String description = scanner.next();
        epic.setTitle(title);
        epic.setDescription(description);
        manager.updateEpicTask(epic);
    }

    public void upDataSub() {
        System.out.println("Введите id элемента которых ходите изменить");
        int id = scanner.nextInt();
        Subtask subtask = manager.getByIdSubtask(id);
        System.out.println("Введите название sub");
        String title = scanner.next();
        System.out.println("Введите описание");
        String description = scanner.next();
        System.out.println("Введите статус");
        String status = scanner.next();
        subtask.setTitle(title);
        subtask.setDescription(description);
        subtask.setStatus(status);
        manager.updateSubtask(subtask);
    }

    public void upDataTask() {
        System.out.println("Введите id элемента которых ходите изменить");
        int id = scanner.nextInt();
        Task task = manager.getByIdTask(id);
        System.out.println("Введите название task");
        String title = scanner.next();
        System.out.println("Введите описание");
        String description = scanner.next();
        System.out.println("Введите статус");
        String status = scanner.next();
        task.setTitle(title);
        task.setDescription(description);
        task.setStatus(status);
        manager.updateTask(task);
    }

    public void deleteById() {
        System.out.println("Введите id элемента которых ходите удалить");
        int id = scanner.nextInt();
        manager.deleteByIdEpic(id);
        manager.deleteByIdSubtask(id);
        manager.deleteByIdTask(id);
    }

    public void getSubtaskByEpic() {
        System.out.println("Введите id эпика которых ходите получить");
        int id = scanner.nextInt();
        manager.getSubtaskByEpic(manager.getByIdEpic(id));
    }
}
