package engine;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;


abstract class IntListContractTest {

    abstract IntList create();

    @Test
    void emptyStructure() {
        IntList list = create();
        assertEquals(0, list.size());
        assertFalse(list.contains(1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(0));
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(1, 5));
    }

    @Test
    void oneElement() {
        IntList list = create();
        list.add(7);
        assertEquals(1, list.size());
        assertEquals(7, list.get(0));
        assertTrue(list.contains(7));
        assertEquals(7, list.remove(0));
        assertEquals(0, list.size());
    }

    @Test
    void duplicates() {
        IntList list = create();
        list.add(3);
        list.add(3);
        list.add(3);
        assertEquals(3, list.size());
        assertEquals(3, list.remove(1));
        assertEquals(2, list.size());
        assertTrue(list.contains(3));
    }

    @Test
    void firstAndLastIndex() {
        IntList list = create();
        for (int i = 0; i < 5; i++) list.add(i);
        list.add(0, 100);          
        list.add(list.size(), 200); 
        assertEquals(100, list.get(0));
        assertEquals(200, list.get(list.size() - 1));
        assertEquals(200, list.remove(list.size() - 1));
        assertEquals(100, list.remove(0));
    }

    @Test
    void invalidIndexThrows() {
        IntList list = create();
        list.add(1);
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(-1, 5));
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(2, 5));
    }

    @Test
    void growsPastInitialCapacity() {
        IntList list = create();
        for (int i = 0; i < 1000; i++) list.add(i);
        assertEquals(1000, list.size());
        for (int i = 0; i < 1000; i++) assertEquals(i, list.get(i));
    }

    @Test
    void randomOperationsMatchJavaUtil() {
        Random rnd = new Random(42);
        IntList list = create();
        ArrayList<Integer> expected = new ArrayList<>();
        for (int step = 0; step < 5000; step++) {
            int op = rnd.nextInt(4);
            if (op == 0 || expected.isEmpty()) {
                int x = rnd.nextInt(100);
                list.add(x);
                expected.add(x);
            } else if (op == 1) {
                int idx = rnd.nextInt(expected.size() + 1);
                int x = rnd.nextInt(100);
                list.add(idx, x);
                expected.add(idx, x);
            } else if (op == 2) {
                int idx = rnd.nextInt(expected.size());
                assertEquals((int) expected.remove(idx), list.remove(idx));
            } else {
                int x = rnd.nextInt(100);
                assertEquals(expected.contains(x), list.contains(x));
            }
            assertEquals(expected.size(), list.size());
        }
        for (int i = 0; i < expected.size(); i++) {
            assertEquals((int) expected.get(i), list.get(i));
        }
    }

    @Test
    void countersAreNotZero() {
        IntList list = create();
        for (int i = 0; i < 100; i++) list.add(i);
        list.metrics().reset();
        list.add(50, -1);
        list.remove(50);
        list.contains(99);
        list.get(60);
        Metrics m = list.metrics();
        assertTrue(m.moves > 0, "moves must be counted");
        assertTrue(m.steps > 0, "steps must be counted");
        assertTrue(m.comparisons > 0, "comparisons must be counted");
    }
}
