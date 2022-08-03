package main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class QueensParallel extends Queens {
    private final List<RecursiveAction> threads = new ArrayList<>();

    public QueensParallel(int dimension) {
        super(dimension);
    }

    public QueensParallel(int[] board, int startingColumn) {
        super(board, startingColumn);
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
                // create a new thread for the next step of the solution
                if (col > Math.sqrt(dimension)// && POOL.getQueuedTaskCount() > 0
                ) {
                    solveNQUtil(col + 1, board);
                } else {
                    int[] newBoard = copyBoard(board);
                    QueensParallel newThread = new QueensParallel(newBoard, col + 1);
                    POOL.execute(newThread);
                    threads.add(newThread);
                }
            }
        }
        threads.forEach(ForkJoinTask::join);
    }

    @Override
    public void compute() {
        solveNQUtil(startingColumn, board);
    }
}
