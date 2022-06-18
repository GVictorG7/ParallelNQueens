package main;

import java.util.ArrayList;
import java.util.List;

public class QueensParallel3 extends Queens {
    public QueensParallel3(int dimension) {
        super(dimension);
    }

    @Override
    protected void solveNQUtil(int col, int[] board) {
        if (isSolution(col)) {
            return;
        }

        // Consider this column and try placing this queen in all rows one by one
        for (int i = 0; i < dimension; i++) {
            // place the queen from column = col at the row = i
            board[col] = i;

            if (isConsistent(col, board)) {
                // copy the board so that the new thread can work with its own data
                int[] newBoard = copyBoard(board);
                // create a new thread for the next step of the solution
                new Thread(() -> solveNQUtil(col + 1, newBoard)).start();
            }
        }
    }

    @Override
    public void compute() {
        // we create N threads, each starting with a different position of the first Queen
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < dimension; i++) {
            int[] startingElement = createStartingElement(i);
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
