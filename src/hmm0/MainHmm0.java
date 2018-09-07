package hmm0;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;

public class MainHmm0 {

    public static void main(String[] args){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
//            for(int l = 0; l < 2; l++) {
                String inputTrans = br.readLine();
                String inputEmiss = br.readLine();
                String inputPi = br.readLine();

                //System.out.printf("Got %s as inputEmiss.\n", inputEmiss.toString());
                //System.out.printf("Got %s as inputPi.\n", inputPi.toString());

                float[][] transM;
                float[][] emissM;
                float[][] pi;

                String[] inputTransElems = inputTrans.split(" ");
                String[] inputEmissElems = inputEmiss.split(" ");
                String[] inputPiElems = inputPi.split(" ");

                /*

                System.out.println(Arrays.toString(inputTransElems));
                System.out.println(Arrays.toString(inputEmissElems));
                System.out.println(Arrays.toString(inputPiElems));

*/
                int rowsTrans = Integer.parseInt(inputTransElems[0]);
                int colsTrans = Integer.parseInt(inputTransElems[1]);

                int rowsEmiss = Integer.parseInt(inputEmissElems[0]);
                int colsEmiss = Integer.parseInt(inputEmissElems[1]);

                int rowsPi = Integer.parseInt(inputPiElems[0]);
                int colsPi = Integer.parseInt(inputPiElems[1]);

                transM = new float[rowsTrans][colsTrans];
                emissM = new float[rowsEmiss][colsEmiss];
                pi = new float[rowsPi][colsPi];

                for (int i = 0; i < rowsTrans; i++) {
                    for (int j = 0; j < colsTrans; j++) {
                        transM[i][j] = Float.parseFloat(inputTransElems[2 + i * colsTrans + j]);
                    }
                }

                for (int i = 0; i < rowsEmiss; i++) {
                    for (int j = 0; j < colsEmiss; j++) {
                        emissM[i][j] = Float.parseFloat(inputEmissElems[2 + i * colsEmiss + j]);
                    }
                }

                for (int i = 0; i < rowsPi; i++) {
                    for (int j = 0; j < colsPi; j++) {
                        pi[i][j] = Float.parseFloat(inputPiElems[2 + i * colsPi + j]);
                    }
                }

                float[][] res = matrixMul(pi, transM);
                res = matrixMul(res, emissM);

                DecimalFormat df = new DecimalFormat("#.#");
                df.setRoundingMode(RoundingMode.HALF_UP);

                System.out.printf("%d %d ", res.length, res[0].length);
                for (int i = 0; i < res.length; i++) {
                    for (int j = 0; j < res[0].length; j++) {
                        System.out.printf("%f ", res[i][j]);
                    }
                }
//            System.out.print("\n");
           // }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String[] nextLineSplit(BufferedReader br){
        try {
            String inputString = br.readLine();
            String[] inputElems = inputString.split(" ");
            System.out.println(Arrays.toString(inputElems));
            return inputElems;
        } catch(IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static float[][] parseArrayToMatrix(String[] arr) {
        int rows = Integer.parseInt(arr[0]);
        int cols = Integer.parseInt(arr[1]);

        float[][] res = new float[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                res[i][j] = Float.parseFloat(arr[2 + i * cols + j]);
            }
        }

        return res;
    }

    public static int[] parseArrayToVector(String[] arr) {
        int noOfVals = Integer.parseInt(arr[0]);

        int[] res = new int[noOfVals];

        for(int i = 0; i < noOfVals; i++) {
            res[i] = Integer.parseInt(arr[1 + i]);
        }

        return res;
    }

    public static float[][] matrixMul(float[][] a, float[][] b) {
        float[][] res = new float[a.length][b[0].length];
        for(int i = 0; i < a.length; i++) {

            for(int j = 0; j < b[0].length; j++ ) {
                for (int k = 0; k < a[0].length; k++) {
                    res[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return res;
    }
}
