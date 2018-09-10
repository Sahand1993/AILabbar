package hmm2;

import hmm1.MainHmm1;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MainHmm2 extends MainHmm1 {

    static float[][] delta;
    static int[][] deltaIndex;

    public static void main(String[] args){
        br = new BufferedReader(new InputStreamReader(System.in));
        readInput();

        delta = new float[A.length][O.length];
        deltaIndex = new int[A.length][O.length];

        fillDelta();

        findSequence(); // This should output the most probably sequence of states

    }

    /**
     * Fills delta and deltaIndex matrices with probabilities.
     */
    public static void fillDelta(){
        firstColDelta();

        float[] probs;
        // For each timestep t
        for(int t = 1; t < O.length; t++) {
            // For each possible state at t
            for(int i = 0; i < A.length; i++) {
                probs = new float[A.length];
                // For each possible state at t - 1
                for(int j = 0; j < A.length; j++) {
                    probs[j] = delta[j][t-1] * A[j][i] * B[i][O[t]];
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
            delta[0][i] = pi[0][i] * B[i][O[0]];
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
    public static void findSequence(){
        
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
