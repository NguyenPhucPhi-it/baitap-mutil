package Week4;

import java.io.File;

public class Lab5 {
	public static void main(String[] args) {
		String duongDanThuMuc = "C:\\Users\\Admin\\Desktop\\javaproject\\JavaNangcao\\src\\Week4";
		File thuMuc = new File(duongDanThuMuc);
		File[] danhSachTep = thuMuc.listFiles();
		System.out.println("Danh sách tệp và thư mục trong:" + duongDanThuMuc);
		for (File tep : danhSachTep) {
			if (tep.isFile()) {
				System.out.println("Tệp: " + tep.getName());
			} else if (tep.isDirectory()) {
				System.out.println("Thư mục: " + tep.getName());
			}
		}
	}
}
