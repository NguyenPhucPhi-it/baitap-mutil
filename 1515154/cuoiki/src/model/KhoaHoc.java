package model;

import java.util.Date;

public class KhoaHoc {
private String courseID, courseName;//courseID-->studentID
private Date startDate,endDate;//ngay bat dau/ ket thuc
private float tuitionFee;// học phí của khóa học

public KhoaHoc() {
	
}

public KhoaHoc(String courseID, String courseName, Date startDate, Date endDate, float tuitionFee) {

	this.courseID = courseID;
	this.courseName = courseName;
	this.startDate = startDate;
	this.endDate = endDate;
	this.tuitionFee = tuitionFee;
}

public String getCourseID() {
	return courseID;
}

public void setCourseID(String courseID) {
	this.courseID = courseID;
}

public String getCourseName() {
	return courseName;
}

public void setCourseName(String courseName) {
	this.courseName = courseName;
}

public Date getStartDate() {
	return startDate;
}

public void setStartDate(Date startDate) {
	this.startDate = startDate;
}

public Date getEndDate() {
	return endDate;
}

public void setEndDate(Date endDate) {
	this.endDate = endDate;
}

public float getTuitionFee() {
	return tuitionFee;
}

public void setTuitionFee(float tuitionFee) {
	this.tuitionFee = tuitionFee;
}

}
