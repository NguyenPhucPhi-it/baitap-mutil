package model;

import java.util.Date;

public class HocPhiVaThanhToan {
private String tuitionAndPaymentID;
private float 	amountPaid;//: Số tiền đã thanh toán.
private float totalFee;//: Tổng học phí của học viên (có thể thay đổi tùy thuộc vào số lượng khóa học và các ưu đãi, giảm giá).
private String	paymentStatus;//: Trạng thái thanh toán (chưa thanh toán, đã thanh toán, thanh toán một phần, v.v.).
private Date paymentDate;//: Ngày thanh toán.
private float discount;//: Mức giảm giá áp dụng (nếu có).
private String paymentMethod;//: Phương thức thanh toán (tiền mặt, chuyển khoản, thẻ tín dụng, v.v.).
private String studentID;
private float missing;// còn thiếu
public HocPhiVaThanhToan() {	
}
public HocPhiVaThanhToan(String tuitionAndPaymentID, float amountPaid, float totalFee, String paymentStatus,
		Date paymentDate, float discount, String paymentMethod, String studentID, float missing) {	
	this.tuitionAndPaymentID = tuitionAndPaymentID;
	this.amountPaid = amountPaid;
	this.totalFee = totalFee;
	this.paymentStatus = paymentStatus;
	this.paymentDate = paymentDate;
	this.discount = discount;
	this.paymentMethod = paymentMethod;
	this.studentID = studentID;
	this.missing = missing;
}
public String getTuitionAndPaymentID() {
	return tuitionAndPaymentID;
}
public void setTuitionAndPaymentID(String tuitionAndPaymentID) {
	this.tuitionAndPaymentID = tuitionAndPaymentID;
}
public float getAmountPaid() {
	return amountPaid;
}
public void setAmountPaid(float amountPaid) {
	this.amountPaid = amountPaid;
}
public float getTotalFee() {
	return totalFee;
}
public void setTotalFee(float totalFee) {
	this.totalFee = totalFee;
}
public String getPaymentStatus() {
	return paymentStatus;
}
public void setPaymentStatus(String paymentStatus) {
	this.paymentStatus = paymentStatus;
}
public Date getPaymentDate() {
	return paymentDate;
}
public void setPaymentDate(Date paymentDate) {
	this.paymentDate = paymentDate;
}
public float getDiscount() {
	return discount;
}
public void setDiscount(float discount) {
	this.discount = discount;
}
public String getPaymentMethod() {
	return paymentMethod;
}
public void setPaymentMethod(String paymentMethod) {
	this.paymentMethod = paymentMethod;
}
public String getStudentID() {
	return studentID;
}
public void setStudentID(String studentID) {
	this.studentID = studentID;
}
public float getMissing() {
	return missing;
}
public void setMissing(float missing) {
	this.missing = missing;
}

}