package finalTask.service;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printMenu();
            int command = scanner.nextInt();
            int subcommand;
            if (command == 1) {
                    printList();
                    subcommand = scanner.nextInt();
                    if (subcommand == 1) {
                        manager.getAllEpic();
                    } else if (subcommand == 2) {
                        manager.getAllSubtasks();
                    } else if (subcommand == 3) {
                        manager.getAllTask();
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
                System.out.println("Введите id элемента, который хотите получить");
                subcommand = scanner.nextInt();
                manager.getById(subcommand);
            } else if (command == 4) {
                printCreate();
                subcommand = scanner.nextInt();
                if (subcommand == 1) {
                    manager.createEpicTask();
                } else if (subcommand == 2) {
                    manager.createSubtask();
                } else if (subcommand == 3) {
                    manager.createTask();
                } else {
                    System.out.println("Извините, такой команды пока нет.");
                }
            } else if (command == 5) {
                printUpdate();
                subcommand = scanner.nextInt();
                System.out.println("Введите id элемента которых ходите изменить");
                int id = scanner.nextInt();
                if (subcommand == 1) {
                    manager.updateEpicTask(id);
                } else if (subcommand == 2) {
                    manager.updateSubtask(id);
                } else if (subcommand == 3) {
                    manager.updateTask(id);
                } else {
                    System.out.println("Извините, такой команды пока нет.");
                }
            } else if (command == 6) {
                System.out.println("Введите id задачи, которую хотите удалить");
                int id = scanner.nextInt();
                manager.deleteById(id);
            } else if (command == 7) {
                System.out.println("Введите id эпика");
                int id = scanner.nextInt();
                manager.getSubtaskByEpic(id);
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

}
