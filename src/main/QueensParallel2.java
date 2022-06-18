package main;

import java.util.ArrayList;
import java.util.List;

public class QueensParallel2 extends Queens {
    public QueensParallel2(int dimension) {
        super(dimension);
    }

    public QueensParallel2(int[] board, int startingColumn) {
        super(board, startingColumn);
    }

    @Override
    public void compute() {
        // we create N threads, each starting with a different position for the first Queen
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < dimension; i++) {
            int[] startingElement = createStartingElement(i);

            forkJoinPool.invoke(new Queens(startingElement, 1));
//            Thread thread = new Thread(() -> solveNQUtil(1, startingElement));
//            threads.add(thread);
//            thread.start();
        }
//        for (Thread thread : threads) {
//            try {
//                thread.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }
}
