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

    public static void main(String[] args) {
        question7();
        question8();
        question9();
        question10();
    }

    private static void question7() {
    }

    private static void question8() {
        int N = 100;
        /*
        double[][] totA = new double[3][3];
        double[][] totB = new double[3][4];
        double[][] totPi = new double[1][3];
        */
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

        for( int i = 0; i < N; i++) {
            trainHMM(3, 4);
            double distA = euclDistance(realA, main.A);
            double distB = euclDistance(realB, main.B);
            double distPi = euclDistance(realPi, main.pi);
            System.out.printf("\nEpoch %d\ntrained until main.pi:\n", i + 1);
            for(int j = 0; j < main.pi[0].length; j++){
                System.out.printf("%f ", main.pi[0][j]);
            }
            System.out.println();
            System.out.printf("distPi: %f\n", distPi);

            avgADist += distA;
            avgBDist += distB;
            avgPiDist += distPi;
            /*
            totA = addMatrices(totA, main.A);
            totB = addMatrices(totB, main.B);
            totPi = addMatrices(totPi, main.pi);
            */
        }
        avgADist /= N;
        avgBDist /= N;
        avgPiDist /= N;

        System.out.printf("\navgADist = %f, avgBDist = %f, avgPiDist = %f\n", avgADist, avgBDist, avgPiDist);
        System.out.printf("Average distance per element:\nA: %f, B: %f, pi: %f\n", avgADist / (3 * 3), avgBDist / (3 * 4), avgPiDist / 3);
        /*
        divideMatrix(totA, N);
        divideMatrix(totB, N);
        divideMatrix(totPi, N);

        main.printMatrix(totA);
        main.printMatrix(totB);
        main.printMatrix(totPi);
        */

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

    private static void question9() {
    }

    private static void question10() {
    }

    /**
     *
     * @param N: number of hidden states
     * @param M: number of observation symbols
     */
    private static void trainHMM(int N, int M) {
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
            br = new BufferedReader(new FileReader("src/training/samples/hmm_c_N1000.in"));
        } catch(IOException e) {
            e.printStackTrace();
        }
        readO(main);

        main.fit();
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


}
