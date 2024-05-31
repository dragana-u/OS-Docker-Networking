//package sensor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class TemperatureSensor {
    public static void main(String[] args) {
        while (true) {
            try {
                PrintWriter pw = new PrintWriter("temperatures.txt");
                for (int i = 0; i < 5; i++) {
                    int num = (int) (Math.random() * 45 + 5);
                    pw.write(String.valueOf(num));
                    pw.write("\n");
                }
                pw.flush();
                Thread.sleep(30000);
            } catch (FileNotFoundException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
