package Week4;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class Lab2 {
	public static void main(String[] args) {
		String fileDich = "output.txt";
		// Sử dụng try-with-resources để đảm bảo đóng luồng tự động
		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				FileWriter fw = new FileWriter(fileDich)) {

			System.out.println("Nhập nội dung (gõ 'exit' để thoát):");

			String dong;
			while (!(dong = br.readLine()).equalsIgnoreCase("exit")) {
				// Ghi nội dung vào file + xuống dòng
				fw.write(dong + System.lineSeparator());
			}

			System.out.println("Đã lưu");
		} catch (IOException e) {
			System.out.println("Lỗi");
		}
	}
}
