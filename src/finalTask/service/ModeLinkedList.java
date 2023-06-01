package finalTask.service;

import finalTask.model.Task;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class ModeLinkedList<T extends Task> {
    public Map<Integer, Node> hashMap = new HashMap<>();

    class Node<E> {
        public E data;
        public Node<E> next;
        public Node<E> prev;

        public Node(Node<E> prev, E data, Node<E> next) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    public void addFirst(T element, int id) {
        final Node<T> oldHead = head;
        final Node<T> newNode = new Node<>(null, element, oldHead);
        if (hashMap.containsValue(newNode)) {
            remove(hashMap.get(newNode));
            hashMap.remove(newNode);
        }
        addInMap(id, newNode);
        head = newNode;
        if (oldHead == null) {
            tail = newNode;
        } else {
            oldHead.prev = newNode;
        }
        size++;
        if (hashMap.size() > 10) {
            remove(tail);
            hashMap.remove(hashMap.size() - 1);
        }
    }

    public T getFirst() {
        final Node<T> curHead = head;
        if (curHead == null)
            throw new NoSuchElementException();
        return head.data;
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
        size++;
    }

    public T getLast() {
        final Node<T> curTail = tail;
        if (curTail == null)
            throw new NoSuchElementException();
        return tail.data;
    }

    public int size() {
        return this.size;
    }

    public void remove(int id) {
        Node removeNode = findElement(id);
        if (removeNode == head) {
            head = removeNode.next;
        } else {
            removeNode.prev = removeNode.next;
        }
    }

    public void remove(Node removeNode) {
        if (removeNode == head) {
            head = removeNode.next;
        } else if (removeNode == tail) {
            tail = removeNode.prev;
        } else {
            removeNode.prev = removeNode.next;
        }
    }

    public Node findElement(int id) {
        return hashMap.get(id);
    }

    public void addInMap(int id, Node newNode) {
        hashMap.put(id, newNode);
    }
}
