# In-Memory Workload Engine (Assignment 2)

DynamicArray, MyLinkedList and MinHeap written from scratch (primitive `int`, no `java.util` collections).

## Requirements
- JDK 17+
- Maven 3.8+

## Build
    mvn clean package

## Run tests
    mvn test

## Run benchmark (coming in feature/metrics)
    mvn compile exec:java

Results are written to `results/results.csv`, plots to `results/plots/`.

## Layout
    src/main/java/engine   structures, Metrics, benchmark
    src/test/java/engine   JUnit 5 tests
    results/               results.csv and plots/
    REPORT.md              complexity table, loop invariants, plots, discussion
# daa_asik2
