package hmm3;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class MainHmm3Test {

    @Test
    void main() {
        MainHmm3 main = new MainHmm3();

        String fileName = "src/hmm3/samples/hmm4_02.in";

        FileInputStream is = null;

        try {
            is = new FileInputStream(fileName);
            System.setIn(is);
            main.main(new String[] {});

//            main.printMatrix(main.pi);
  //          main.printMatrix(main.A);
    //        main.printMatrix(main.B);
  //          main.printMatrix(main.alpha);
//            main.printMatrix(main.beta);
 //           main.printMatrix(main.gamma);
   //         main.printMatrix(main.digamma);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}