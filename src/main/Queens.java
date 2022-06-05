package main;

import java.time.Instant;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Queens {
    protected final int dimension;
    protected final byte[][] board;
    public volatile static AtomicReference<Instant> endTime = new AtomicReference<>(Instant.now());
    public volatile static AtomicInteger numberOfSolutions = new AtomicInteger(0);

    public Queens(int dimension) {
        this.dimension = dimension;
        this.board = new byte[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            Arrays.fill(board[i], (byte) 0);
        }
    }

//    protected synchronized void printSolution(byte[][] board) {
//        for (int i = 0; i < dimension; i++) {
//            for (int j = 0; j < dimension; j++) {
//                System.out.print(board[i][j] + " ");
//            }
//            System.out.println();
//        }
//        System.out.println();
//    }

    protected boolean isConsistent(int row, int col, byte[][] board) {
        // Check this row on left side
        for (int j = 0; j < col; j++) {
            if (board[row][j] == 1) {
                return false;
            }
        }

        // Check upper diagonal on left side
        for (int i = row, j = col; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 1) {
                return false;
            }
        }

        // Check lower diagonal on left side
        for (int i = row, j = col; j >= 0 && i < dimension; i++, j--) {
            if (board[i][j] == 1) {
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
                byte[][] newBoard = copyBoard(board);
                solveNQUtil(col + 1, newBoard);

                // remove queen from board[i][col]
                board[i][col] = 0; // BACKTRACK
            }
        }
    }

    public void solveNQ() {
        solveNQUtil(0, copyBoard(board));
    }

    protected byte[][] copyBoard(byte[][] board) {
        byte[][] newBoard = new byte[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            newBoard[i] = Arrays.copyOf(board[i], dimension);
        }
        return newBoard;
    }

    /**
     * Creates a board with only one Queen, on the fist column and the specified row
     *
     * @param row the row of the Queen
     * @return a new board with a Queen on the first column
     */
    protected byte[][] createStartingElement(int row) {
        byte[][] startingElement = copyBoard(board);
        startingElement[row][0] = 1;
        return startingElement;
    }

    protected synchronized void updateEndTime(Instant newTime) {
        if (newTime.isAfter(endTime.get())) {
//            System.out.println(newTime);
            endTime.set(newTime);
        }
    }
}
