package model;

import java.util.Date;

public class LopHoc {
private String classID;
private  String courseID;
private String studentID;
private Date ngayDangKy;
private String tinhTrang;
private String className;
public LopHoc() {
	
}
public LopHoc(String classID, String courseID, String studentID, Date ngayDangKy, String tinhTrang, String className) {
	
	this.classID = classID;
	this.courseID = courseID;
	this.studentID = studentID;
	this.ngayDangKy = ngayDangKy;
	this.tinhTrang = tinhTrang;
	this.className = className;
}
public String getClassID() {
	return classID;
}
public void setClassID(String classID) {
	this.classID = classID;
}
public String getCourseID() {
	return courseID;
}
public void setCourseID(String courseID) {
	this.courseID = courseID;
}
public String getStudentID() {
	return studentID;
}
public void setStudentID(String studentID) {
	this.studentID = studentID;
}
public Date getNgayDangKy() {
	return ngayDangKy;
}
public void setNgayDangKy(Date ngayDangKy) {
	this.ngayDangKy = ngayDangKy;
}
public String getTinhTrang() {
	return tinhTrang;
}
public void setTinhTrang(String tinhTrang) {
	this.tinhTrang = tinhTrang;
}
public String getClassName() {
	return className;
}
public void setClassName(String className) {
	this.className = className;
}

}