package Week4;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Lab1 {
	public static void main(String[] args) {
		String fileNguon = "a.txt";
		String fileDich = "b.txt";

		try (FileInputStream fileDoc = new FileInputStream(fileNguon);
				FileOutputStream fileGhi = new FileOutputStream(fileDich);) {
			byte[] boDem = new byte[1024]; // mảng lưu giá trị đọc
			int soByteDoc; // số byte đọc được
			while ((soByteDoc = fileDoc.read(boDem)) != -1) {
				fileGhi.write(boDem, 0, soByteDoc);
			}
			System.out.println("Đã sao chép từ " + fileNguon + " sang " + fileDich);
		} catch (IOException e) {
			System.out.println("Lỗi");
		}
	}
}
