package main;

public class QueensParallel extends Queens {
    public QueensParallel(int dimension) {
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

                // copy the board so that the new thread can work with its own data
                byte[][] newBoard = copyBoard(board);
                // create a new thread for the next step of the solution
                new Thread(() -> solveNQUtil(col + 1, newBoard)).start();

                // remove queen from board[i][col]
                board[i][col] = 0; // BACKTRACK
            }
        }
    }
}
