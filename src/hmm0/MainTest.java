package hmm0;

import java.io.*;

class MainTest {

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void main() {
        String fileName = "/Users/sahandzarrinkoub/IdeaProjects/AIlab1/src/hmm0/samples/sample_00.in";

        /*
        BufferedReader br = null;
        FileReader fr = null;
        */
        FileInputStream is = null;

        try {
            is = new FileInputStream(fileName);
            /*
            fr = new FileReader(fileName);
            br = new BufferedReader(fr);
            */

            String transM;
            String emissM;
            String pi;

            System.setIn(is);

            MainHmm0.main(new String[] {});



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @org.junit.jupiter.api.Test
    void matrixMul() {
    }
}