package finalTask.service;

import finalTask.model.Task;

import java.util.*;

public class ModeLinkedList<T extends Task> {
    private final Map<Integer, Node<T>> hashMap = new HashMap<>();

    private static class Node<E> {
        public E data;
        public Node<E> next;
        public Node<E> prev;

        public Node<E> getNext() {
            return next;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }

        public Node<E> getPrev() {
            return prev;
        }

        public void setPrev(Node<E> prev) {
            this.prev = prev;
        }

        public Node(Node<E> prev, E data, Node<E> next) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node<T> head;
    private Node<T> tail;

    public void addFirst(T element, int id) {
        if (hashMap.containsKey(id)) {
            remove(id);
            hashMap.remove(id);
        }
        final Node<T> oldHead = head;
        final Node<T> newNode = new Node<>(null, element, oldHead);
        addInMap(id, newNode);
        head = newNode;
        if (oldHead == null) {
            tail = newNode;
        } else {
            oldHead.prev = newNode;
        }
    }

    public T getFirst() {
        final Node<T> curHead = head;
        if (curHead == null)
            throw new NoSuchElementException();
        return head.data;
    }

    public List<Task> getTask() {
        List<Task> nodeData = new ArrayList<>();
        Node<T> supportElement = head;
        while (supportElement != null) {
            nodeData.add(supportElement.data);
            supportElement = supportElement.next;
        }
        return nodeData;
    }

    public void addLast(T element) {
        final Node<T> oldTail = tail;
        final Node<T> newNode = new Node<>(oldTail, element, null);
        tail = newNode;
        if (oldTail != null) {
            oldTail.next = newNode;
        } else {
            head = newNode;
        }
    }

    public T getLast() {
        final Node<T> curTail = tail;
        if (curTail == null)
            throw new NoSuchElementException();
        return tail.data;
    }

    protected void remove(int id) {
        Node<T> removeNode = findElement(id);
        if (findElement(id) != null) {
            Node<T> next = removeNode.next;
            Node<T> prev = removeNode.prev;
            if (next == null && prev == null) {
                head = null;
                tail = null;
            } else if (removeNode == head) {
                next.setPrev(null);
                head = next;
            } else if (removeNode == tail) {
                prev.setNext(null);
                tail = prev;
            } else {
                next.setPrev(prev);
                prev.setNext(next);
            }
            hashMap.remove(id);
        }
    }

    private Node<T> findElement(int id) {
        return hashMap.get(id);
    }

    private void addInMap(int id, Node<T> newNode) {
        hashMap.put(id, newNode);
    }
}
