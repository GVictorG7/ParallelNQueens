package main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

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
        List<RecursiveAction> threads = new ArrayList<>();
        for (int i = 0; i < dimension; i++) {
            int[] startingElement = createStartingElement(i);
            Queens queen = new Queens(startingElement, 1);
            queen.fork();
            threads.add(queen);
        }
//        invokeAll(threads); //28.5
        threads.forEach(ForkJoinTask::join); //32.82
    }
}
