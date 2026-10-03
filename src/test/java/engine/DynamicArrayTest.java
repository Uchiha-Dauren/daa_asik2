package engine;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DynamicArrayTest extends IntListContractTest {
    @Override
    IntList create() {
        return new DynamicArray();
    }

    @Test
    void getCostsOneStep() {
        DynamicArray arr = new DynamicArray();
        for (int i = 0; i < 100; i++) arr.add(i);
        arr.metrics().reset();
        arr.get(77);
        assertEquals(1, arr.metrics().steps);
    }

    @Test
    void insertAtHeadShiftsEveryElement() {
        DynamicArray arr = new DynamicArray();
        for (int i = 0; i < 10; i++) arr.add(i);
        arr.metrics().reset();
        arr.add(0, -1);
        assertEquals(10, arr.metrics().moves);
    }
}
