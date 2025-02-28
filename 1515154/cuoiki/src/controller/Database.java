 package controller;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import model.HocPhiVaThanhToan;
import model.HocVien;
import model.KhoaHoc;
import model.LopHoc;

public class Database {
private static final String DB_SeverName="DESKTOP-ALACJE3\\SQLEXPRESS";
private static final String DB_Login="Sa";
private static final String DB_Password="21022006";
private static final String DB_databaseName = "QLHPJAVA";
private Connection conn;
private String studentID;
private String tuitionAndPaymentID;
private Connection connection;

public Database() {
	this.conn=getConnection();
}


public static Connection getConnection() {
	
    try {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String DB_URL = "jdbc:sqlserver://" + DB_SeverName + 
                       ":1433;databaseName=" + DB_databaseName + 
                       ";encrypt=true;trustServerCertificate=true";
        return DriverManager.getConnection(DB_URL, DB_Login, DB_Password);
    } catch (Exception e) {
        e.printStackTrace();
       
    return null;

}
}


public ArrayList<HocVien> getListHocVien() {
    ArrayList<HocVien> hocvien = new ArrayList<HocVien>();  // Khởi tạo danh sách HocVien
    
    String sql = "SELECT * FROM HocVien";  // Truy vấn SQL
    try {
        // Chuẩn bị câu lệnh SQL
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();  // Thực thi truy vấn và nhận kết quả

        while (rs.next()) {
            HocVien hocviens = new HocVien();  // Tạo đối tượng HocVien
            
            // Lấy dữ liệu từ ResultSet và set vào đối tượng hocviens
            hocviens.setStudentID(rs.getString("studentID"));
            hocviens.setStudentName(rs.getString("StudentName"));
            hocviens.setDob(rs.getDate("dob"));
            hocviens.setAddress(rs.getString("address"));
            hocviens.setPhoneNumber(rs.getString("phoneNumber"));
            hocviens.setEmail(rs.getString("email"));
            hocviens.setCourseList(rs.getString("courseList"));
            hocviens.setGender(rs.getBoolean("Gender"));
            
            hocvien.add(hocviens);  // Thêm hocviens vào danh sách
        }

        rs.close();  // Đóng ResultSet
        ps.close();  // Đóng PreparedStatement
    } catch (SQLException e) {
        e.printStackTrace();  // Xử lý lỗi nếu có
    }
    
    return hocvien;  // Trả về danh sách học viên
}
public boolean addHocVien(HocVien newStudent) {
    String sql = "INSERT INTO HocVien (studentID, studentName, dob, address, phoneNumber, email, courseList, Gender) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, newStudent.getStudentID());
        ps.setString(2, newStudent.getStudentName());
        ps.setDate(3, new java.sql.Date(newStudent.getDob().getTime()));
        ps.setString(4, newStudent.getAddress());
        ps.setString(5, newStudent.getPhoneNumber());
        ps.setString(6, newStudent.getEmail());
        ps.setString(7, newStudent.getCourseList());
        ps.setBoolean(8, newStudent.isGender());
        int rowsAffected = ps.executeUpdate();
        return rowsAffected > 0;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

public ArrayList<HocVien> getAllHocVien() {
    ArrayList<HocVien> students = new ArrayList<>();  
    return students;
}

public boolean deleteHocVien(String studentID) {
    String query = "DELETE FROM HocVien WHERE studentID = ?";
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setString(1, studentID);
        int rowsAffected = stmt.executeUpdate(); 
        return rowsAffected > 0; 
    } catch (SQLException e) {
        e.printStackTrace();
        return false; 
    }
}

public boolean updateStudent(HocVien student) {
    String sql = "UPDATE HocVien SET studentName = ?, dob = ?, address = ?, phoneNumber = ?, email = ?, courseList = ?, gender = ? WHERE studentID = ?";
    try (PreparedStatement ps=conn.prepareStatement(sql)) {
    	ps.setString(1, student.getStudentName());
    	ps.setDate(2, student.getDob());
    	ps.setString(3, student.getAddress());
    	ps.setString(4, student.getPhoneNumber());
    	ps.setString(5, student.getEmail());
    	ps.setString(6, student.getCourseList());
    	ps.setBoolean(7, student.isGender());
    	ps.setString(8, student.getStudentID());

        int rowsAffected = ps.executeUpdate();
        return rowsAffected > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

public ArrayList<HocVien> searchHocVien(String searchTerm) {
    ArrayList<HocVien> result = new ArrayList<>();
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try {
        conn = getConnection();
        String query = "SELECT * FROM HocVien WHERE studentID LIKE ? OR studentName LIKE ?";
        stmt = conn.prepareStatement(query);
        String searchPattern = "%" + searchTerm + "%";
        stmt.setString(1, searchPattern);
        stmt.setString(2, searchPattern);        
        rs = stmt.executeQuery();
        while (rs.next()) {
            HocVien student = new HocVien();
            student.setStudentID(rs.getString("studentID"));
            student.setStudentName(rs.getString("studentName"));
            student.setDob(rs.getDate("dob"));
            student.setAddress(rs.getString("address"));
            student.setPhoneNumber(rs.getString("phoneNumber"));
            student.setEmail(rs.getString("Email"));
            student.setCourseList(rs.getString("courseList"));
            student.setGender(rs.getBoolean("Gender"));
            result.add(student);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return result;
}

public HocVien getStudentById(String studentID) {
    HocVien student = null;
    String sql = "SELECT * FROM students WHERE studentID = ?";
    try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, studentID);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            student = new HocVien();
            student.setStudentID(rs.getString("studentID"));
            student.setStudentName(rs.getString("studentName"));
            student.setDob(rs.getDate("dob"));
            student.setPhoneNumber(rs.getString("phoneNumber"));
            student.setAddress(rs.getString("address"));
            student.setEmail(rs.getString("email"));
            student.setCourseList(rs.getString("courseList"));
            student.setGender(rs.getBoolean("gender"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return student;
}
public ArrayList<KhoaHoc> getListKhoaHoc() {
    ArrayList<KhoaHoc> courses = new ArrayList<>(); 
    String sql = "SELECT*FROM KhoaHoc;"; 
    try (PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {          
        while (rs.next()) {
            KhoaHoc khoaHoc = new KhoaHoc();              
            khoaHoc.setCourseID(rs.getString("courseID"));
            khoaHoc.setCourseName(rs.getString("courseName"));
            khoaHoc.setStartDate(rs.getDate("startDate"));
            khoaHoc.setEndDate(rs.getDate("endDate"));
            khoaHoc.setTuitionFee(rs.getFloat("tuitionFee"));           
            courses.add(khoaHoc);
        }
    } catch (SQLException e) {
        e.printStackTrace();  
    }
    return courses;  
}
public boolean updateCourse(KhoaHoc course) {
	 String sql = "UPDATE KhoaHoc SET courseName = ?, startDate = ?, endDate = ?, tuitionFee = ? WHERE courseID = ?";	    
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {	 
	        ps.setString(1, course.getCourseName());
	        ps.setDate(2, new java.sql.Date(course.getStartDate().getTime()));
	        ps.setDate(3, new java.sql.Date(course.getEndDate().getTime()));
	        ps.setFloat(4, course.getTuitionFee());
	        ps.setString(5, course.getCourseID());	      
	        int rowsAffected = ps.executeUpdate();	       
	        return rowsAffected > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();  
	        return false;
	    }
}
    public boolean deleteKhoaHoc(String courseID) {
        String sql = "DELETE FROM KhoaHoc WHERE CourseID = ?";              
        try (Connection conn = getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {             
            ps.setString(1, courseID);  
            int rowsAffected = ps.executeUpdate();             
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();  
        }       
        return false;  
    }
	public boolean addCourse(KhoaHoc newCourse) {
		String sql = "INSERT INTO KhoaHoc (courseID, courseName, startDate, endDate, tuitionFee) VALUES (?, ?, ?, ?, ?)"; 	    
	    try (Connection conn = getConnection(); 
	         PreparedStatement ps = conn.prepareStatement(sql)) {	        	       
	        ps.setString(1, newCourse.getCourseID());
	        ps.setString(2, newCourse.getCourseName());
	        ps.setDate(3, new java.sql.Date(newCourse.getStartDate().getTime()));  
	        ps.setDate(4, new java.sql.Date(newCourse.getEndDate().getTime()));   
	        ps.setFloat(5, newCourse.getTuitionFee());	        
	        int rowsAffected = ps.executeUpdate();	        	        
	        return rowsAffected > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();  
	    }	    
	    return false;  
	}
	public ArrayList<KhoaHoc> searchCourseByCourseID(String courseID) {
	    ArrayList<KhoaHoc> result = new ArrayList<>();
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    try {
	        conn = getConnection(); 
	        String query = "SELECT * FROM KhoaHoc WHERE courseID LIKE ?";
	        stmt = conn.prepareStatement(query);
	        stmt.setString(1, "%" + courseID + "%");  
	        rs = stmt.executeQuery();	       
	        while (rs.next()) {
	            KhoaHoc course = new KhoaHoc();
	            course.setCourseID(rs.getString("courseID"));
	            course.setCourseName(rs.getString("courseName"));
	            course.setStartDate(rs.getDate("startDate"));
	            course.setEndDate(rs.getDate("endDate"));
	            course.setTuitionFee(rs.getFloat("tuitionFee"));
	            result.add(course);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {	      
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return result;
	}
	public ArrayList<LopHoc> getListClasses() {
	    ArrayList<LopHoc> classes = new ArrayList<>();
	    String sql = "SELECT * FROM LopHoc"; 
	    try (PreparedStatement ps = conn.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {
	        while (rs.next()) {
	            LopHoc lopHoc = new LopHoc();
	            lopHoc.setClassID(rs.getString("classID"));
	            lopHoc.setCourseID(rs.getString("courseID"));
	            lopHoc.setStudentID(rs.getString("studentID"));
	            lopHoc.setNgayDangKy(rs.getDate("ngayDangKy"));
	            lopHoc.setTinhTrang(rs.getString("tinhTrang"));
	            lopHoc.setClassName(rs.getString("className"));
	            classes.add(lopHoc);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return classes;
	}
	public ArrayList<LopHoc> searchClassByClassID(String classID) {
	    ArrayList<LopHoc> result = new ArrayList<>();
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;

	    try {
	        conn = getConnection();
	        // Query tìm lớp học theo classID
	        String query = "SELECT * FROM LopHoc WHERE classID LIKE ?";
	        stmt = conn.prepareStatement(query);
	        stmt.setString(1, "%" + classID + "%");  // Sử dụng LIKE để tìm kiếm gần đúng
	        rs = stmt.executeQuery();
	        while (rs.next()) {
	            LopHoc classItem = new LopHoc();
	            classItem.setClassID(rs.getString("classID"));
	            classItem.setCourseID(rs.getString("courseID"));
	            classItem.setStudentID(rs.getString("studentID"));
	            classItem.setNgayDangKy(rs.getDate("NgayDangKy"));
	            classItem.setTinhTrang(rs.getString("tinhTrang"));
	            classItem.setClassName(rs.getString("className"));
	            result.add(classItem);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return result;
	}
	public boolean updateClass(LopHoc updatedClass) {
	    String query = "UPDATE LopHoc SET courseID = ?, studentID = ?, ngayDangKy = ?, tinhTrang = ?, className = ? WHERE classID = ?";
	    
	    try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
	        stmt.setString(1, updatedClass.getCourseID());
	        stmt.setString(2, updatedClass.getStudentID());
	        stmt.setDate(3, (Date) updatedClass.getNgayDangKy());
	        stmt.setString(4, updatedClass.getTinhTrang());
	        stmt.setString(5, updatedClass.getClassName());
	        stmt.setString(6, updatedClass.getClassID());

	        int rowsAffected = stmt.executeUpdate();
	        return rowsAffected > 0; // Trả về true nếu có dòng bị ảnh hưởng (cập nhật thành công)
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	public boolean addClass(LopHoc newClass) {
		String sql = "INSERT INTO LopHoc (classID, courseID, studentID, ngayDangKy, tinhTrang, className)" +"VALUES (?, ?, ?, ?, ?, ?)";

	    try (PreparedStatement ps = conn.prepareStatement(sql)) {	      
	        ps.setString(1, newClass.getClassID());
	        ps.setString(2, newClass.getCourseID());
	        ps.setString(3, newClass.getStudentID());
	        ps.setDate(4, new java.sql.Date(newClass.getNgayDangKy().getTime()));  
	        ps.setString(5, newClass.getTinhTrang());
	        ps.setString(6, newClass.getClassName());	       
	        int rowsAffected = ps.executeUpdate();
	        return rowsAffected > 0;  
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	        return false;  	    
	}
	public boolean deleteClass(String classID) {
	    String sql = "DELETE FROM LopHoc WHERE classID = ?";  
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {      
	        ps.setString(1, classID);  	      
	        int rowsAffected = ps.executeUpdate();  
	        return rowsAffected > 0;  
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false; 
	    }
	}
	public boolean updateClass(String classID, String updatedCourseID, String updatedStudentID,
            String updatedNgayDangKy, String updatedTinhTrang, String updatedClassName) {
String sql = "UPDATE LopHoc SET courseID = ?, studentID = ?, ngayDangKy = ?, tinhTrang = ?, className = ? WHERE classID = ?";

try (PreparedStatement ps = connection.prepareStatement(sql)) {
ps.setString(1, updatedCourseID);
ps.setString(2, updatedStudentID);
ps.setString(3, updatedNgayDangKy);
ps.setString(4, updatedTinhTrang);
ps.setString(5, updatedClassName);
ps.setString(6, classID);  
int rowsAffected = ps.executeUpdate();
return rowsAffected > 0;
} catch (SQLException e) {
e.printStackTrace();
return false;  
}
}
	public ArrayList<HocPhiVaThanhToan> getListTuition() {
	    ArrayList<HocPhiVaThanhToan> tuitions = new ArrayList<>();  
	    String sql = "SELECT * FROM HocPhiVaThanhToan"; 
	    try (PreparedStatement ps = conn.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {  	               
	        while (rs.next()) {
	            HocPhiVaThanhToan tuition = new HocPhiVaThanhToan();
	            tuition.setTuitionAndPaymentID(rs.getString("tuitionAndPaymentID"));
	            tuition.setAmountPaid(rs.getFloat("amountPaid"));
	            tuition.setTotalFee(rs.getFloat("totalFee"));
	            tuition.setDiscount(rs.getFloat("discount"));
	            tuition.setPaymentDate(rs.getDate("paymentDate"));
	            tuition.setPaymentMethod(rs.getString("paymentMethod"));
	            tuition.setPaymentStatus(rs.getString("paymentStatus"));
	            tuition.setStudentID(rs.getString("studentID"));
	            tuition.setMissing(rs.getFloat("missing"));
	            tuitions.add(tuition); 
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();  
	    }
	    return tuitions;  
	}
	 public boolean addTuition(HocPhiVaThanhToan newtuition) {	   
		    String sql = "INSERT INTO HocPhiVaThanhToan(tuitionAndPaymentID, amountPaid, totalFee, discount, paymentDate, paymentMethod, paymentStatus, studentID)"+"VALUES(?,?,?,?,?,?,?,?)";
		    try {		      
		        PreparedStatement ps = conn.prepareStatement(sql);		        
		        ps.setString(1, newtuition.getTuitionAndPaymentID()); 
		        ps.setFloat(2, newtuition.getAmountPaid());           
		        ps.setFloat(3, newtuition.getTotalFee());              
		        ps.setFloat(4, (float) newtuition.getDiscount()); 
		        ps.setDate(5, new java.sql.Date(newtuition.getPaymentDate().getTime()));          		      
		        ps.setString(6, newtuition.getPaymentMethod());      
		        ps.setString(7, newtuition.getPaymentStatus());     
		        ps.setString(8, newtuition.getStudentID());   
		        ps.setFloat(9, newtuition.getMissing());  
		        int rowsAffected = ps.executeUpdate();
		        return ps.executeUpdate() > 0;		        
		    } catch (SQLException e) {
		       
		        e.printStackTrace();
		        return false;
		    }
		}
	 public boolean deleteTuition(String tuitionAndPaymentID) {
		    String sql = "DELETE FROM HocPhiVaThanhToan WHERE tuitionAndPaymentID=?";
		    try {
		        PreparedStatement ps = conn.prepareStatement(sql);
		        ps.setString(1, tuitionAndPaymentID);
		        return ps.executeUpdate() > 0;  
		    } catch (Exception e) {
		        e.printStackTrace();
		        return false;
		    }
		}

	public KhoaHoc getCourseByID(String courseID) {
		// TODO Auto-generated method stub
		return null;
	}
	public ArrayList<HocPhiVaThanhToan> searchTuitionByTuitionID(String tuitionID) {
	    ArrayList<HocPhiVaThanhToan> tuitionList = new ArrayList<>();
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    try {
	        conn = getConnection();	      
	        String query = "SELECT * FROM HocPhiVaThanhToan WHERE tuitionAndPaymentID LIKE ?";	       	     
	        stmt = conn.prepareStatement(query);	        	      
	        stmt.setString(1, "%" + tuitionID + "%");	        	    
	        rs = stmt.executeQuery();	   
	        while (rs.next()) {
	            HocPhiVaThanhToan tuition = new HocPhiVaThanhToan();
	            tuition.setTuitionAndPaymentID(rs.getString("tuitionAndPaymentID"));
	            tuition.setAmountPaid(rs.getFloat("amountPaid"));
	            tuition.setTotalFee(rs.getFloat("totalFee"));
	            tuition.setDiscount(rs.getFloat("discount"));
	            tuition.setPaymentDate(rs.getDate("paymentDate"));
	            tuition.setPaymentMethod(rs.getString("paymentMethod"));
	            tuition.setPaymentStatus(rs.getString("paymentStatus"));
	            tuition.setStudentID(rs.getString("studentID"));	
	            tuition.setMissing(rs.getFloat("missing"));	
	            tuitionList.add(tuition);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {	       
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace(); 
	        }
	    }
	    return tuitionList;
	}
	public float getTotalTuitionForStudent(String studentID) {
	    float totalTuition = 0;
	    // Câu lệnh SQL đã được sửa
	    String sql = "SELECT SUM(hpt.totalFee) AS totalTuition " +
	                 "FROM HocPhiVaThanhToan hpt " +
	                 "JOIN LopHoc dkh ON hpt.studentID = kh.studentID " +
	                 "WHERE kh.studentID = 01";
	    
	    try (Connection conn = getConnection(); 
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setString(1, studentID);  // Truyền tham số studentID vào câu lệnh SQL
	        ResultSet rs = stmt.executeQuery();
	        
	        if (rs.next()) {
	            totalTuition = rs.getFloat("totalTuition");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return totalTuition;
	}


	public boolean updateTuition(HocPhiVaThanhToan updatedTuition) {
	    String query = "UPDATE HocPhiVaThanhToan SET amountPaid = ?, totalFee = ?, discount = ?, paymentDate = ?, paymentMethod = ?, paymentStatus = ?, studentID = ? WHERE tuitionAndPaymentID = ?";

	    try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
	        stmt.setFloat(1, updatedTuition.getAmountPaid());
	        stmt.setFloat(2, updatedTuition.getTotalFee());
	        stmt.setFloat(3, updatedTuition.getDiscount());
	        stmt.setDate(4, (Date) updatedTuition.getPaymentDate());
	        stmt.setString(5, updatedTuition.getPaymentMethod());
	        stmt.setString(6, updatedTuition.getPaymentStatus());
	        stmt.setString(7, updatedTuition.getStudentID());
	        stmt.setString(8, updatedTuition.getTuitionAndPaymentID());
	        int rowsAffected = stmt.executeUpdate();
	        return rowsAffected > 0; // Trả về true nếu có dòng bị ảnh hưởng (cập nhật thành công)
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	



	




	

	}

	