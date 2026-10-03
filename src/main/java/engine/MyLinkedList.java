package engine;

public class MyLinkedList implements IntList {
    private static final class Node {
        final int value;
        Node next;

        Node(int value) {
            this.value = value;
        }
    }

    private Node head;
    private Node tail;
    private int size;
    private final Metrics metrics = new Metrics();

    @Override
    public void add(int x) {
        Node node = new Node(x);
        if (head == null) {
            head = node;
            tail = node;
            metrics.moves++;
        } else {
            tail.next = node;
            tail = node;
            metrics.moves++;
        }
        size++;
    }

    @Override
    public void add(int index, int x) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index=" + index + ", size=" + size);
        }
        if (index == size) {
            add(x);
            return;
        }
        Node node = new Node(x);
        if (index == 0) {
            node.next = head;
            head = node;
            metrics.moves += 2;
        } else {
            Node prev = nodeAt(index - 1);
            node.next = prev.next;
            prev.next = node;
            metrics.moves += 2;
        }
        size++;
    }

    @Override
    public int remove(int index) {
        checkIndex(index);
        int removed;
        if (index == 0) {
            removed = head.value;
            head = head.next;
            metrics.moves++;
            if (head == null) {
                tail = null;
            }
        } else {
            Node prev = nodeAt(index - 1);
            Node target = prev.next;
            removed = target.value;
            prev.next = target.next;
            metrics.moves++;
            if (target == tail) {
                tail = prev;
            }
        }
        size--;
        return removed;
    }

    @Override
    public int get(int index) {
        checkIndex(index);
        return nodeAt(index).value;
    }

    @Override
    public boolean contains(int x) {
        Node cur = head;
        while (cur != null) {
            metrics.comparisons++;
            if (cur.value == x) {
                return true;
            }
            cur = cur.next;
            metrics.steps++;       
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Metrics metrics() {
        return metrics;
    }

    
    private Node nodeAt(int index) {
        Node cur = head;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
            metrics.steps++;
        }
        return cur;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index=" + index + ", size=" + size);
        }
    }
}
