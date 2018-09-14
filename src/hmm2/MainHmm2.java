package hmm2;

import hmm1.MainHmm1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainHmm2 extends MainHmm1 {

    static float[][] delta;
    static int[][] deltaIndex;

    public static void main(String[] args){
        br = new BufferedReader(new InputStreamReader(System.in));
        readInput();

        delta = new float[A.length][O.length];
        deltaIndex = new int[A.length][O.length];

        fillDelta();

        System.out.println(findSequence()); // This should output the most probable sequence of states
    }

    /**
     * Fills delta and deltaIndex matrices with probabilities.
     */
    public static void fillDelta(){
        firstColDelta();

        float[] probs;
        // For each timestep t
        for(int t = 1; t < O.length; t++) {
      //      System.out.println(t);
            // For each possible state at t
            for(int i = 0; i < A.length; i++) {
         //       System.out.println(" " + i);
                probs = new float[A.length];
                // For each possible state at t - 1
                for(int j = 0; j < A.length; j++) {
                    probs[j] = delta[j][t - 1] * A[j][i] * B[i][O[t]];
//                    System.out.printf("  %f * %f * %f = %f\n", delta[j][t - 1], A[j][i], B[i][O[t]], probs[j]);
                }
                FloatInt floatInt = max(probs);
                delta[i][t] = floatInt.getMax();
                deltaIndex[i][t] = floatInt.getArgmax();
            }
        }
    }

    /**
     *  Fills the first column of delta
     */
    public static void firstColDelta(){
        for(int i = 0; i < A.length; i++) {
            delta[i][0] = pi[0][i] * B[i][O[0]];
        }
    }

    /**
     * Return max of array
     */
    public static FloatInt max(float[] arr){
        float max = arr[0];
        int argmax = 0;
        for(int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
                argmax = i;
            }
        }
        return new FloatInt(max, argmax);
    }

    /**
     * Finds the most likely path by backtracking in deltaIndex.
     */
    public static String findSequence(){
        String seq = "";
        float maxProb = 0;
        int iMax = -1;
        for (int i = 0; i < A.length; i++) {
            float tempProb = delta[i][delta[0].length - 1];
            if (tempProb > maxProb) {
                maxProb = tempProb;
                iMax = i;
            }
        }
        if (maxProb > 0) {
            seq = backTrack(iMax, delta[0].length - 1);
        }
        return seq;
    }

    /**
     *  Returns the sequence of states from backtracking as a String.
     * @param i is the state we're backtracking from.
     * @param t is the timestep where we have state i.
     * @return the entire backtracking sequence
     */
    public static String backTrack(int i, int t){
        if (t == 0) {
            return "" + i + " ";
        }
        return backTrack(deltaIndex[i][t], t - 1) + i + " ";
    }

    private static class FloatInt {
        private float max;
        private int argmax;
        public FloatInt(float max, int argmax) {
            this.max = max;
            this.argmax = argmax;
        }

        public float getMax() {
            return max;
        }

        public void setMax(float max) {
            this.max = max;
        }

        public int getArgmax() {
            return argmax;
        }

        public void setArgmax(int argmax) {
            this.argmax = argmax;
        }
    }
}
