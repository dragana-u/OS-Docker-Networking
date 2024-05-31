import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class TemperatureMonitor {
    public static void main(String[] args) {
        double lowTemperature = Double.parseDouble(System.getenv("LOW_TEMPERATURE"));
        double mediumTemperature = Double.parseDouble(System.getenv("MEDIUM_TEMPERATURE"));
        double highTemperature = Double.parseDouble(System.getenv("HIGH_TEMPERATURE"));

        while (true) {
            try {
                PrintWriter pw = new PrintWriter(new FileOutputStream("temperaturelevel.txt", true));
                Scanner sc = new Scanner(new File("temperatures.txt"));
                Thread.sleep(60000);
                double sum = 0;
                for (int i = 0; i < 5; i++) {
                    sum += sc.nextInt();
                }
                double average = sum / 5.0;
                if (average >= lowTemperature && average < mediumTemperature) {
                    pw.write("Low\n");
                }
                if (average >= mediumTemperature && average < highTemperature) {
                    pw.write("Medium\n");
                }
                if (average >= highTemperature) {
                    pw.write("High\n");
                }
                pw.flush();
                pw.close();
            } catch (FileNotFoundException | InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
