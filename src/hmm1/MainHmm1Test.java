package hmm1;
import hmm0.MainHmm0;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;

class MainHmm1Test {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void main() {
    }

    @Test
    void readInput() {
        MainHmm1 main = new MainHmm1();

        String fileName = "src/hmm1/samples/hmm2_01.in";

        FileInputStream is = null;

        try {
            is = new FileInputStream(fileName);
            System.setIn(is);
            main.main(new String[] {});

        } catch (IOException e){
            e.printStackTrace();
        }
    }
    void printMatrix(float[][] m){
        for(int i = 0; i < m.length; i++) {
            for( int j = 0; j < m[0].length; j++) {
                System.out.print(m[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    void printVector(int[] v) {
        for(int i = 0; i < v.length; i++) {
            System.out.print(v[i] + " ");
        }
        System.out.println();
    }
    public float[][] transpose(float[][] trans) {
        float[][] res = new float[trans[0].length][trans.length];
        for (int i = 0; i < trans.length; i++) {
            for (int j = 0; j < trans[0].length; j++) {
                res[j][i] = trans[i][j];
            }
        }
        return res;
    }
}