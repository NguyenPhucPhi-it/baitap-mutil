package model;

import java.sql.Date;

public class HocVien {
private String studentID, studentName;
private Date dob;//Ngày sinh của học viên (dùng để xác định độ tuổi, có thể ảnh hưởng đến mức học phí).
private String address;//Địa chỉ của học viên (dùng để gửi thông báo, biên lai, hóa đơn).
private String phoneNumber;
private String email;
private String courseList;//(khoa ngoai den courseID)danh sách khóa học mà học vên đangư ký
private boolean Gender;
public HocVien() {
	
}
public HocVien(String studentID, String studentName, Date dob, String address, String phoneNumber, String email,
		String courseList, boolean gender) {

	this.studentID = studentID;
	this.studentName = studentName;
	this.dob = dob;
	this.address = address;
	this.phoneNumber = phoneNumber;
	this.email = email;
	this.courseList = courseList;
	Gender = gender;
}
public String getStudentID() {
	return studentID;
}
public void setStudentID(String studentID) {
	this.studentID = studentID;
}
public String getStudentName() {
	return studentName;
}
public void setStudentName(String studentName) {
	this.studentName = studentName;
}
public Date getDob() {
    return dob;
}
public void setDob(Date dob) {
    this.dob = dob;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public String getPhoneNumber() {
	return phoneNumber;
}
public void setPhoneNumber(String phoneNumber) {
	this.phoneNumber = phoneNumber;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getCourseList() {
	return courseList;
}
public void setCourseList(String courseList) {
	this.courseList = courseList;
}
public boolean isGender() {
	return Gender;
}
public void setGender(Boolean  gender) {
	Gender = gender;
}

}
