package main.java.com.restaurant.gui;

/**
 *
 * @author Admin
 */

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StaffHomeFrm extends JFrame {
    private JButton btnManageOrders;
    private JButton btnStaffInfo;

    public StaffHomeFrm() {
        initComponents();
    }

    private void initComponents() {
        btnManageOrders = new JButton();
        btnStaffInfo = new JButton();
        
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Staff Home");

        btnManageOrders.setText("Quản lý gọi món");
        btnManageOrders.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnManageOrdersActionPerformed(evt);
            }
        });
        
        btnStaffInfo.setText("Thông tin nhân viên");

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(btnManageOrders)
//                .addGap(200)
                .addComponent(btnStaffInfo)
                .addGap(500)
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addGap(120)
                .addComponent(btnManageOrders)
                .addGap(50)
                .addComponent(btnStaffInfo)
                .addGap(150)
        );

        pack();
        setLocationRelativeTo(null);
    }

    private void btnManageOrdersActionPerformed(ActionEvent evt) {
        new ManageBookedDishFrm().setVisible(true);
        dispose();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StaffHomeFrm().setVisible(true);
            }
        });
    }
}