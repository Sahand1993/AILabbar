package hmm1;
import hmm0.MainHmm0;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MainHmm1 extends MainHmm0 {
    float[][] A;
    float[][] B;
    float[][] pi;
    int[] O;
    static BufferedReader br;

    public static void main(String[] args){
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    public void readInput() {
        String[] aArr = nextLineSplit(br);
        String[] bArr = nextLineSplit(br);
        String[] piArr = nextLineSplit(br);
        String[] oArr = nextLineSplit(br);

        A = parseArrayToMatrix(aArr);
        B = parseArrayToMatrix(bArr);
        pi = parseArrayToMatrix(piArr);
        O = parseArrayToVector(oArr);
    }

    public float[][] alfa(float[][] A, float[][] B, float[][] prev) {
        for(int i = 0; i < ) {
//TODO: Continue here
        }
    }

    public float[][] vectorMul(float[][] a, float[][] b){
        float[][] res = new float[a.length][1];
        for(int i = 0; i < a.length; i++) {
            res[i][0] = a[i][0] * b[i][0];
        }
        return res;
    }

    public float[][] extractColumn(float[][] m, int colNo){
        float[][] res = new float[m.length][1];
        for(int i = 0; i < m.length; i++) {
            res[i][0] = m[i][colNo];
        }
        return res;
    }
}
