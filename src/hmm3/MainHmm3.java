package hmm3;

import hmm2.MainHmm2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.Math;

public class MainHmm3 extends MainHmm2 {

    public static double[][] beta; // i, t
    public static double[][][] digamma; // i, j, t
    public static double[][] gamma; // i, t
    public static int maxIters = 1000;
    private static double[] colSums;
    private static double logProb;

    public static void main(String[] args) {
        br = new BufferedReader(new InputStreamReader(System.in));
        readInput();

        fit();

        System.out.printf("%d %d ", A.length, A[0].length);
        for (int i = 0; i < A.length; i++) {
            for(int j = 0; j < A[0].length; j++) {
                System.out.printf("%f ", A[i][j]);
            }
        }
        System.out.println();
        System.out.printf("%d %d ", B.length, B[0].length);
        for (int i = 0; i < B.length; i++){
            for(int j = 0; j < B[0].length; j++) {
                System.out.printf("%f ", B[i][j]);
            }
        }
    }

    public static void fit(){
        estimateParams();
        double newLogProb = computeLogP();
        int iters = 0;
        do {
            //System.out.printf("logProb: %f\n", newLogProb);
            logProb = newLogProb;
            estimateParams();
            newLogProb = computeLogP();
            iters++;
        } while(iters < maxIters && newLogProb > logProb);
        System.out.printf("Stopped after %d iterations.\n", iters);
    }

    private static double computeLogP() {
        double logProb = 0;
        for(int i = 0; i < O.length; i++) {
            logProb += Math.log(1 / colSums[i]); // Does it matter which log-base is used?
        }
        logProb = -logProb;
        return logProb;
    }

    private static void estimateParams() {
        fillAlpha();
        fillBeta();
        fillGammas();

        for(int i = 0; i < A.length; i++) {
            pi[0][i] = gamma[i][0];
        }

        double numer;
        double denom;
        for(int i = 0; i < A.length; i++) {
            for(int j = 0; j < A.length; j++) {
                numer = 0;
                denom = 0;
                for(int t = 0; t < O.length - 1; t++) {
                    numer += digamma[i][j][t];
                    denom += gamma[i][t];
                }
                A[i][j] = numer / denom;
            }
        }

        for(int i = 0; i < A.length; i++) {
            for(int j = 0; j < B[0].length; j++) {
                numer = 0;
                denom = 0;
                for(int t = 0; t < O.length; t++) {
                    if(O[t] == j) {
                        numer += gamma[i][t];
                    }
                    denom += gamma[i][t];
                }
                B[i][j] = numer / denom;
            }
        }
    }

    public static void fillAlpha() {
        alpha = new double[A.length][O.length];
        colSums = new double[O.length];
        // First column of alpha
        double colSum = 0;
        for (int i = 0; i < alpha.length; i++) {
            alpha[i][0] = pi[0][i] * B[i][O[0]];
            colSum += alpha[i][0];
        }
        colSums[0] = colSum;
        normalizeCol(alpha, 0, colSum);

        double[][] alphaOld;
        double[][] newColAlpha = extractColumn(alpha, 0);
        for (int i = 1; i < O.length; i++) {
            alphaOld = extractColumn(alpha, i - 1);
            newColAlpha = matrixMul(transpose(alphaOld), A);
            newColAlpha = vectorMul(transpose(newColAlpha), extractColumn(B, O[i]));
            colSum = 0;
            for(int j = 0; j < alpha.length; j++) {
                alpha[j][i] = newColAlpha[j][0];
                colSum += alpha[j][i];
            }
            colSums[i] = colSum;
            normalizeCol(alpha, i, colSum);
        }
    }

    private static void fillBeta() {
        beta = new double[A.length][O.length];
        // Last col of beta
        for (int i = 0; i < beta.length; i++) {
            beta[i][beta[0].length - 1] = 1 / colSums[O.length - 1];
        }
        for (int t = O.length - 2; t >= 0; t--) {
            for (int i = 0; i < A.length; i++) {
                double sum = 0;
                for (int j = 0; j < A.length; j++) {
                    sum += beta[j][t + 1] * B[j][O[t + 1]] * A[i][j];
                }
                beta[i][t] = sum / colSums[t];
            }
        }
    }

    private static void fillGammas() {
        gamma = new double[A.length][O.length];
        digamma = new double[A.length][A.length][O.length - 1];
        for (int t = 0; t < O.length - 1; t++) {
            for (int i = 0; i < A.length; i++) {
                gamma[i][t] = 0;
                for (int j = 0; j < A.length; j++) {
                    digamma[i][j][t] = alpha[i][t] * A[i][j] * B[j][O[t + 1]] * beta[j][t + 1];
                    gamma[i][t] += digamma[i][j][t];
                }
            }
        }
        for (int i = 0; i < A.length; i++) {
            gamma[i][O.length - 1] = alpha[i][O.length - 1];
        }
    }

    public static void printMatrix(double[][][] m) {
        for(int t = 0; t < m[0][0].length; t++) {
            for(int i = 0; i < m.length; i++) {
                for(int j = 0; j < m[0].length; j++){
                    System.out.printf("%f ", m[i][j][t]);
                }
                System.out.println();
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void normalizeCol(double[][] m, int colNo, double colSum) {
        for (int i = 0; i < m.length; i++) {
            m[i][colNo] /= colSum;
        }
    }

    private static void printCol(double[][] m, int colNo){
        for (int i = 0; i < m.length; i++) {
            System.out.printf("%f\n", m[i][colNo]);
        }
    }
}
