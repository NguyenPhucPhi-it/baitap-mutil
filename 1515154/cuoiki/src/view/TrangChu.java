package view;

import controller.Database;
import model.HocPhiVaThanhToan;
import model.HocVien;
import model.KhoaHoc;
import model.LopHoc;
import net.miginfocom.layout.DimConstraint;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

public class TrangChu extends JFrame {
    private JPanel mainPanel;
    private JPanel contentPanel;
    private Database dao;
    private JTable studentTable; 
    private DefaultTableModel tableModel;  
	private JTable courseTable;
	private JTable tuitionTable;
	  private ChartPanel chartPanel;
	public TrangChu() {
		setTitle("quản lý học phí");
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize(); 
		int width = screenSize.width;
		int height = screenSize.height;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(0, 0, width, height);
		setLocationRelativeTo(null);
		mainPanel = new JPanel();
		mainPanel.setBackground(Color.WHITE);
		setContentPane(mainPanel);
		mainPanel.setLayout(new BorderLayout());

		JPanel menuPanel = new JPanel();
		menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
		menuPanel.setBackground(new Color(50, 50, 50));

		menuPanel.setPreferredSize(new Dimension(160, height));

		JLabel nameLabel = new JLabel("ADMIN");
		nameLabel.setForeground(Color.WHITE);
		nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		nameLabel.setFont(new Font("Arial", Font.BOLD, 25));
		JLabel roleLabel = new JLabel("TTDT");
		roleLabel.setForeground(Color.WHITE);
		roleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		roleLabel.setFont(new Font("Arial", Font.PLAIN, 14));

		menuPanel.add(Box.createVerticalStrut(20));
		menuPanel.add(nameLabel);
		menuPanel.add(roleLabel);
		menuPanel.add(Box.createVerticalStrut(20));

		String[] menuItems = { "STUDENT MANAGEMENT", "COURSE MANAGEMENT", "CLASS MANAGEMENT", "TUITION MANAGEMENT","Statistical"};
		for (String item : menuItems) {
			JButton btn = new JButton(item);
			btn.setAlignmentX(Component.CENTER_ALIGNMENT);
			btn.setForeground(Color.WHITE);
			btn.setMaximumSize(new Dimension(180, 40));
			btn.setBackground(new Color(34, 139, 34));
			btn.setFocusPainted(false);
			btn.addActionListener(new ActionListener() {
				private Object tinhTrang;
				private HocPhiVaThanhToan[] hocPhiList;
				private Database db;
				private JTable tuitionTable;
				private JTable classTable;
				private Component parent;
				private JFreeChart chart;

				@Override
				public void actionPerformed(ActionEvent e) {
					updateContent(item);
				}

				private void updateContent(String menuItem) {
					contentPanel.removeAll();
					switch (menuItem) {

					case "STUDENT MANAGEMENT":
						showStudentManagement();
						break;
					case "COURSE MANAGEMENT":
						showCourseManagement();
						break;
					case "CLASS MANAGEMENT":
						showClassManagement();
						break;
					case "TUITION MANAGEMENT":
						showTuitionManagement();
						break;		
					case "Statistical":
						
						new Dashboard().setVisible(true);
					}
					contentPanel.revalidate();
					contentPanel.repaint();
				}				
				private void showTuitionManagement() {
				    contentPanel.setLayout(new BorderLayout());
				    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));				    
				    buttonPanel.setBackground(Color.RED);
				    JButton addButton = new JButton("Add");
				    addButton.setBackground(Color.CYAN); 
				    JButton deleteButton = new JButton("Delete");
				    deleteButton.setBackground(Color.CYAN); 
				    JButton updateButton = new JButton("Update");
				    updateButton.setBackground(Color.CYAN); 
				    JButton timkiemButton = new JButton("Search");
				    timkiemButton.setBackground(Color.CYAN); 
				    JButton dsButton = new JButton("Export list");
				    dsButton.setBackground(Color.CYAN);
				    addButton.addActionListener(e -> openAddTuition());
				    deleteButton.addActionListener(e -> deleteTuition());
				    updateButton.addActionListener(e -> updateTuition());
				    timkiemButton.addActionListener(e -> timkiemTuition());
				    dsButton.addActionListener(e -> dsTuition());
				    buttonPanel.add(addButton);
				    buttonPanel.add(deleteButton);
				    buttonPanel.add(updateButton);
				    buttonPanel.add(timkiemButton);
				    buttonPanel.add(dsButton);

				    String[] columnNames = { "tuitionAndPaymentID", "amountPaid", "totalFee", "discount", "paymentDate", "paymentMethod", "paymentStatus", "studentID","missing" };
				    DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
				    tuitionTable = new JTable(tableModel);
				    tuitionTable.setDefaultEditor(Object.class, null);
				    Font ttableFont = new Font("Arial", Font.PLAIN, 16);
				    tuitionTable.setFont(ttableFont);
				    tuitionTable.setRowHeight(30);
				    JTableHeader header = tuitionTable.getTableHeader();
				    header.setFont(new Font("Arial", Font.BOLD, 18));
				    JScrollPane scrollPane = new JScrollPane(tuitionTable);				 
				    tuitionTable.addMouseListener(new MouseAdapter() {
				        @Override
				        public void mouseClicked(MouseEvent e) {
				            int selectedRow = tuitionTable.rowAtPoint(e.getPoint());
				            if (selectedRow != -1) {				       
				                showTuitionDetails(selectedRow);
				            }
				        }
				    });
				    contentPanel.add(buttonPanel, BorderLayout.NORTH);
				    contentPanel.add(scrollPane, BorderLayout.CENTER);
				    loadTuitionData();
				}			
				private void showTuitionDetails(int selectedRow) {
				    String tuitionAndPaymentID = (String) tuitionTable.getValueAt(selectedRow, 0);
				    String amountPaid = tuitionTable.getValueAt(selectedRow, 1).toString();
				    String totalFee = tuitionTable.getValueAt(selectedRow, 2).toString();
				    String discount = tuitionTable.getValueAt(selectedRow, 3).toString();
				    String paymentDate = tuitionTable.getValueAt(selectedRow, 4).toString();
				    String paymentMethod = tuitionTable.getValueAt(selectedRow, 5).toString();
				    String paymentStatus = tuitionTable.getValueAt(selectedRow, 6).toString();
				    String studentID = tuitionTable.getValueAt(selectedRow, 7).toString();		
				    String missing = tuitionTable.getValueAt(selectedRow, 8).toString();		
				    JDialog detailsDialog = new JDialog();
				    detailsDialog.setTitle("Tuition Details");
				    detailsDialog.setSize(400, 300);
				    detailsDialog.setLocationRelativeTo(contentPanel);

				    JPanel detailsPanel = new JPanel(new GridLayout(9, 2));
				    detailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
				    detailsPanel.add(new JLabel("Tuition ID:"));
				    detailsPanel.add(new JLabel(tuitionAndPaymentID));
				    detailsPanel.add(new JLabel("Amount Paid:"));
				    detailsPanel.add(new JLabel(amountPaid));
				    detailsPanel.add(new JLabel("Total Fee:"));
				    detailsPanel.add(new JLabel(totalFee));
				    detailsPanel.add(new JLabel("Discount:"));
				    detailsPanel.add(new JLabel(discount));
				    detailsPanel.add(new JLabel("Payment Date:"));
				    detailsPanel.add(new JLabel(paymentDate));
				    detailsPanel.add(new JLabel("Payment Method:"));
				    detailsPanel.add(new JLabel(paymentMethod));
				    detailsPanel.add(new JLabel("Payment Status:"));
				    detailsPanel.add(new JLabel(paymentStatus));
				    detailsPanel.add(new JLabel("Student ID:"));
				    detailsPanel.add(new JLabel(studentID));
				    detailsPanel.add(new JLabel("Mising"));
				    detailsPanel.add(new JLabel(missing));
				    detailsDialog.add(detailsPanel, BorderLayout.CENTER);
				    JButton closeButton = new JButton("Close");
				    closeButton.addActionListener(e -> detailsDialog.dispose());
				    JPanel buttonPanel = new JPanel();
				    buttonPanel.add(closeButton);
				    detailsDialog.add(buttonPanel, BorderLayout.SOUTH);
				    detailsDialog.setVisible(true);
				}

				private void dsTuition() {				 
				    DefaultTableModel model = (DefaultTableModel) tuitionTable.getModel();
				    int rowCount = model.getRowCount();
				    int columnCount = model.getColumnCount();
				    JFileChooser fileChooser = new JFileChooser();
				    fileChooser.setDialogTitle("Save As CSV");
				    fileChooser.setSelectedFile(new File("tuition_list.csv"));
				    int result = fileChooser.showSaveDialog(null);
				    if (result == JFileChooser.APPROVE_OPTION) {
				        File file = fileChooser.getSelectedFile();
				        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {				      
				            for (int i = 0; i < columnCount; i++) {
				                writer.write(model.getColumnName(i));
				                if (i < columnCount - 1) {
				                    writer.write(",");
				                }
				            }
				            writer.newLine();			         
				            for (int i = 0; i < rowCount; i++) {
				                for (int j = 0; j < columnCount; j++) {
				                    writer.write(model.getValueAt(i, j).toString());
				                    if (j < columnCount - 1) {
				                        writer.write(",");
				                    }
				                }
				                writer.newLine();
				            }
				            JOptionPane.showMessageDialog(null, "Danh sách đã được xuất thành công!");

				        } catch (IOException e) {
				            JOptionPane.showMessageDialog(null, "Lỗi khi xuất danh sách: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
				        }
				    }
				}
				private void timkiemTuition() {			  
				    JTextField searchField = new JTextField(20);				  
				    JPanel searchPanel = new JPanel();
				    searchPanel.add(new JLabel("Search by Tuition ID:"));
				    searchPanel.add(searchField);				 
				    int option = JOptionPane.showConfirmDialog(null, searchPanel, "Search Tuition", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);			
				    if (option == JOptionPane.OK_OPTION) {
				        String searchTerm = searchField.getText().trim();				    
				        if (!searchTerm.isEmpty()) {
				            filterTuitionData(searchTerm);
				        } else {				      
				            JOptionPane.showMessageDialog(null, "Please enter a tuition ID.");
				        }
				    }
				}
					private void filterTuitionData(String searchTerm) {
				    DefaultTableModel model = (DefaultTableModel) tuitionTable.getModel();
				    model.setRowCount(0); 				 
				    Database db = new Database();
				    ArrayList<HocPhiVaThanhToan> matchingTuition = db.searchTuitionByTuitionID(searchTerm);  
				    for (HocPhiVaThanhToan tuition : matchingTuition) {
				        model.addRow(new Object[] {
				            tuition.getTuitionAndPaymentID(),
				            tuition.getAmountPaid(),
				            tuition.getTotalFee(),
				            tuition.getDiscount(),
				            tuition.getPaymentDate(),
				            tuition.getPaymentMethod(),
				            tuition.getPaymentStatus(),
				            tuition.getStudentID(),
				            tuition.getMissing()
				            
				        });
				    }				   
				    if (matchingTuition.isEmpty()) {
				        JOptionPane.showMessageDialog(null, "No matching tuition records found.");
				    }
				}


					private void updateTuition() {
					    // Lấy dòng đã chọn trong bảng
					    int selectedRow = tuitionTable.getSelectedRow();
					    if (selectedRow == -1) {
					        JOptionPane.showMessageDialog(null, "Please select a tuition record to update.");
					        return;
					    }

					    // Lấy dữ liệu của học phí đã chọn
					    String tuitionAndPaymentID = tuitionTable.getValueAt(selectedRow, 0).toString();
					    String amountPaid = tuitionTable.getValueAt(selectedRow, 1).toString();
					    String totalFee = tuitionTable.getValueAt(selectedRow, 2).toString();
					    String discount = tuitionTable.getValueAt(selectedRow, 3).toString();
					    String paymentDate = tuitionTable.getValueAt(selectedRow, 4).toString();
					    String paymentMethod = tuitionTable.getValueAt(selectedRow, 5).toString();
					    String paymentStatus = tuitionTable.getValueAt(selectedRow, 6).toString();
					    String studentID = tuitionTable.getValueAt(selectedRow, 7).toString();
					    String missing = tuitionTable.getValueAt(selectedRow, 8).toString();
					    // Tạo các trường nhập liệu với dữ liệu hiện tại
					    JTextField tuitionIDField = new JTextField(tuitionAndPaymentID, 20);
					    tuitionIDField.setEditable(false); // Không cho phép thay đổi Tuition ID
					    JTextField amountPaidField = new JTextField(amountPaid, 20);
					    JTextField totalFeeField = new JTextField(totalFee, 20);
					    JTextField discountField = new JTextField(discount, 20);
					    JTextField paymentDateField = new JTextField(paymentDate, 20);
					    JTextField paymentMethodField = new JTextField(paymentMethod, 20);
					    JTextField paymentStatusField = new JTextField(paymentStatus, 20);
					    JTextField studentIDField = new JTextField(studentID, 20);
					    JTextField missingField = new JTextField(missing, 20);
					    JPanel updatePanel = new JPanel(new GridLayout(9, 2));
					    updatePanel.add(new JLabel("Tuition ID:"));
					    updatePanel.add(tuitionIDField);
					    updatePanel.add(new JLabel("Amount Paid:"));
					    updatePanel.add(amountPaidField);
					    updatePanel.add(new JLabel("Total Fee:"));
					    updatePanel.add(totalFeeField);
					    updatePanel.add(new JLabel("Discount:"));
					    updatePanel.add(discountField);
					    updatePanel.add(new JLabel("Payment Date (yyyy-MM-dd):"));
					    updatePanel.add(paymentDateField);
					    updatePanel.add(new JLabel("Payment Method:"));
					    updatePanel.add(paymentMethodField);
					    updatePanel.add(new JLabel("Payment Status:"));
					    updatePanel.add(paymentStatusField);
					    updatePanel.add(new JLabel("Student ID:"));
					    updatePanel.add(studentIDField);
					    updatePanel.add(new JLabel("Missing: "));
					    updatePanel.add(missingField);
					    int option = JOptionPane.showConfirmDialog(null, updatePanel, "Update Tuition", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
					    if (option == JOptionPane.OK_OPTION) {
					        // Lấy dữ liệu từ các trường nhập liệu
					        String newAmountPaid = amountPaidField.getText();
					        String newTotalFee = totalFeeField.getText();
					        String newDiscount = discountField.getText();
					        String newPaymentDate = paymentDateField.getText();
					        String newPaymentMethod = paymentMethodField.getText();
					        String newPaymentStatus = paymentStatusField.getText();
					        String newStudentID = studentIDField.getText();
					        String newMissing = missingField.getText();
					        // Kiểm tra dữ liệu đầu vào
					        if (newAmountPaid.isEmpty() || newTotalFee.isEmpty() || newDiscount.isEmpty() || newPaymentDate.isEmpty() || newPaymentMethod.isEmpty() || newPaymentStatus.isEmpty() || newStudentID.isEmpty()||newMissing.isEmpty()) {
					            JOptionPane.showMessageDialog(null, "Please fill in all fields.");
					            return;
					        }
					        // Chuyển đổi ngày tháng từ chuỗi
					        java.sql.Date sqlPaymentDate = null;
					        try {
					            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
					            java.util.Date parsedDate = sdf.parse(newPaymentDate);
					            sqlPaymentDate = new java.sql.Date(parsedDate.getTime());
					        } catch (java.text.ParseException ex) {
					            JOptionPane.showMessageDialog(null, "Invalid date format. Please use yyyy-mm-dd.");
					            return;
					        }

					        // Cập nhật đối tượng Học phí
					        HocPhiVaThanhToan updatedTuition = new HocPhiVaThanhToan();
					        updatedTuition.setTuitionAndPaymentID(tuitionAndPaymentID); // Không thay đổi TuitionID
					        updatedTuition.setAmountPaid(Float.parseFloat(newAmountPaid));
					        updatedTuition.setTotalFee(Float.parseFloat(newTotalFee));
					        updatedTuition.setDiscount(Float.parseFloat(newDiscount));
					        updatedTuition.setPaymentDate(sqlPaymentDate);
					        updatedTuition.setPaymentMethod(newPaymentMethod);
					        updatedTuition.setPaymentStatus(newPaymentStatus);
					        updatedTuition.setStudentID(newStudentID);
					        updatedTuition.setMissing(Float.parseFloat(newMissing));
					        // Cập nhật vào cơ sở dữ liệu
					        Database db = new Database();
					        boolean success = db.updateTuition(updatedTuition);
					        if (success) {
					            JOptionPane.showMessageDialog(null, "Tuition updated successfully.");
					            loadTuitionData(); // Tải lại dữ liệu bảng sau khi cập nhật
					        } else {
					            JOptionPane.showMessageDialog(null, "Failed to update tuition.");
					        }
					    }
					}


				private Object deleteTuition() {
					int selectedRow = tuitionTable.getSelectedRow();
					if (selectedRow != -1) {
						String tuitionAndPaymentID = (String) tuitionTable.getValueAt(selectedRow, 0);
						int confirm = JOptionPane.showConfirmDialog(contentPanel,
								"Are you sure you want to delete this Tuition?", "Delete Tuition",
								JOptionPane.YES_NO_OPTION);
						if (confirm == JOptionPane.YES_OPTION) {
							Database db = new Database();
							boolean deleted = db.deleteTuition(tuitionAndPaymentID);
							if (deleted) {
								JOptionPane.showMessageDialog(contentPanel, "Tuition deleted successfully.");
								loadTuitionData();
							} else {
								JOptionPane.showMessageDialog(contentPanel, "Failed to delete tuition.");
							}
						}
					} else {
						JOptionPane.showMessageDialog(contentPanel, "Please select a tuition to delete.");
					}
					return selectedRow;
				}		
				private Object openAddTuition() {				   
				    JDialog addTuitionDialog = new JDialog();
				    addTuitionDialog.setTitle("Add New Tuition");
				    addTuitionDialog.setSize(400, 300);
				    addTuitionDialog.setLocationRelativeTo(contentPanel);				    
				    JPanel addPanel = new JPanel(new GridLayout(0, 2, 10, 10)); 
				    addPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 				  
				    JTextField tuitionIDField = new JTextField();
				    JTextField amountPaidField = new JTextField();
				    JTextField totalFeeField = new JTextField();
				    JTextField discountField = new JTextField();
				    JTextField paymentDateField = new JTextField();
				    JTextField paymentMethodField = new JTextField();
				    JTextField paymentStatusField = new JTextField();
				    JTextField studentIDField = new JTextField();	
				    JTextField missingField = new JTextField();
				    addPanel.add(new JLabel("Tuition ID:"));
				    addPanel.add(tuitionIDField);
				    addPanel.add(new JLabel("Amount Paid:"));
				    addPanel.add(amountPaidField);
				    addPanel.add(new JLabel("Total Fee:"));
				    addPanel.add(totalFeeField);
				    addPanel.add(new JLabel("Discount:"));
				    addPanel.add(discountField);
				    addPanel.add(new JLabel("Payment Date:"));
				    addPanel.add(paymentDateField);
				    addPanel.add(new JLabel("Payment Method:"));
				    addPanel.add(paymentMethodField);
				    addPanel.add(new JLabel("Payment Status:"));
				    addPanel.add(paymentStatusField);
				    addPanel.add(new JLabel("Student ID:"));
				    addPanel.add(studentIDField);		
				    addPanel.add(new JLabel("Missing :"));
				    addPanel.add(missingField);
				    addTuitionDialog.add(addPanel, BorderLayout.CENTER);				  
				    JPanel buttonPanel = new JPanel(new FlowLayout());				    
				    JButton okButton = new JButton("OK");
				    okButton.addActionListener(e -> {
				        String tuitionID = tuitionIDField.getText();
				        String amountPaid = amountPaidField.getText();
				        String totalFee = totalFeeField.getText();
				        String discount = discountField.getText();
				        String paymentDate = paymentDateField.getText();
				        String paymentMethod = paymentMethodField.getText();
				        String paymentStatus = paymentStatusField.getText();
				        String studentID = studentIDField.getText();			
				        String missing=missingField.getText();
				        if (tuitionID.isEmpty() || amountPaid.isEmpty() || totalFee.isEmpty() || discount.isEmpty()
				                || paymentDate.isEmpty() || paymentMethod.isEmpty() || paymentStatus.isEmpty() || studentID.isEmpty()||missing.isEmpty()) {
				            JOptionPane.showMessageDialog(addTuitionDialog, "Please fill in all fields.");
				            return;
				        }				        
				        java.sql.Date sqlPaymentDate = null;
				        try {
				            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
				            java.util.Date parsedDate = sdf.parse(paymentDate);
				            sqlPaymentDate = new java.sql.Date(parsedDate.getTime());
				        } catch (java.text.ParseException ex) {
				            JOptionPane.showMessageDialog(addTuitionDialog, "Invalid date format. Please use yyyy-MM-dd.");
				            return;
				        }				
				        HocPhiVaThanhToan newTuition = new HocPhiVaThanhToan();
				        newTuition.setTuitionAndPaymentID(tuitionID);
				        newTuition.setAmountPaid(Float.parseFloat(amountPaid));
				        newTuition.setTotalFee(Float.parseFloat(totalFee));
				        newTuition.setDiscount(Float.parseFloat(discount));
				        newTuition.setPaymentDate(sqlPaymentDate);
				        newTuition.setPaymentMethod(paymentMethod);
				        newTuition.setPaymentStatus(paymentStatus);
				        newTuition.setStudentID(studentID);			
				        newTuition.setMissing(Float.parseFloat(missing));
				        Database db = new Database();
				        boolean success = db.addTuition(newTuition);
				        if (success) {
				            JOptionPane.showMessageDialog(addTuitionDialog, "Tuition added successfully.");
				            loadTuitionData();
				            addTuitionDialog.dispose();  
				        } else {
				            JOptionPane.showMessageDialog(addTuitionDialog, "Failed to add tuition.");
				        }
				    });				
				    JButton cancelButton = new JButton("Cancel");
				    cancelButton.addActionListener(e -> addTuitionDialog.dispose());  				  
				    buttonPanel.add(okButton);
				    buttonPanel.add(cancelButton);			
				    addTuitionDialog.add(buttonPanel, BorderLayout.SOUTH);				   
				    addTuitionDialog.setVisible(true);

				    return addTuitionDialog;
				}
				private void loadTuitionData() {				  
				    DefaultTableModel tableModel = (DefaultTableModel) tuitionTable.getModel();
				    tableModel.setRowCount(0);  				    
				    Database db = new Database();
				    ArrayList<HocPhiVaThanhToan> tuitions = db.getListTuition(); 			
				    for (HocPhiVaThanhToan tuitionData : tuitions) {
				        Object[] row = {
				            tuitionData.getTuitionAndPaymentID(),
				            tuitionData.getAmountPaid(),
				            tuitionData.getTotalFee(),
				            tuitionData.getDiscount(),
				            tuitionData.getPaymentDate(),
				            tuitionData.getPaymentMethod(),
				            tuitionData.getPaymentStatus(),
				            tuitionData.getStudentID(),
				            tuitionData.getMissing()
				        };
				     
				        tableModel.addRow(row);
				    }
				}
				private void showClassManagement() {
				    contentPanel.setLayout(new BorderLayout());
				    JPanel buttonPanel3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
				    buttonPanel3.setBackground(Color.RED);
				    JButton addButton = new JButton("Add");
				    addButton.setBackground(Color.CYAN);                    
				    JButton deleteButton = new JButton("Delete");
				    deleteButton.setBackground(Color.CYAN);  
				    JButton updateButton = new JButton("Update");
				    updateButton.setBackground(Color.CYAN);  
				    JButton timkiemButton = new JButton("Search");
				    timkiemButton.setBackground(Color.CYAN);  				    				   
				    addButton.addActionListener(e -> openAddClass());                    
				    deleteButton.addActionListener(e -> deleteClass());
				    updateButton.addActionListener(e -> updateClass());
				    timkiemButton.addActionListener(e -> timkiemClass()); 				    
				    buttonPanel3.add(addButton);                    
				    buttonPanel3.add(deleteButton);
				    buttonPanel3.add(updateButton);
				    buttonPanel3.add(timkiemButton);
				    String[] columnNames3 = { "classID", "courseID", "studentID", "NgayDangKy", "tinhTrang", "className" };
				    DefaultTableModel tableModel = new DefaultTableModel(columnNames3, 0);
				    courseTable = new JTable(tableModel);
				    courseTable.setDefaultEditor(Object.class, null);
				    Font tableFont = new Font("Arial", Font.PLAIN, 16); 
				    courseTable.setFont(tableFont);
				    courseTable.setRowHeight(30);
				    JTableHeader header = courseTable.getTableHeader();
				    header.setFont(new Font("Arial", Font.BOLD, 18)); 
				    JScrollPane scrollPane3 = new JScrollPane(courseTable);
				    contentPanel.add(buttonPanel3, BorderLayout.NORTH);
				    contentPanel.add(scrollPane3, BorderLayout.CENTER);

				    loadClassData(); 
				}				
				private void timkiemClass() {				
				    JTextField searchField = new JTextField(20);
				    JPanel searchPanel = new JPanel();
				    searchPanel.add(new JLabel("Search by classID:"));
				    searchPanel.add(searchField);				 
				    int option = JOptionPane.showConfirmDialog(null, searchPanel, "Search Class", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);				    
				    if (option == JOptionPane.OK_OPTION) {
				        String searchTerm = searchField.getText().trim();

				        if (!searchTerm.isEmpty()) {
				            searchClass(searchTerm);  
				        } else {
				            JOptionPane.showMessageDialog(null, "Please enter a classID.");
				        }
				    }
				}
				private void searchClass(String searchTerm) {				
				    DefaultTableModel model = (DefaultTableModel) courseTable.getModel();
				    model.setRowCount(0); 

				    Database db = new Database();
				    ArrayList<LopHoc> matchingClasses = db.searchClassByClassID(searchTerm);  			
				    for (LopHoc classItem : matchingClasses) {
				        model.addRow(new Object[] {
				            classItem.getClassID(),
				            classItem.getCourseID(),
				            classItem.getStudentID(),
				            classItem.getNgayDangKy(),
				            classItem.getTinhTrang(),
				            classItem.getClassName()
				        });
				    }				  
				    if (matchingClasses.isEmpty()) {
				        JOptionPane.showMessageDialog(null, "No classes found.");
				    }
				}
				private void updateClass() {
				    // Lấy dòng đã chọn trong bảng
				    int selectedRow = courseTable.getSelectedRow();
				    if (selectedRow == -1) {
				        JOptionPane.showMessageDialog(null, "Please select a class to update.");
				        return;
				    }
				    // Lấy dữ liệu của lớp học đã chọn
				    String classID = courseTable.getValueAt(selectedRow, 0).toString();
				    String courseID = courseTable.getValueAt(selectedRow, 1).toString();
				    String studentID = courseTable.getValueAt(selectedRow, 2).toString();
				    String ngayDangKy = courseTable.getValueAt(selectedRow, 3).toString();
				    String tinhTrang = courseTable.getValueAt(selectedRow, 4).toString();
				    String className = courseTable.getValueAt(selectedRow, 5).toString();

				    // Tạo các trường nhập liệu với dữ liệu hiện tại
				    JTextField classIDField = new JTextField(classID, 20);
				    classIDField.setEditable(false); // Không cho phép thay đổi classID
				    JTextField courseIDField = new JTextField(courseID, 20);
				    JTextField studentIDField = new JTextField(studentID, 20);
				    JTextField ngayDangKyField = new JTextField(ngayDangKy, 20);
				    JTextField tinhTrangField = new JTextField(tinhTrang, 20);
				    JTextField classNameField = new JTextField(className, 20);

				    JPanel updatePanel = new JPanel(new GridLayout(7, 2));
				    updatePanel.add(new JLabel("Class ID:"));
				    updatePanel.add(classIDField);
				    updatePanel.add(new JLabel("Course ID:"));
				    updatePanel.add(courseIDField);
				    updatePanel.add(new JLabel("Student ID:"));
				    updatePanel.add(studentIDField);
				    updatePanel.add(new JLabel("Ngay Dang Ky (yyyy-MM-dd):"));
				    updatePanel.add(ngayDangKyField);
				    updatePanel.add(new JLabel("Tinh Trang:"));
				    updatePanel.add(tinhTrangField);
				    updatePanel.add(new JLabel("Class Name:"));
				    updatePanel.add(classNameField);

				    int option = JOptionPane.showConfirmDialog(null, updatePanel, "Update Class", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
				    if (option == JOptionPane.OK_OPTION) {
				        // Lấy dữ liệu từ các trường nhập liệu
				        String newCourseID = courseIDField.getText();
				        String newStudentID = studentIDField.getText();
				        String newNgayDangKy = ngayDangKyField.getText();
				        String newTinhTrang = tinhTrangField.getText();
				        String newClassName = classNameField.getText();

				        // Kiểm tra dữ liệu đầu vào
				        if (newCourseID.isEmpty() || newStudentID.isEmpty() || newNgayDangKy.isEmpty() || newTinhTrang.isEmpty() || newClassName.isEmpty()) {
				            JOptionPane.showMessageDialog(null, "Please fill in all fields.");
				            return;
				        }

				        // Chuyển đổi ngày tháng từ chuỗi
				        java.sql.Date sqlNgayDangKy = null;
				        try {
				            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
				            java.util.Date parsedDate = sdf.parse(newNgayDangKy);
				            sqlNgayDangKy = new java.sql.Date(parsedDate.getTime());
				        } catch (java.text.ParseException ex) {
				            JOptionPane.showMessageDialog(null, "Invalid date format. Please use yyyy-mm-dd.");
				            return;
				        }

				        // Cập nhật đối tượng lớp học
				        LopHoc updatedClass = new LopHoc();
				        updatedClass.setClassID(classID); // Không thay đổi ClassID
				        updatedClass.setCourseID(newCourseID);
				        updatedClass.setStudentID(newStudentID);
				        updatedClass.setNgayDangKy(sqlNgayDangKy);
				        updatedClass.setTinhTrang(newTinhTrang);
				        updatedClass.setClassName(newClassName);

				        // Cập nhật vào cơ sở dữ liệu
				        Database db = new Database();
				        boolean success = db.updateClass(updatedClass);
				        if (success) {
				            JOptionPane.showMessageDialog(null, "Class updated successfully.");
				            loadClassData(); // Tải lại dữ liệu bảng sau khi cập nhật
				        } else {
				            JOptionPane.showMessageDialog(null, "Failed to update class.");
				        }
				    }
				}

				private Object deleteClass() {
					int selectedRow = courseTable.getSelectedRow();
					if (selectedRow != -1) {

						String classID = (String) courseTable.getValueAt(selectedRow, 0);
						int confirm = JOptionPane.showConfirmDialog(contentPanel,
								"Are you sure you want to delete this class?", "Delete Class",
								JOptionPane.YES_NO_OPTION);

						if (confirm == JOptionPane.YES_OPTION) {
							Database db = new Database();
							boolean deleted = db.deleteClass(classID);

							if (deleted) {
								JOptionPane.showMessageDialog(contentPanel, "Class deleted successfully.");
								loadClassData();
							} else {
								JOptionPane.showMessageDialog(contentPanel, "Failed to delete class.");
							}
						}
					} else {

						JOptionPane.showMessageDialog(contentPanel, "Please select a class to delete.");
					}

					return selectedRow;
				}			
				private void openAddClass() {

					JDialog addClassDialog = new JDialog();
					addClassDialog.setTitle("Add New Class");
					addClassDialog.setSize(400, 300);
					addClassDialog.setLocationRelativeTo(contentPanel);

					JPanel panel = new JPanel();
					panel.setLayout(new GridLayout(7, 2));

					JLabel classIDLabel = new JLabel("Class ID:");
					JTextField classIDField = new JTextField();

					JLabel courseIDLabel = new JLabel("Course ID:");
					JTextField courseIDField = new JTextField();

					JLabel studentIDLabel = new JLabel("Student ID:");
					JTextField studentIDField = new JTextField();

					JLabel ngayDangKyLabel = new JLabel("Ngay Dang Ky (yyyy-mm-dd):");
					JTextField ngayDangKyField = new JTextField();

					JLabel tinhTrangLabel = new JLabel("Tinh Trang:");
					JTextField tinhTrangField = new JTextField();

					JLabel classNameLabel = new JLabel("Class Name:");
					JTextField classNameField = new JTextField();
					panel.add(classIDLabel);
					panel.add(classIDField);
					panel.add(courseIDLabel);
					panel.add(courseIDField);
					panel.add(studentIDLabel);
					panel.add(studentIDField);
					panel.add(ngayDangKyLabel);
					panel.add(ngayDangKyField);
					panel.add(tinhTrangLabel);
					panel.add(tinhTrangField);
					panel.add(classNameLabel);
					panel.add(classNameField);

					JButton okButton = new JButton("OK");
					JButton cancelButton = new JButton("Cancel");
					okButton.addActionListener(e -> {
						String classID = classIDField.getText();
						String courseID = courseIDField.getText();
						String studentID = studentIDField.getText();
						String ngayDangKy = ngayDangKyField.getText();
						String tinhTrang = tinhTrangField.getText();
						String className = classNameField.getText();

						if (classID.isEmpty() || courseID.isEmpty() || studentID.isEmpty() || ngayDangKy.isEmpty()
								|| tinhTrang.isEmpty() || className.isEmpty()) {
							JOptionPane.showMessageDialog(addClassDialog, "Please fill in all fields.");
							return;
						}

						java.sql.Date sqlNgayDangKy = null;
						try {
							java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
							java.util.Date parsedDate = sdf.parse(ngayDangKy);
							sqlNgayDangKy = new java.sql.Date(parsedDate.getTime());
						} catch (java.text.ParseException ex) {
							JOptionPane.showMessageDialog(addClassDialog,
									"Invalid date format. Please use yyyy-mm-dd.");
							return;
						}
						LopHoc newClass = new LopHoc();
						newClass.setClassID(classID);
						newClass.setCourseID(courseID);
						newClass.setStudentID(studentID);
						newClass.setNgayDangKy(sqlNgayDangKy);
						newClass.setTinhTrang(tinhTrang);
						newClass.setClassName(className);

						Database db = new Database();
						boolean success = db.addClass(newClass);
						if (success) {
							JOptionPane.showMessageDialog(addClassDialog, "Class added successfully.");
							loadClassData();
							addClassDialog.dispose();
						} else {
							JOptionPane.showMessageDialog(addClassDialog, "Failed to add class.");
						}
					});

					cancelButton.addActionListener(e -> addClassDialog.dispose());
					panel.add(okButton);
					panel.add(cancelButton);
					addClassDialog.add(panel);
					addClassDialog.setVisible(true);
				}

				private void loadClassData() {
					DefaultTableModel tableModel = (DefaultTableModel) courseTable.getModel();
					tableModel.setRowCount(0);

					Database db = new Database();
					ArrayList<LopHoc> classes = db.getListClasses();
					for (LopHoc classData : classes) {
						Object[] row = { classData.getClassID(), classData.getCourseID(), classData.getStudentID(),
								classData.getNgayDangKy(), classData.getTinhTrang(), classData.getClassName()

						};
						tableModel.addRow(row);
					}

				}

				private void showCourseManagement() {
				    contentPanel.setLayout(new BorderLayout());				
				    JPanel buttonPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
				    buttonPanel2.setBackground(Color.RED);
				    JButton addButton = new JButton("Add");	
				    addButton.setBackground(Color.CYAN);
				    JButton deleteButton = new JButton("Delete");
				    deleteButton.setBackground(Color.CYAN);
				    JButton updateButton = new JButton("Update");
				    updateButton.setBackground(Color.CYAN);
				    JButton timkiemButton = new JButton("Seach");
				    timkiemButton.setBackground(Color.CYAN);
				    addButton.addActionListener(e -> openAddCourse());				
				    deleteButton.addActionListener(e -> deleteCourse());
				    updateButton.addActionListener(e -> updateCourse());
				   timkiemButton.addActionListener(e-> timkiemCourse());
				    				    
				    buttonPanel2.add(addButton);				
				    buttonPanel2.add(deleteButton);
				    buttonPanel2.add(updateButton);	
				    buttonPanel2.add(timkiemButton);	
				    String[] columnNames2 = { "Course ID", "Course Name", "Start Date", "End Date", "Tuition Fee" };				  
				    DefaultTableModel tableModel = new DefaultTableModel(columnNames2, 0);
				    courseTable = new JTable(tableModel);
				    courseTable.setDefaultEditor(Object.class, null); 
				    Font ctableFont = new Font("Arial", Font.PLAIN, 16); 
				    courseTable.setFont(ctableFont);
				    courseTable.setRowHeight(30);
				    JTableHeader header = courseTable.getTableHeader();
				    header.setFont(new Font("Arial", Font.BOLD, 18)); 
				    JScrollPane scrollPane2 = new JScrollPane(courseTable);

				    contentPanel.add(buttonPanel2, BorderLayout.NORTH);
				    contentPanel.add(scrollPane2, BorderLayout.CENTER);				    
				    loadCourseData();
				}
               
				private void timkiemCourse() {				    
				    JTextField searchField = new JTextField(20);
				    JPanel searchPanel = new JPanel();
				    searchPanel.add(new JLabel("Search by Course ID:"));
				    searchPanel.add(searchField);				  
				    int option = JOptionPane.showConfirmDialog(null, searchPanel, "Search Course", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);				    
				    if (option == JOptionPane.OK_OPTION) {
				        String searchTerm = searchField.getText().trim();				       
				        if (!searchTerm.isEmpty()) {
				            searchCourse(searchTerm);
				        } else {
				            JOptionPane.showMessageDialog(null, "Please enter a Course ID.");
				        }
				    }
				}
				private void searchCourse(String searchTerm) {		
				    DefaultTableModel model = (DefaultTableModel) courseTable.getModel();
				    model.setRowCount(0); 
				    Database db = new Database();
				    ArrayList<KhoaHoc> matchingCourses = db.searchCourseByCourseID(searchTerm);				    				
				    for (KhoaHoc course : matchingCourses) {
				        model.addRow(new Object[] {
				            course.getCourseID(),
				            course.getCourseName(),
				            course.getStartDate(),
				            course.getEndDate(),
				            course.getTuitionFee()
				        });
				    }				
				    if (matchingCourses.isEmpty()) {
				        JOptionPane.showMessageDialog(null, "No courses found with the specified Course ID.");
				    }
				}


				private void loadCourseData(DefaultTableModel tableModel) {

				}
				
				private void updateCourse() {
				    // Lấy dòng đã chọn trong bảng
				    int selectedRow = courseTable.getSelectedRow();
				    if (selectedRow == -1) {
				        JOptionPane.showMessageDialog(null, "Please select a course to update.");
				        return;
				    }

				    // Lấy dữ liệu của khóa học đã chọn
				    String courseID = courseTable.getValueAt(selectedRow, 0).toString();
				    String courseName = courseTable.getValueAt(selectedRow, 1).toString();
				    String startDate = courseTable.getValueAt(selectedRow, 2).toString();
				    String endDate = courseTable.getValueAt(selectedRow, 3).toString();
				    String tuitionFee = courseTable.getValueAt(selectedRow, 4).toString();

				    // Tạo các trường nhập liệu với dữ liệu hiện tại
				    JTextField courseNameField = new JTextField(courseName, 20);
				    JTextField startDateField = new JTextField(startDate, 20);
				    JTextField endDateField = new JTextField(endDate, 20);
				    JTextField tuitionFeeField = new JTextField(tuitionFee, 20);

				    JPanel updatePanel = new JPanel(new GridLayout(5, 2));
				    updatePanel.add(new JLabel("Course ID:"));
				    updatePanel.add(new JLabel(courseID)); // Không cho phép thay đổi Course ID
				    updatePanel.add(new JLabel("Course Name:"));
				    updatePanel.add(courseNameField);
				    updatePanel.add(new JLabel("Start Date:"));
				    updatePanel.add(startDateField);
				    updatePanel.add(new JLabel("End Date:"));
				    updatePanel.add(endDateField);
				    updatePanel.add(new JLabel("Tuition Fee:"));
				    updatePanel.add(tuitionFeeField);

				    int option = JOptionPane.showConfirmDialog(null, updatePanel, "Update Course", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
				    if (option == JOptionPane.OK_OPTION) {
				        // Lấy dữ liệu từ các trường nhập liệu
				        String updatedCourseName = courseNameField.getText();
				        String updatedStartDate = startDateField.getText();
				        String updatedEndDate = endDateField.getText();
				        String updatedTuitionFee = tuitionFeeField.getText();

				        // Kiểm tra dữ liệu đầu vào
				        if (updatedCourseName.isEmpty() || updatedStartDate.isEmpty() || updatedEndDate.isEmpty() || updatedTuitionFee.isEmpty()) {
				            JOptionPane.showMessageDialog(null, "Please fill in all fields.");
				            return;
				        }

				        // Chuyển đổi ngày tháng từ chuỗi
				        java.sql.Date sqlStartDate = null;
				        java.sql.Date sqlEndDate = null;
				        try {
				            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
				            java.util.Date parsedStartDate = sdf.parse(updatedStartDate);
				            java.util.Date parsedEndDate = sdf.parse(updatedEndDate);
				            sqlStartDate = new java.sql.Date(parsedStartDate.getTime());
				            sqlEndDate = new java.sql.Date(parsedEndDate.getTime());
				        } catch (java.text.ParseException ex) {
				            JOptionPane.showMessageDialog(null, "Invalid date format. Please use yyyy-mm-dd.");
				            return;
				        }

				        // Cập nhật đối tượng Khóa học
				        KhoaHoc updatedCourse = new KhoaHoc();
				        updatedCourse.setCourseID(courseID); // Không thay đổi Course ID
				        updatedCourse.setCourseName(updatedCourseName);
				        updatedCourse.setStartDate(sqlStartDate);
				        updatedCourse.setEndDate(sqlEndDate);
				        updatedCourse.setTuitionFee(Float.parseFloat(updatedTuitionFee));

				        // Cập nhật vào cơ sở dữ liệu
				        Database db = new Database();
				        boolean success = db.updateCourse(updatedCourse);
				        if (success) {
				            JOptionPane.showMessageDialog(null, "Course updated successfully.");
				            loadCourseData(); // Tải lại dữ liệu bảng sau khi cập nhật
				        } else {
				            JOptionPane.showMessageDialog(null, "Failed to update course.");
				        }
				    }
				}

				private void deleteCourse() {
					int selectedRow = courseTable.getSelectedRow();
					if (selectedRow != -1) {
						String courseID = (String) courseTable.getValueAt(selectedRow, 0);
						int confirm = JOptionPane.showConfirmDialog(contentPanel,
								"Are you sure you want to delete this course?", "Delete Course",
								JOptionPane.YES_NO_OPTION);
						if (confirm == JOptionPane.YES_OPTION) {
							boolean deleted = dao.deleteKhoaHoc(courseID);

							if (deleted) {
								JOptionPane.showMessageDialog(contentPanel, "Course deleted successfully.");
								loadCourseData();
							} else {
								JOptionPane.showMessageDialog(contentPanel, "Failed to delete the course.");
							}
						}
					} else {
						JOptionPane.showMessageDialog(contentPanel, "Please select a course to delete.");
					}
				}

				
				private KhoaHoc openAddCourse() {
					JTextField idField = new JTextField(20);
					JTextField nameField = new JTextField(20);
					JTextField dateField = new JTextField(20);
					JTextField endField = new JTextField(20);
					JTextField feeField = new JTextField(20);

					JPanel panel = new JPanel();
					panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
					panel.add(new JLabel("Course ID"));
					panel.add(idField);
					panel.add(new JLabel("Course Name"));
					panel.add(nameField);
					panel.add(new JLabel("Start Date (yyyy-MM-dd)"));
					panel.add(dateField);
					panel.add(new JLabel("End Date (yyyy-MM-dd)"));
					panel.add(endField);
					panel.add(new JLabel("Tuition Fee"));
					panel.add(feeField);
					int option = JOptionPane.showConfirmDialog(null, panel, "Add Course", JOptionPane.OK_CANCEL_OPTION,
							JOptionPane.PLAIN_MESSAGE);
					if (option == JOptionPane.OK_OPTION) {
						String id = idField.getText();
						String name = nameField.getText();
						String startDateStr = dateField.getText();
						String endDateStr = endField.getText();
						String feeString = feeField.getText();
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						dateFormat.setLenient(false);
						Date start = null;
						Date end = null;
						try {
							start = dateFormat.parse(startDateStr);
							end = dateFormat.parse(endDateStr);
						} catch (ParseException e) {
							JOptionPane.showMessageDialog(null, "Invalid date format. Please use yyyy-MM-dd.");
							return null;
						}
						float fee = 0;
						try {
							fee = (float) Double.parseDouble(feeString);
						} catch (NumberFormatException e) {
							JOptionPane.showMessageDialog(null, "Invalid Tuition Fee. Please enter a valid number.");
							return null;
						}
						KhoaHoc newCourse = new KhoaHoc();
						newCourse.setCourseID(id);
						newCourse.setCourseName(name);
						newCourse.setStartDate(start);
						newCourse.setEndDate(end);
						newCourse.setTuitionFee(fee);
						Database db = new Database();
						boolean added = db.addCourse(newCourse);
						if (added) {
							JOptionPane.showMessageDialog(null, "Course added successfully!");
							loadCourseData((DefaultTableModel) courseTable.getModel());
							return newCourse;
						} else {
							JOptionPane.showMessageDialog(null, "Error adding course.");
						}
					}
					return null;
				}
				private void loadCourseData() {
					DefaultTableModel tableModel = (DefaultTableModel) courseTable.getModel();
					tableModel.setRowCount(0);
					Database db = new Database();
					ArrayList<KhoaHoc> courses = db.getListKhoaHoc();
					for (KhoaHoc course : courses) {
						Object[] row = { course.getCourseID(), course.getCourseName(), course.getStartDate(),
								course.getEndDate(), course.getTuitionFee() };
						tableModel.addRow(row);
					}
				}

				private void showStudentManagement() {
					contentPanel.setLayout(new BorderLayout());

					JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
					buttonPanel.setBackground(Color.RED);
					JButton addButton = new JButton("Add Student");
					addButton.setBackground(Color.CYAN);
					JButton updateButton = new JButton("Update Student");
					updateButton.setBackground(Color.CYAN);
					JButton deleteButton = new JButton("Delete Student");
					deleteButton.setBackground(Color.CYAN);
					JButton timkiemButton = new JButton("Search");
					timkiemButton.setBackground(Color.CYAN);
					addButton.addActionListener(e -> openAddStudentDialog());
					updateButton.addActionListener(e -> updateStudent());
					deleteButton.addActionListener(e -> deleteStudent());
					timkiemButton.addActionListener(e -> timkiemButton());
					buttonPanel.add(addButton);
					buttonPanel.add(updateButton);
					buttonPanel.add(deleteButton);
					buttonPanel.add(timkiemButton);
					String[] columnNames = { "studentID", "studentName", "dob", "address", "phoneNumber", "Email",
							"courseList", "Gender" };
					tableModel = new DefaultTableModel(columnNames, 0);
					studentTable = new JTable(tableModel);
					Font tableFont = new Font("Arial", Font.PLAIN, 16); 
				    studentTable.setFont(tableFont);				   
				    studentTable.setRowHeight(30); 			
				    JTableHeader header = studentTable.getTableHeader();
				    header.setFont(new Font("Arial", Font.BOLD, 18));
					JScrollPane scrollPane = new JScrollPane(studentTable);
					buttonPanel.add(scrollPane);
                     
					contentPanel.add(buttonPanel, BorderLayout.NORTH);
					contentPanel.add(scrollPane, BorderLayout.CENTER);

					loadStudentData();
				}

			
				private void timkiemButton() {				    
				    JTextField searchField = new JTextField(20);
				    JPanel searchPanel = new JPanel();
				    searchPanel.add(new JLabel("Search by Name or ID:"));
				    searchPanel.add(searchField);				   			    				  
				    int option = JOptionPane.showConfirmDialog(null, searchPanel, "Search Student", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
				    if (option == JOptionPane.OK_OPTION) {
				        String searchTerm = searchField.getText().trim();

				        if (!searchTerm.isEmpty()) {
				            searchStudents(searchTerm);  
				        } else {
				            JOptionPane.showMessageDialog(null, "Please enter a search term.");
				        }
				    }
				}
				private void searchStudents(String searchTerm) {				  
				    DefaultTableModel model = (DefaultTableModel) studentTable.getModel();
				    model.setRowCount(0); 

				    Database db = new Database();
				    ArrayList<HocVien> matchingStudents = db.searchHocVien(searchTerm);  
				    for (HocVien student : matchingStudents) {
				        model.addRow(new Object[] {
				            student.getStudentID(),
				            student.getStudentName(),
				            student.getDob(),
				            student.getAddress(),
				            student.getPhoneNumber(),
				            student.getEmail(),
				            student.getCourseList(),
				            student.isGender() ? "Male" : "Female"
				        });
				    }				  
				    if (matchingStudents.isEmpty()) {
				        JOptionPane.showMessageDialog(null, "No students found.");
				    }
				}
				private void openAddStudentDialog() {
					JTextField idField = new JTextField(20);
					JTextField nameField = new JTextField(20);
					JTextField dobField = new JTextField(20);
					JTextField addressField = new JTextField(20);
					JTextField phoneNumberField = new JTextField(20);
					JTextField emailField = new JTextField(20);
					JTextField courseListField = new JTextField(20);
					JLabel studentGenderLabel = new JLabel("Gender");

					JRadioButton Genderm = new JRadioButton("Male");
					JRadioButton Genderf = new JRadioButton("Female");
					ButtonGroup genderGroup = new ButtonGroup();
					genderGroup.add(Genderm);
					genderGroup.add(Genderf);
					JPanel panel = new JPanel();
					panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
					panel.add(new JLabel("ID"));
					panel.add(idField);
					panel.add(new JLabel("Name:"));
					panel.add(nameField);
					panel.add(new JLabel("Ngày sinh (yyyy-MM-dd):"));
					panel.add(dobField);
					panel.add(new JLabel("Địa chỉ"));
					panel.add(addressField);
					panel.add(new JLabel("Sdt"));
					panel.add(phoneNumberField);
					panel.add(new JLabel("Email"));
					panel.add(emailField);
					panel.add(new JLabel("Khóa học"));
					panel.add(courseListField);
					panel.add(studentGenderLabel);
					panel.add(Genderm);
					panel.add(Genderf);
					int option = JOptionPane.showConfirmDialog(null, panel, "Add Student", JOptionPane.OK_CANCEL_OPTION,
							JOptionPane.PLAIN_MESSAGE);

					if (option == JOptionPane.OK_OPTION) {
						String id = idField.getText();
						String name = nameField.getText();
						String dobStr = dobField.getText();
						String address = addressField.getText();
						String phone = phoneNumberField.getText();
						String email = emailField.getText();
						String courseList = courseListField.getText();
						boolean gender = Genderm.isSelected();
						java.sql.Date dob = null;
						try {
							java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
							java.util.Date parsedDate = dateFormat.parse(dobStr);
							dob = new java.sql.Date(parsedDate.getTime());
						} catch (java.text.ParseException e) {
							e.printStackTrace();
							JOptionPane.showMessageDialog(null, "Invalid date format. Please use yyyy-MM-dd.");
							return;
						}
						HocVien newStudent = new HocVien();
						newStudent.setStudentID(id);
						newStudent.setStudentName(name);
						newStudent.setDob(dob);
						newStudent.setAddress(address);
						newStudent.setPhoneNumber(phone);
						newStudent.setEmail(email);
						newStudent.setCourseList(courseList);
						newStudent.setGender(gender);
						Database cn = new Database();
						Connection conn = null;

						try {
							conn = cn.getConnection();

							if (id.isEmpty() || name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
								JOptionPane.showMessageDialog(null, "Please fill in all required fields.");
								return;
							}
							String query = "INSERT INTO HocVien (studentID, studentName, dob, address, phoneNumber, Email, courseList, Gender) "
									+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
							PreparedStatement stmt = conn.prepareStatement(query);
							stmt.setString(1, id);
							stmt.setString(2, name);
							stmt.setDate(3, dob);
							stmt.setString(4, address);
							stmt.setString(5, phone);
							stmt.setString(6, email);
							stmt.setString(7, courseList);
							stmt.setBoolean(8, gender);
							int rowsAffected = stmt.executeUpdate();
							if (rowsAffected > 0) {
								JOptionPane.showMessageDialog(null, "Student added successfully.");
							} else {
								JOptionPane.showMessageDialog(null, "Failed to add student.");
							}

						} catch (SQLException e) {
							e.printStackTrace();
							JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
						} finally {
							try {
								if (conn != null) {
									conn.close();
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
					}
				}
				private void refreshStudentTable() {
					DefaultTableModel model = (DefaultTableModel) studentTable.getModel();
					model.setRowCount(0);
					Database db = new Database();
					ArrayList<HocVien> allStudents = db.getListHocVien();
					for (HocVien student : allStudents) {
						model.addRow(new Object[] { student.getStudentID(), student.getStudentName(), student.getDob(),
								student.getPhoneNumber(), student.getAddress(), student.getEmail(),
								student.getCourseList(), student.isGender() ? "Male" : "Female" });
					}
				}

				public HocVien getStudentById(String studentID) {
					return null;
				}
				private void updateStudent() {
				    // Lấy dòng đã chọn trong bảng
				    int selectedRow = studentTable.getSelectedRow();
				    if (selectedRow == -1) {
				        JOptionPane.showMessageDialog(null, "Please select a student to update.");
				        return;
				    }
				    // Lấy dữ liệu của học viên đã chọn
				    String studentID = studentTable.getValueAt(selectedRow, 0).toString();
				    String studentName = studentTable.getValueAt(selectedRow, 1).toString();
				    String dob = studentTable.getValueAt(selectedRow, 2).toString();
				    String address = studentTable.getValueAt(selectedRow, 3).toString();
				    String phoneNumber = studentTable.getValueAt(selectedRow, 4).toString();
				    String email = studentTable.getValueAt(selectedRow, 5).toString();
				    String courseList = studentTable.getValueAt(selectedRow, 6).toString();
				    String gender = studentTable.getValueAt(selectedRow, 7).toString();
				    // Tạo các trường nhập liệu với dữ liệu hiện tại
				    JTextField nameField = new JTextField(studentName, 20);
				    JTextField dobField = new JTextField(dob, 20);
				    JTextField addressField = new JTextField(address, 20);
				    JTextField phoneNumberField = new JTextField(phoneNumber, 20);
				    JTextField emailField = new JTextField(email, 20);
				    JTextField courseListField = new JTextField(courseList, 20);
				    JRadioButton maleRadioButton = new JRadioButton("Male");
				    JRadioButton femaleRadioButton = new JRadioButton("Female");
				    if ("Male".equals(gender)) {
				        maleRadioButton.setSelected(true);
				    } else {
				        femaleRadioButton.setSelected(true);
				    }

				    ButtonGroup genderGroup = new ButtonGroup();
				    genderGroup.add(maleRadioButton);
				    genderGroup.add(femaleRadioButton);

				    // Tạo panel để hiển thị các trường nhập liệu
				    JPanel panel = new JPanel();
				    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
				    panel.add(new JLabel("Student ID:"));
				    panel.add(new JLabel(studentID));  // Không cho phép sửa ID
				    panel.add(new JLabel("Name:"));
				    panel.add(nameField);
				    panel.add(new JLabel("Date of Birth (yyyy-MM-dd):"));
				    panel.add(dobField);
				    panel.add(new JLabel("Address:"));
				    panel.add(addressField);
				    panel.add(new JLabel("Phone Number:"));
				    panel.add(phoneNumberField);
				    panel.add(new JLabel("Email:"));
				    panel.add(emailField);
				    panel.add(new JLabel("Courses:"));
				    panel.add(courseListField);
				    panel.add(new JLabel("Gender:"));
				    panel.add(maleRadioButton);
				    panel.add(femaleRadioButton);
				    int option = JOptionPane.showConfirmDialog(null, panel, "Update Student", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
				    if (option == JOptionPane.OK_OPTION) {
				        String updatedName = nameField.getText();
				        String updatedDobStr = dobField.getText();
				        String updatedAddress = addressField.getText();
				        String updatedPhone = phoneNumberField.getText();
				        String updatedEmail = emailField.getText();
				        String updatedCourseList = courseListField.getText();
				        boolean updatedGender = maleRadioButton.isSelected();
				        // Kiểm tra dữ liệu đầu vào
				        if (updatedName.isEmpty() || updatedDobStr.isEmpty() || updatedAddress.isEmpty() || updatedPhone.isEmpty() || updatedEmail.isEmpty()) {
				            JOptionPane.showMessageDialog(null, "Please fill in all fields.");
				            return;
				        }
				        // Chuyển đổi ngày sinh từ chuỗi
				        java.sql.Date updatedDob = null;
				        try {
				            java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
				            java.util.Date parsedDate = dateFormat.parse(updatedDobStr);
				            updatedDob = new java.sql.Date(parsedDate.getTime());
				        } catch (java.text.ParseException e) {
				            JOptionPane.showMessageDialog(null, "Invalid date format. Please use yyyy-MM-dd.");
				            return;
				        }
				        // Cập nhật đối tượng HocVien với dữ liệu đã sửa
				        HocVien updatedStudent = new HocVien();
				        updatedStudent.setStudentID(studentID); // Không thay đổi Student ID
				        updatedStudent.setStudentName(updatedName);
				        updatedStudent.setDob(updatedDob);
				        updatedStudent.setAddress(updatedAddress);
				        updatedStudent.setPhoneNumber(updatedPhone);
				        updatedStudent.setEmail(updatedEmail);
				        updatedStudent.setCourseList(updatedCourseList);
				        updatedStudent.setGender(updatedGender);
				        // Cập nhật vào cơ sở dữ liệu
				        Database db = new Database();
				        boolean success = db.updateStudent(updatedStudent);

				        if (success) {
				            JOptionPane.showMessageDialog(null, "Student updated successfully.");
				            loadStudentData();  // Tải lại dữ liệu bảng sau khi cập nhật
				        } else {
				            JOptionPane.showMessageDialog(null, "Failed to update student.");
				        }
				    }
				}


				private Object deleteStudent() {
				    int selectedRow = studentTable.getSelectedRow();
				    if (selectedRow != -1) {
				        String studentID = (String) studentTable.getValueAt(selectedRow, 0);
				        int confirm = JOptionPane.showConfirmDialog(contentPanel,
				        		"Are you sure you want to delete this student?", "Delete Student",
				        		JOptionPane.YES_NO_CANCEL_OPTION);				        
				        if (confirm == JOptionPane.YES_OPTION) {
				            Database db = new Database();
				            boolean deleted = db.deleteHocVien(studentID);
				            if (deleted) {
				                JOptionPane.showMessageDialog(contentPanel, "Student deleted successfully!");
				                loadStudentData(); 
				            } else {
				                JOptionPane.showMessageDialog(contentPanel, "Error deleting student.");
				            }
				        }
				    } else {
				        JOptionPane.showMessageDialog(contentPanel, "Please select a student to delete.");
				    }
				    return selectedRow;
				}

				private Connection getConnection() {
					// TODO Auto-generated method stub
					return null;
				}
			});
			menuPanel.add(Box.createVerticalStrut(10));
			menuPanel.add(btn);
		}
		mainPanel.add(menuPanel, BorderLayout.WEST);
		contentPanel = new JPanel();
		contentPanel.setBackground(Color.CYAN);
		contentPanel.setPreferredSize(new Dimension(600, height));
		mainPanel.add(contentPanel, BorderLayout.CENTER);

		dao = new Database();
		setVisible(true);
	}
	protected void Dashboard() {
		// TODO Auto-generated method stub
		
	}
	private void loadStudentData() {
	    tableModel.setRowCount(0);
	    ArrayList<HocVien> hocviens = dao.getListHocVien();	    
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");	   
	    for (HocVien hoc : hocviens) {
	        String formattedDob = ""; 	      
	        if (hoc.getDob() != null) {
	            formattedDob = dateFormat.format(hoc.getDob());
	        }	     
	        Object[] row = {
	            hoc.getStudentID(),
	            hoc.getStudentName(),
	            formattedDob, 
	            hoc.getAddress(),
	            hoc.getPhoneNumber(),
	            hoc.getEmail(),
	            hoc.getCourseList(),
	            hoc.isGender() ? "Nam" : "Nữ"
	        };
	        tableModel.addRow(row);
	    }
	}
	
	

    public static void main(String[] args) {
        new TrangChu();
    }
}
