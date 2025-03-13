package fileStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;

public class Bai1 {
    public static void main(String[] args) {
        FileInputStream fs = null;
        FileOutputStream fos = null;
        File inputFile = new File("int.txt");
        try {
            fs = new FileInputStream("int.txt");
            fos = new FileOutputStream("out.txt");

            int data;
            while ((data = fs.read()) != -1) {
                fos.write(data);
            }         

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(" Error: " + e.getMessage());
            e.printStackTrace();
        } finally {         
            try {
                if (fs != null) {
                    fs.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
