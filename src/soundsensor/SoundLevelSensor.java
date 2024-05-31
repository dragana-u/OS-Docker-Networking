
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class SoundLevelSensor {
    public static void main(String[] args) {
        while(true){
            try {
                PrintWriter pw = new PrintWriter("soundlevel.txt");
                for (int i = 0; i < 10; i++) {
                    Random r = new Random();
                    int num = r.nextInt(40,100);
                    pw.write(num + " ");
                }
                pw.flush();
                Thread.sleep(20000);
            } catch (FileNotFoundException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
