package main;

import java.time.Instant;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Queens extends RecursiveAction {
    protected final int dimension;
    protected final int[] board;
    protected final int startingColumn;
    protected ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
    public volatile static AtomicReference<Instant> endTime = new AtomicReference<>(Instant.now());
    public volatile static AtomicInteger numberOfSolutions = new AtomicInteger(0);

    public Queens(int dimension) {
        this(new int[dimension], 0);
    }

    public Queens (int[] board, int startingColumn) {
        this.dimension = board.length;
        this.board = board;
        this.startingColumn = startingColumn;
    }

    protected synchronized void printSolution(int[] board) {
        for (int value : board) {
            System.out.printf(value + "%3s", " ");
        }

        System.out.println("\n");

        for (int i = 0; i < dimension; i++) {
            for (int value : board) {
                System.out.println(value == i ? "Q\t" : "*\t");
            }
            System.out.print("\n");
        }
    }

    protected boolean isConsistent(int col, int[] board) {
        for (int i = 0; i < col; i++) {
            // same row
            if (board[i] == board[col]) {
                return false;
            }

            // diagonal
            if (col - i == Math.abs(board[col] - board[i])) {
                return false;
            }
        }

        return true;
    }

    protected boolean isSolution(int col) {
        if (col >= dimension) {
            updateEndTime(Instant.now());
            numberOfSolutions.getAndIncrement();
            return true;
        }
        return false;
    }

    protected void solveNQUtil(int col, int[] board) {
        if (isSolution(col)) {
            return;
        }

        for (int i = 0; i < dimension; i++) {
            // place the queen from column = col at the row = i
            board[col] = i;
            if (isConsistent(col, board)) {
                // recur to place rest of the queens
                solveNQUtil(col + 1, board);
            }
        }
    }

    @Override
    public void compute() {
        solveNQUtil(startingColumn, board);
    }

    protected int[] copyBoard(int[] board) {
        return Arrays.copyOf(board, dimension);
    }

    /**
     * Creates a board with only one Queen, on the fist column and the specified row
     *
     * @param row the row of the Queen
     * @return a new board with a Queen on the first column
     */
    protected int[] createStartingElement(int row) {
        int[] startingElement = copyBoard(board);
        startingElement[0] = row;
        return startingElement;
    }

    protected synchronized void updateEndTime(Instant newTime) {
        if (newTime.isAfter(endTime.get())) {
//            System.out.println(newTime);
            endTime.set(newTime);
        }
    }
}
