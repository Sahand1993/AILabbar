package training;

import hmm3.MainHmm3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CPart {

    static MainHmm3 main = new MainHmm3();
    private static final double DEV = 0.2;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static String dataset = "LARGE";

    public static void main(String[] args) {
        question7();
        question8();
        question9();
        question10();
    }

    /**
    The algorithm converges. It finished the convergence on iteration 949.
    The convergence can be interpreted in many ways. The way we interpreted convergence is when logProb gets close to 0.

     */
    private static void question7() {
        main.A = new double[][]{{0.54, 0.26, 0.20}, {0.19, 0.53, 0.28}, {0.22, 0.18, 0.60}};
        main.B = new double[][]{{0.5, 0.2, 0.11, 0.19}, {0.22, 0.28, 0.23, 0.27}, {0.19, 0.21, 0.15, 0.45}};
        main.pi = new double[][]{{1, 0, 0}};
        // Read O
        try {
            br = new BufferedReader(new FileReader("src/training/samples/hmm_c_N1000.in"));
        } catch(IOException e) {
            e.printStackTrace();
        }
        readO(main);

        fit();
        System.out.println(main.iters);
        main.printMatrix(main.A);
    }

    private static void question8() {
        System.out.println("\nQUESTION 8\n");
        calcErrors(10);
    }

    private static void question9() {
        System.out.println("\nQUESTION 9\n");
        // Let's do a grid search with 1 < N < 10 and M = 4
        // M must be 4.
        // Here, it is senseless to compare euclidean distances between A, B and pi matrices, since they will have different dimensions.
        // Instead, we should focus on the log probability, and making it as large as possible

        int N = 10;
        int iters = 5;
        double avgLogProb;
        for(int n = 1; n <= N; n++) {
            System.out.printf("N, M: %d, %d\n", n, 4);
            avgLogProb = avgLogProb(iters, n, 4);
            System.out.printf("avgLogProb: %f\n\n", avgLogProb);
        }
    }

    private static double avgLogProb(int iters, int n, int m) {
        double totLogProb = 0;
        for(int i = 0; i < iters; i++) {
            totLogProb += trainHMM(n, m);
        }
        return totLogProb /= iters;
    }

    /**
     * Prints the average of the distances to the generating matrices A, B and pi over iters iterations.
     * @param iters number of times to train the HMM.
     */
    private static void calcErrors(int iters) {
        double[][] realA = new double[][]{{0.7, 0.05, 0.25},
                {0.1, 0.8, 0.1},
                {0.2, 0.3, 0.5}};
        double[][] realB = new double[][]{{0.7, 0.2, 0.1, 0.0},
                {0.1, 0.4, 0.3, 0.2},
                {0.0, 0.1, 0.2, 0.7}};
        double[][] realPi = new double[][]{{1.0, 0.0, 0.0}};

        double avgADist = 0;
        double avgBDist = 0;
        double avgPiDist = 0;
        double avgLogProb = 0;
        for( int i = 0; i < iters; i++) {
            avgLogProb += trainHMM(3 , 4);
            double distA = euclDistance(realA, main.A);
            double distB = euclDistance(realB, main.B);
            double distPi = euclDistance(realPi, main.pi);

            avgADist += distA;
            avgBDist += distB;
            avgPiDist += distPi;
        }
        avgADist /= iters;
        avgBDist /= iters;
        avgPiDist /= iters;
        avgLogProb /= iters;

        System.out.printf("\navgADist = %f, avgBDist = %f, avgPiDist = %f\n", avgADist, avgBDist, avgPiDist);
        System.out.printf("Average distance per element:\nA: %f, B: %f, pi: %f\n", avgADist / (3 * 3), avgBDist / (3 * 4), avgPiDist / 3);
        System.out.printf("avgLogProb: %f\n", avgLogProb);
    }

    private static double euclDistance(double[][] x, double[][] y) {
        double dist = 0;
        for(int i = 0; i < x.length; i++) {
            for(int j = 0; j < x[0].length; j++) {
                dist += Math.pow(x[i][j] - y[i][j], 2);
            }
        }
        dist = Math.sqrt(dist);
        return dist;
    }

    private static void divideMatrix(double[][] x, int n) {
        for(int i = 0; i < x.length; i++) {
            for(int j = 0; j < x[0].length; j++) {
                x[i][j] /= n;
            }
        }
    }

    private static double[][] addMatrices(double[][] x, double[][] y) {
        double[][] res = new double[x.length][x[0].length];
        for(int i = 0; i < x.length; i++) {
            for(int j = 0; j < x[0].length; j++) {
                res[i][j] = x[i][j] + y[i][j];
            }
        }
        return res;
    }

    /**
     * 1. The algorithm does not converge to the right value when initialized with an uniform distribution.
     * 2. The algoithm returns NaN when initialized with A = diagonal matrix and pi = {0,0,1}. This is expected because
     * nothing should happen when there is no transition from the initial state matrix.
     * 3. If the algorithm is initialized with numbers close to the Real Solution the answer will be further
     * away than from the beginning.
     */
    private static void question10() {
        main.A = new double[][]{{0.56, 0.24, 0.20}, {0.12, 0.58, 0.30}, {0.22, 0.15, 0.63}};
        main.B = new double[][]{{0.4, 0.3, 0.11, 0.19}, {0.12, 0.38, 0.23, 0.27}, {0.29, 0.11, 0.15, 0.45}};
        main.pi = new double[][]{{1, 0, 0}};
        // Read O
        try {
            br = new BufferedReader(new FileReader("src/training/samples/hmm_c_N1000.in"));
        } catch(IOException e) {
            e.printStackTrace();
        }
        readO(main);

        fit();
        System.out.println(main.iters);
        main.printMatrix(main.A);
    }


    /**
     *
     * @param N: number of hidden states
     * @param M: number of observation symbols
     */
    private static double trainHMM(int N, int M) {
        double[][] pi;
        double[][] A;
        double[][] B;

        // Create random pi
        pi = randomMatrix(1, N);
        main.pi = pi;

        // Create random A
        A = randomMatrix(N, N);
        main.A = A;

        // Create random B
        B = randomMatrix(N, M);
        main.B = B;

        // Read O
        try {
            if(dataset.equals("SMALL")) {
                br = new BufferedReader(new FileReader("src/training/samples/hmm_c_N1000.in"));
            } else if(dataset.equals("LARGE")) {
                br = new BufferedReader(new FileReader("src/training/samples/hmm_c_N10000.in"));
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        readO(main);

        return main.fit();
    }


    private static void readO(MainHmm3 main) {
        String[] oArr = main.nextLineSplit(br);
        int[] O = main.parseArrayToVector(oArr);

        main.O = O;
    }

    /**
     * Generates a random row of length n centered around 0 with uniform deviation from 0 up to 0.05.
     * @param n length of returned vector
     * @return random vector
     */
    private static double[] randomRow(int n, double dev) {
        double[] res = new double[n];
        for (int i = 0; i < n; i++) {
            dev = (Math.random() - 0.5) * dev;
            res[i] = 1 / n + dev;
        }
        return res;
    }

    /**
     * Creates and returns row-stochastic matrix
     * @param rows: number of rows
     * @param cols: number of columns
     * @return row-stochastic matrix
     */
    private static double[][] randomMatrix(int rows, int cols) {
        double[][] res = new double[rows][cols];
        double[] randomRow;
        double sum;
        for (int i = 0; i < rows; i++) {
            randomRow = randomRow(cols, DEV);
            sum = 0;
            for(int j = 0; j < cols; j++) {
                res[i][j] = 1.0 / cols + randomRow[j];
                sum += res[i][j];
            }
            for(int j = 0; j < cols; j++) {
                res[i][j] /= sum;
            }
        }
        return res;
    }

    public static void fit(){
        main.estimateParams();
        double newLogProb = main.computeLogP();
        main.iters = 0;
        double diff = 10;
        do {
            //System.out.printf("logProb: %f\n", newLogProb);
            main.logProb = newLogProb;
            main.estimateParams();
            newLogProb = main.computeLogP();
            main.iters++;
            System.out.println(main.iters);
            diff = Math.abs(main.logProb - newLogProb);
            main.printMatrix(main.A);
            main.printMatrix(main.B);
            System.out.println(diff);
        } while(main.iters < main.maxIters && newLogProb > main.logProb && diff > Math.pow(10, -7));
        System.out.printf("Stopped after %d iterations.\n", main.iters);
    }


}
