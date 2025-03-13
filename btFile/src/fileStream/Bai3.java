package fileStream;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Bai3 {
    public static void main(String[] args) {
        File inputFile = new File("int.txt");
        if (!inputFile.exists()) {
            System.out.println("File không tồn tại.");
            return;
        }

        BufferedReader reader = null;
        int lineCount = 0;

        try {
            reader = new BufferedReader(new FileReader(inputFile));

            String line;
            while ((line = reader.readLine()) != null) {
                lineCount++;  
            }
            System.out.println("Số dòng trong file: " + lineCount);

        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
