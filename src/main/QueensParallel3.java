package main;

import java.util.ArrayList;
import java.util.List;

public class QueensParallel3 extends Queens {
    public QueensParallel3(int dimension) {
        super(dimension);
    }

    @Override
    protected void solveNQUtil(int col, byte[][] board) {
        if (isSolution(col)) {
            return;
        }

        // Consider this column and try placing this queen in all rows one by one
        for (int i = 0; i < dimension; i++) {
            // Check if the queen can be placed on board[i][col]
            if (isConsistent(i, col, board)) {
                // Place this queen in board[i][col]
                board[i][col] = 1;

                // recur to place rest of the queens
                // copy the board so that the new thread can work with its own data
                byte[][] newBoard = copyBoard(board);
                // create a new thread for the next step of the solution
                new Thread(() -> solveNQUtil(col + 1, newBoard)).start();

                // remove queen from board[i][col]
                board[i][col] = 0; // BACKTRACK
            }
        }
    }

    @Override
    public void solveNQ() {
        // we create N threads, each starting with a different position of the first Queen
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
