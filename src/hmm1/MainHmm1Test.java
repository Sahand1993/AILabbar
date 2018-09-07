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

        String fileName = "/Users/ishahin/IdeaProjects/AILabbar/src/hmm1/samples/hmm2_01.in";

        FileInputStream is = null;

        try {
            is = new FileInputStream(fileName);
            System.setIn(is);
            main.main(new String[] {});
            main.readInput();

            System.out.println("This is the A matrix");
            printMatrix(main.A);

            System.out.println("This is the B matrix");
            printMatrix(main.B);

            System.out.println("This is the pi matrix");
            printMatrix(transpose(main.pi));

            System.out.println("This is the Emission matrix");
            printVector(main.O);

            /*
            TESTING
             */
            float[][] alphaA;
            float[][] alpha = main.vectorMul(transpose(main.pi), main.extractColumn(main.B, main.O[0]));
            System.out.println("This is ALpha before the loop");
            printMatrix(alpha);
            for (int i = 1; i < main.O.length; i++) {

                alphaA = MainHmm0.matrixMul(transpose(alpha), main.A);
                printMatrix(alphaA);
                System.out.println();
                alpha = main.vectorMul(transpose(alphaA), main.extractColumn(main.B, main.O[i]));
                printMatrix(alpha);
                System.out.println();

                //System.out.println("This is Alpha in the loop");
                //printMatrix(alpha);
            }
            System.out.println("This is Alpha after the loop");
            printMatrix(alpha);
            float ans = main.sumMatrix(alpha);
            System.out.println("This should be the answer");
            System.out.println(ans);



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