
package view;

import chart.ModelChart;
import java.awt.Color;


public class Dashboard extends javax.swing.JFrame {
    public Dashboard() {
        initComponents();
        getContentPane().setBackground(new Color(250, 250, 250));
        chart.addLegend("Học phí", new Color(245, 189, 135));
        chart.addLegend("Tiền đã nộp", new Color(135, 189, 245));
        chart.addLegend("Còn thiếu", new Color(189, 135, 245));
//    //   chart.addLegend("Cost", new Color(139, 229, 222));
//        chart.addData(new ModelChart("T1", new double[]{500, 200, 80, 89}));
//        chart.addData(new ModelChart("T2", new double[]{600, 750, 90, 150}));
//        chart.addData(new ModelChart("T3", new double[]{200, 350, 460, 900}));
//        chart.addData(new ModelChart("T4", new double[]{480, 150, 750, 700}));
//        chart.addData(new ModelChart("T5", new double[]{350, 540, 300, 150}));
//        chart.addData(new ModelChart("T6", new double[]{390, 280, 81, 200}));
//        chart.addData(new ModelChart("T7", new double[]{190, 280, 81, 200}));
//        chart.addData(new ModelChart("T8", new double[]{290, 280, 71, 200}));
        chart.addData(new ModelChart("T9", new double[]{190, 280, 81, 200}));
      //  chart.addData(new ModelChart("T10", new double[]{150, 280, 61, 200}));
        chart.addData(new ModelChart("T11", new double[]{190, 280, 81, 200}));
      //  chart.addData(new ModelChart("T12", new double[]{190, 280, 51, 200}));
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        chart = new chart.Chart();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        chart.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N

        jButton1.setText("Refresh And Clear");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(164, 164, 164)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1)
                    .addComponent(chart, javax.swing.GroupLayout.PREFERRED_SIZE, 722, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(170, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(119, Short.MAX_VALUE)
                .addComponent(chart, javax.swing.GroupLayout.PREFERRED_SIZE, 462, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addGap(97, 97, 97))
        );

        pack();
        setLocationRelativeTo(null);
    }

    private void formWindowOpened(java.awt.event.WindowEvent evt) {
        chart.start();
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        chart.clear();
        chart.addData(new ModelChart("January", new double[]{500, 200, 80, 89}));
        chart.addData(new ModelChart("February", new double[]{600, 750, 90, 150}));
        chart.addData(new ModelChart("March", new double[]{200, 350, 460, 900}));
        chart.addData(new ModelChart("April", new double[]{480, 150, 750, 700}));
        chart.addData(new ModelChart("May", new double[]{350, 540, 300, 150}));
        chart.addData(new ModelChart("June", new double[]{190, 280, 81, 200}));
        chart.start();
    }
    public static void main(String args[]) {
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
       
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Dashboard().setVisible(true);
            }
        });
    }

    
    private chart.Chart chart;
    private javax.swing.JButton jButton1;
   
}
