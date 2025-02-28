package view;

import javax.swing.*;
import java.awt.*;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

public class Charttion extends JDialog {
    private ChartPanel chartPanel;
    
    public Charttion(JFrame parent, JFreeChart chart, String title) {
        super(parent, title, true);
        
        // Tạo chart panel
        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        
        // Thêm các controls
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(chartPanel, BorderLayout.CENTER);
        
        // Thêm nút đóng
        JButton closeButton = new JButton("Đóng");
        closeButton.addActionListener(e -> dispose());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(closeButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Cấu hình dialog
        setContentPane(mainPanel);
        pack();
        setLocationRelativeTo(parent);
    }
    
    public void updateChart(JFreeChart newChart) {
        chartPanel.setChart(newChart);
        chartPanel.revalidate();
    }
}