package main;

import java.time.Duration;
import java.time.Instant;

public class Main {
    // 15  (2279184): non:   28.5359368S | P2:  5.2216068S
    // 16 (14772512): non: 3M37.4622168S | P2: 34.1554748S
    // 17 (95815104): non: 58M3.3582646S | P2: 5M2.2752189S / 6M55.6064562S
    private static final int DIMENSION = 17;

    public static void main(String... args) {
//        simpleExecution();
//        parallelExecution();
        parallelExecution2();
//        parallelExecution3();
    }

    private static void simpleExecution() {
        System.out.println("Non parallel solution:");
        Queens queens = new Queens(DIMENSION);
        Instant start = Instant.now();
        queens.solveNQ();
        System.out.println(Queens.endTime.get() + " Solutions found: " + Queens.numberOfSolutions.get());
        System.out.println("duration non parallel: " + Duration.between(start, Queens.endTime.get()));
    }

    private static void parallelExecution() {
        System.out.println("Parallel solution 1:");
        Queens queensParallel = new QueensParallel(DIMENSION);
        Instant startParallel = Instant.now();
        queensParallel.solveNQ();
        System.out.println(Queens.endTime.get() + " Solutions found: " + Queens.numberOfSolutions.get());
        System.out.println("duration parallel: " + Duration.between(startParallel, Queens.endTime.get()));
    }

    private static void parallelExecution2() {
        System.out.println("Parallel solution 2:");
        Queens queensParallel2 = new QueensParallel2(DIMENSION);
        Instant start = Instant.now();
        queensParallel2.solveNQ();
        System.out.println(Queens.endTime.get() + " Solutions found: " + Queens.numberOfSolutions.get());
        System.out.println("duration parallel2: " + Duration.between(start, Queens.endTime.get()));
    }

    private static void parallelExecution3() {
        System.out.println("Parallel solution 3:");
        Queens queensParallel3 = new QueensParallel3(DIMENSION);
        Instant start = Instant.now();
        queensParallel3.solveNQ();
        System.out.println(Queens.endTime.get() + " Solutions found: " + Queens.numberOfSolutions.get());
        System.out.println("duration parallel2: " + Duration.between(start, Queens.endTime.get()));
    }
}
