// Written by Sahand Zarrinkoub and Shahin Saleh

package hmm1;
import hmm0.MainHmm0;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MainHmm1 extends MainHmm0 {
    public static float[][] A;
    static float[][] B;
    static float[][] pi;
    public static int[] O;
    public static BufferedReader br;

    public static void main(String[] args) {
        br = new BufferedReader(new InputStreamReader(System.in));
        readInput();

        float[][] alpha1 = vectorMul(transpose(pi), extractColumn(B, O[0]));
        float ans = alpha(A, B, alpha1);
        System.out.println(ans);
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

    public static float alpha(float[][] a, float[][] b, float[][] alpha) {
        float[][] alphaA;
        for (int i = 1; i < O.length; i++) {
            alphaA = MainHmm0.matrixMul(transpose(alpha), a);
            alpha = vectorMul(transpose(alphaA), extractColumn(b, O[i]));
        }
        return sumMatrix(alpha);
    }

    public static float[][] vectorMul(float[][] a, float[][] b){
        float[][] res = new float[a.length][1];
        for(int i = 0; i < a.length; i++) {
            res[i][0] = a[i][0] * b[i][0];
        }
        return res;
    }

    public static float[][] extractColumn(float[][] m, int colNo){
        float[][] res = new float[m.length][1];
        for(int i = 0; i < m.length; i++) {
            res[i][0] = m[i][colNo];
        }
        return res;
    }

    public static float[][] transpose(float[][] trans) {
        float[][] res = new float[trans[0].length][trans.length];
        for (int i = 0; i < trans.length; i++) {
            for (int j = 0; j < trans[0].length; j++) {
                res[j][i] = trans[i][j];
            }
        }
        return res;
    }
    public static float sumMatrix(float[][] matrix) {
        float sum = 0;
        for (int i = 0; i < matrix.length; i++) {
            sum += matrix[i][0];
        }
        return sum;
    }
    public void printMatrix(float[][] m){
        for(int i = 0; i < m.length; i++) {
            for( int j = 0; j < m[0].length; j++) {
                System.out.print(m[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void printVector(int[] v) {
        for(int i = 0; i < v.length; i++) {
            System.out.print(v[i] + " ");
        }
        System.out.println();
    }


}
