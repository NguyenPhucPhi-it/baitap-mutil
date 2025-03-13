package fileStream;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Bai4 {
    public static void main(String[] args) {
        String fileName = "numbers.dat"; 
        int[] numbersToWrite = {10, 20, 30, 40, 50};
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(fileName))) {
            for (int num : numbersToWrite) {
                dos.writeInt(num);
            }
            System.out.println("Đã ghi các số nguyên vào file: " + fileName);
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi vào file: " + e.getMessage());
            e.printStackTrace();
        }

        try (DataInputStream dis = new DataInputStream(new FileInputStream(fileName))) {
            System.out.println("Đọc các số nguyên từ file:");
            while (dis.available() > 0) {
                int number = dis.readInt(); 
                System.out.println(number);
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc từ file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
