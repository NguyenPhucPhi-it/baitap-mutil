package fileStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;

public class Bai2 {
    public static void main(String[] args) {
        FileInputStream fs = null;
        FileOutputStream fos = null;
        File inputFile = new File("int.txt");

        try {         
            fs = new FileInputStream("int.txt");
            fos = new FileOutputStream("out.txt");
            int code = fs.read();
            if (code != -1) {
                char c = (char) code;
                System.out.println("First character read: " + c);
            }
            StringBuilder contentBuilder = new StringBuilder();
            int data;
            while ((data = fs.read()) != -1) {
                contentBuilder.append((char) data);
            }
            String content = contentBuilder.toString();
            System.out.println("File content:\n" + content);
            fos.write(content.getBytes());

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("I/O Error: " + e.getMessage());
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
