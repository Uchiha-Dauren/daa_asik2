package engine;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class MinHeapTest {

    @Test
    void emptyHeapThrows() {
        MinHeap heap = new MinHeap();
        assertThrows(IllegalStateException.class, heap::peekMin);
        assertThrows(IllegalStateException.class, heap::extractMin);
    }

    @Test
    void oneElement() {
        MinHeap heap = new MinHeap();
        heap.insert(5);
        assertEquals(5, heap.peekMin());
        assertEquals(5, heap.extractMin());
        assertTrue(heap.isEmpty());
    }

    @Test
    void duplicates() {
        MinHeap heap = new MinHeap();
        for (int i = 0; i < 10; i++) heap.insert(4);
        for (int i = 0; i < 10; i++) assertEquals(4, heap.extractMin());
    }

    @Test
    void heapPropertyHoldsAfterEveryOperation() {
        Random rnd = new Random(42);
        MinHeap heap = new MinHeap();
        for (int i = 0; i < 1000; i++) {
            heap.insert(rnd.nextInt(10_000));
            assertTrue(heap.isValidHeap(), "after insert #" + i);
        }
        while (!heap.isEmpty()) {
            heap.extractMin();
            assertTrue(heap.isValidHeap(), "after extractMin");
        }
    }

    @Test
    void extractMinReturnsNonDecreasingValues() {
        Random rnd = new Random(42);
        MinHeap heap = new MinHeap();
        int n = 10_000;
        for (int i = 0; i < n; i++) heap.insert(rnd.nextInt());
        int prev = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            int x = heap.extractMin();
            assertTrue(prev <= x, "order broken at " + i);
            prev = x;
        }
        assertTrue(heap.isEmpty());
    }

    @Test
    void peekMinDoesNotRemove() {
        MinHeap heap = new MinHeap();
        heap.insert(9);
        heap.insert(2);
        heap.insert(5);
        assertEquals(2, heap.peekMin());
        assertEquals(3, heap.size());
    }
}
