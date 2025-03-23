package Week4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Lab3 {
	public static void main(String[] args) {
		String tenFile = "a.txt";

		// Sử dụng try-with-resources để tự động đóng luồng khi hoàn thành
		try (BufferedReader br = new BufferedReader(new FileReader(tenFile))) {
			int soDong = 0;
			String dong;
			while ((dong = br.readLine()) != null) {
				soDong += 1;
			}

			System.out.println("Tổng số dòng là " + soDong);
		} catch (IOException e) {
			System.err.println("Lỗi");
		}
	}
}
