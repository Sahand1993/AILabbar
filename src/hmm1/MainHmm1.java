// Written by Sahand Zarrinkoub and Shahin Saleh

package hmm1;
import hmm0.MainHmm0;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MainHmm1 extends MainHmm0 {
    public static double[][] A;
    public static double[][] B;
    public static double[][] pi;
    public static int[] O;
    public static BufferedReader br;
    public static double[][] alpha;

    public static void main(String[] args) {
        br = new BufferedReader(new InputStreamReader(System.in));
        readInput();

        fillAlpha();
        //double[][] alpha1 = vectorMul(transpose(pi), extractColumn(B, O[0]));
        //double ans = alpha(A, B, alpha1);
        double[][] lastColAlpha = extractColumn(alpha, alpha[0].length - 1);
        double ans = sumMatrix(lastColAlpha);
        System.out.println(ans);
    }

    public static void fillAlpha() {
        alpha = new double[A.length][O.length];
        // First column of alpha
        for (int i = 0; i < alpha.length; i++) {
            alpha[i][0] = pi[0][i] * B[i][O[0]];
        }

        double[][] alphaA;
        double[][] newColAlpha = extractColumn(alpha, 0);
        for (int i = 1; i < O.length; i++) {
            alphaA = matrixMul(transpose(newColAlpha), A);
            newColAlpha = vectorMul(transpose(alphaA), extractColumn(B, O[i]));
            for(int j = 0; j < alpha.length; j++) {
                alpha[j][i] = newColAlpha[j][0];
            }
        }
    }

    public static void readInput() {
        String[] aArr = nextLineSplit(br);
        String[] bArr = nextLineSplit(br);
        String[] piArr = nextLineSplit(br);
        String[] oArr = nextLineSplit(br);

        A = parseArrayToMatrix(aArr);
        B = parseArrayToMatrix(bArr);
        pi = parseArrayToMatrix(piArr);
        O = parseArrayToVector(oArr);
    }

    public static double alpha(double[][] a, double[][] b, double[][] alpha) {
        double[][] alphaA;
        for (int i = 1; i < O.length; i++) {
            alphaA = matrixMul(transpose(alpha), a);
            alpha = vectorMul(transpose(alphaA), extractColumn(b, O[i]));
        }
        printMatrix(alpha);
        return sumMatrix(alpha);
    }

    public static double[][] vectorMul(double[][] a, double[][] b){
        double[][] res = new double[a.length][1];
        for(int i = 0; i < a.length; i++) {
            res[i][0] = a[i][0] * b[i][0];
        }
        return res;
    }

    public static double[][] extractColumn(double[][] m, int colNo){
        double[][] res = new double[m.length][1];
        for(int i = 0; i < m.length; i++) {
            res[i][0] = m[i][colNo];
        }
        return res;
    }

    public static double[][] transpose(double[][] trans) {
        double[][] res = new double[trans[0].length][trans.length];
        for (int i = 0; i < trans.length; i++) {
            for (int j = 0; j < trans[0].length; j++) {
                res[j][i] = trans[i][j];
            }
        }
        return res;
    }
    public static double sumMatrix(double[][] matrix) {
        double sum = 0;
        for (int i = 0; i < matrix.length; i++) {
            sum += matrix[i][0];
        }
        return sum;
    }
    public static void printMatrix(double[][] m){
        for(int i = 0; i < m.length; i++) {
            for( int j = 0; j < m[0].length; j++) {
                System.out.printf("%2f ", m[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void printMatrix(int[][] m) {
        for(int i = 0; i < m.length; i++) {
            for( int j = 0; j < m[0].length; j++) {
                System.out.print(m[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void printVector(int[] v) {
        for(int i = 0; i < v.length; i++) {
            System.out.print(v[i] + " ");
        }
        System.out.println();
    }


}
