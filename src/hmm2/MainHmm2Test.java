package hmm2;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class MainHmm2Test {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void main() {
        MainHmm2 main = new MainHmm2();

        String fileName = "src/hmm2/samples/hmm3_01.in";

        FileInputStream is = null;

        try {
            is = new FileInputStream(fileName);
            System.setIn(is);
            main.main(new String[] {});

            main.printMatrix(main.delta);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}