package Week4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Lab4 {
	public static void main(String[] args) {
		String tenFile = "a.txt";
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(tenFile))) {
			int[] danhSachSo = { 10, 20, 30, 40, 50 };
			for (int so : danhSachSo) {
				bw.write(so + "\n");
			}
			System.out.println("Đã xon");
		} catch (IOException e) {
			System.err.println("Lỗi");
		}
		try (BufferedReader br = new BufferedReader(new FileReader(tenFile))) {
			System.out.println("Số on file là ");

			String dong;
			while ((dong = br.readLine()) != null) {
				System.out.println(Integer.parseInt(dong));
			}
		} catch (IOException e) {
			System.err.println("Lỗi khi đọc file: " + e.getMessage());
		}
	}
}
