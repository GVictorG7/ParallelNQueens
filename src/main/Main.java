package main;

import java.time.Duration;
import java.time.Instant;

public class Main {
    // non: 2279184 - 1M19.4518605S P2: 2279184 - 9.7818787S
    // P2: 16: 14772512 - 34.1554748S; 17: 95815104 - 5M2.2752189S
    // FJTP: 16: 14772512 - 34.2575063S
    private static final int DIMENSION = 16;

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
        queens.compute();
        System.out.println(Queens.endTime.get() + " Solutions found: " + Queens.numberOfSolutions.get());
        System.out.println("duration non parallel: " + Duration.between(start, Queens.endTime.get()));
    }

    private static void parallelExecution() {
        System.out.println("Parallel solution 1:");
        Queens queensParallel = new QueensParallel(DIMENSION);
        Instant startParallel = Instant.now();
        queensParallel.compute();
        System.out.println(Queens.endTime.get() + " Solutions found: " + Queens.numberOfSolutions.get());
        System.out.println("duration parallel: " + Duration.between(startParallel, Queens.endTime.get()));
    }

    private static void parallelExecution2() {
        System.out.println("Parallel solution 2:");
        Queens queensParallel2 = new QueensParallel2(DIMENSION);
        Instant start = Instant.now();
        queensParallel2.compute();
        System.out.println(Queens.endTime.get() + " Solutions found: " + Queens.numberOfSolutions.get());
        System.out.println("duration parallel2: " + Duration.between(start, Queens.endTime.get()));
    }

    private static void parallelExecution3() {
        System.out.println("Parallel solution 3:");
        Queens queensParallel3 = new QueensParallel3(DIMENSION);
        Instant start = Instant.now();
        queensParallel3.compute();
        System.out.println(Queens.endTime.get() + " Solutions found: " + Queens.numberOfSolutions.get());
        System.out.println("duration parallel2: " + Duration.between(start, Queens.endTime.get()));
    }
}
