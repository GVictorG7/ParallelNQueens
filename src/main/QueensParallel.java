package main;

public class QueensParallel extends Queens {
    public QueensParallel(int dimension) {
        super(dimension);
    }

    @Override
    protected void solveNQUtil(int col, int[] board) {
        if (isSolution(col)) {
            return;
        }

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
}
