package engine;


public class DynamicArray implements IntList {
    private static final int INITIAL_CAPACITY = 16;

    private int[] data = new int[INITIAL_CAPACITY];
    private int size = 0;
    private final Metrics metrics = new Metrics();

    @Override
    public void add(int x) {
        ensureCapacity();
        data[size++] = x;
    }

    @Override
    public void add(int index, int x) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index=" + index + ", size=" + size);
        }
        ensureCapacity();
        // shift the tail one cell to the right
        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
            metrics.moves++;
        }
        data[index] = x;
        size++;
    }

    @Override
    public int remove(int index) {
        checkIndex(index);
        int removed = data[index];
        // shift the tail one cell to the left
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
            metrics.moves++;
        }
        size--;
        return removed;
    }

    @Override
    public int get(int index) {
        checkIndex(index);
        metrics.steps++;
        return data[index];
    }

    @Override
    public boolean contains(int x) {
        for (int i = 0; i < size; i++) {
            metrics.steps++;        
            metrics.comparisons++;  
            if (data[i] == x) {
                return true;
            }
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

    
    private void ensureCapacity() {
        if (size == data.length) {
            int[] bigger = new int[data.length * 2];
            for (int i = 0; i < size; i++) {
                bigger[i] = data[i];
                metrics.moves++;
            }
            data = bigger;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index=" + index + ", size=" + size);
        }
    }
}
