import finalTask.model.Epic;
import finalTask.model.Subtask;
import finalTask.model.Task;
import finalTask.service.HistoryManager;
import finalTask.service.InMemoryTaskManager;

import java.util.LinkedList;

import static finalTask.model.Status.*;
import static finalTask.service.Managers.getDefaultHistory;

public class Main {

    public static void main(String[] args) {
        java.util.LinkedList<Task> task = new LinkedList();

        Task q1 = new Task("q", "q", NEW, 1);
        Task q2 = new Task("w", "w", DONE, 2);

        Epic z1 = new Epic("sa", "vz", IN_PROGRESS, 3);
        Epic z2 = new Epic("sq", "vf", NEW, 7);

        Subtask x1 = new Subtask("zvs", "wiqw", IN_PROGRESS, 4, 3);
        Subtask x2 = new Subtask("qwe", "sasaf", NEW, 5, 3);
        Subtask x3 = new Subtask("wer", "saf", NEW, 6, 3);

        InMemoryTaskManager manager = new InMemoryTaskManager();

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


        System.out.println(manager.getHistory());

        manager.deleteByIdTask(1);

        System.out.println(manager.getHistory());

        manager.deleteByIdEpic(3);

        System.out.println(manager.getHistory());

    }
}