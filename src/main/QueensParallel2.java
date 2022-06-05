package main;

import java.util.ArrayList;
import java.util.List;

public class QueensParallel2 extends Queens {
    public QueensParallel2(int dimension) {
        super(dimension);
    }

    @Override
    public void solveNQ() {
        // we create N threads, each starting with a different position for the first Queen
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < dimension; i++) {
            byte[][] startingElement = createStartingElement(i);
            Thread thread = new Thread(() -> solveNQUtil(1, startingElement));
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
