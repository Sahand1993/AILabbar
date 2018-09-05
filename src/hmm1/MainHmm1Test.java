package hmm1;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

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

        String fileName = "/Users/sahandzarrinkoub/IdeaProjects/AIlab1/src/hmm1/samples/hmm2_01.in";

        FileInputStream is = null;

        try {
            is = new FileInputStream(fileName);
            System.setIn(is);
            main.main(new String[] {});
            main.readInput();

            printMatrix(main.A);

            printMatrix(main.B);

            printMatrix(main.pi);

            printVector(main.O);


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
}