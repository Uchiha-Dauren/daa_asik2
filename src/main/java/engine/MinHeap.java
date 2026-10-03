package engine;

public class MinHeap {
    private static final int INITIAL_CAPACITY = 16;

    private int[] a = new int[INITIAL_CAPACITY];
    private int size = 0;
    private final Metrics metrics = new Metrics();

    public void insert(int x) {
        if (size == a.length) {
            int[] bigger = new int[a.length * 2];
            for (int i = 0; i < size; i++) {
                bigger[i] = a[i];
                metrics.moves++;
            }
            a = bigger;
        }
        a[size] = x;
        metrics.moves++;
        bubbleUp(size);
        size++;
    }

    public int peekMin() {
        if (size == 0) {
            throw new IllegalStateException("heap is empty");
        }
        metrics.steps++;
        return a[0];
    }

    public int extractMin() {
        if (size == 0) {
            throw new IllegalStateException("heap is empty");
        }
        int min = a[0];
        metrics.steps++;
        size--;
        a[0] = a[size];       
        metrics.moves++;
        if (size > 0) {
            bubbleDown(0);
        }
        return min;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public Metrics metrics() {
        return metrics;
    }

    
    public boolean isValidHeap() {
        for (int i = 1; i < size; i++) {
            if (a[(i - 1) / 2] > a[i]) {
                return false;
            }
        }
        return true;
    }

    private void bubbleUp(int i) {
        while (i > 0) {
            int parent = (i - 1) / 2;
            metrics.comparisons++;
            metrics.steps += 2;
            if (a[i] < a[parent]) {
                swap(i, parent);
                i = parent;
            } else {
                break;
            }
        }
    }

    private void bubbleDown(int i) {
        while (true) {
            int left = 2 * i + 1;
            int right = left + 1;
            if (left >= size) {
                break;
            }
            int smallest = left;
            if (right < size) {
                metrics.comparisons++;
                metrics.steps += 2;
                if (a[right] < a[left]) {
                    smallest = right;
                }
            }
            metrics.comparisons++;
            metrics.steps += 2;
            if (a[smallest] < a[i]) {
                swap(i, smallest);
                i = smallest;
            } else {
                break;
            }
        }
    }

    private void swap(int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
        metrics.moves += 2;
    }
}
