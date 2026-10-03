package engine;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MyLinkedListTest extends IntListContractTest {
    @Override
    IntList create() {
        return new MyLinkedList();
    }

    @Test
    void getCostsAboutIndexSteps() {
        MyLinkedList list = new MyLinkedList();
        for (int i = 0; i < 100; i++) list.add(i);
        list.metrics().reset();
        list.get(77);
        assertEquals(77, list.metrics().steps);
    }

    @Test
    void insertAtHeadIsConstant() {
        MyLinkedList list = new MyLinkedList();
        for (int i = 0; i < 100; i++) list.add(i);
        list.metrics().reset();
        list.add(0, -1);
        assertEquals(0, list.metrics().steps);
        assertEquals(2, list.metrics().moves);
    }
}
