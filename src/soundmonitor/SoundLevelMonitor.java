

import java.io.*;
import java.util.Arrays;

public class SoundLevelMonitor {
    public static void main(String[] args) {
        int low = Integer.parseInt(System.getenv("LOW_SOUNDLEVEL"));
        int medium = Integer.parseInt(System.getenv("MEDIUM_SOUNDLEVEL"));
        int high = Integer.parseInt(System.getenv("HIGH_SOUNDLEVEL"));
        while (true){
            try {
                PrintWriter pw = new PrintWriter(new FileOutputStream("noisepollution.txt", true));
                BufferedReader br = new BufferedReader(new FileReader("soundlevel.txt"));
                int[] numbers = Arrays.stream(br.readLine().split("\\s+")).mapToInt(Integer::parseInt).toArray();
                int average = (int) Arrays.stream(numbers).average().getAsDouble();

                if (average >= low && average < medium){
                    pw.write("Low ");
                }
                if (average >= medium && average < high){
                    pw.write("Medium ");
                }
                if (average >= high){
                    pw.write("High ");
                }
                pw.flush();
                Thread.sleep(30000);
            } catch (InterruptedException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
