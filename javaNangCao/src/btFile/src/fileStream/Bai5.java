package fileStream;

import java.io.File;

public class Bai5 {
    public static void main(String[] args) {
        // Xác định thư mục cần liệt kê file
        String directoryPath = "D:\\ontapopp\\btFile"; // Thay đổi đường dẫn này thành thư mục  muốn liệt kê

        // Tạo đối tượng File cho thư mục
        File directory = new File(directoryPath);

        //  Kiểm tra nếu thư mục tồn tại và là thư mục
        if (directory.exists() && directory.isDirectory()) {
            //  Liệt kê tất cả các file và thư mục con trong thư mục
            File[] files = directory.listFiles();

            // Kiểm tra nếu thư mục có chứa file hoặc thư mục con
            if (files != null) {
                System.out.println("Danh sách các file và thư mục trong thư mục '" + directoryPath + "':");
                for (File file : files) {
                    //In ra tên file hoặc thư mục
                    if (file.isFile()) {
                        System.out.println("File: " + file.getName());
                    } else if (file.isDirectory()) {
                        System.out.println("Thư mục: " + file.getName());
                    }
                }
            } else {
                System.out.println("Không thể liệt kê các file trong thư mục.");
            }
        } else {
            System.out.println("Thư mục không tồn tại hoặc không phải thư mục.");
        }
    }
}
