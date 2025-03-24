package Week4;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Lab7 {
	private static final String TEP_NGUON = "source.txt";
	private static final String TEP_DAU_RA = "output.txt";
	private static final int KICH_THUOC_CHUNK = 1024 * 1024;

	public static void main(String[] args) {
		File tepNguon = new File(TEP_NGUON);
		long kichThuocTep = tepNguon.length();
		int soLuongPhan = (int) Math.ceil((double) kichThuocTep / KICH_THUOC_CHUNK);
		ExecutorService executor = Executors.newFixedThreadPool(soLuongPhan);
		for (int i = 0; i < soLuongPhan; i++) {
			final int chiMucPhan = i;
			executor.execute(() -> docVaGhiPhan(chiMucPhan, kichThuocTep));
		}
		executor.shutdown();
		while (!executor.isTerminated()) {
		}

		System.out.println("Đọc tệp hoàn tất");
	}

	private static void docVaGhiPhan(int chiMucPhan, long kichThuocTep) {
		try (RandomAccessFile tep = new RandomAccessFile(TEP_NGUON, "r")) {
			long viTriBatDau = (long) chiMucPhan * KICH_THUOC_CHUNK;
			tep.seek(viTriBatDau);
			byte[] boDem = new byte[KICH_THUOC_CHUNK];
			int soByteDoc = tep.read(boDem);
			synchronized (Lab7.class) {
				try (FileOutputStream fos = new FileOutputStream(TEP_DAU_RA, true)) {
					fos.write(boDem, 0, soByteDoc);
				}
			}

		} catch (IOException e) {
			System.err.println("Lỗi khi đọc phần " + chiMucPhan);
		}
	}
}
