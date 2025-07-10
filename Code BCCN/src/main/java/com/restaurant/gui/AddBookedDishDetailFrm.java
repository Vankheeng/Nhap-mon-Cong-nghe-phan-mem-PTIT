/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.restaurant.gui;

/**
 *
 * @author Admin
 */
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import main.java.com.restaurant.entity.Booking;
import main.java.com.restaurant.entity.BookedDish;
import main.java.com.restaurant.dao.BookedDishDAO;

public class AddBookedDishDetailFrm extends JFrame {
    private JLabel lblDishName;
    private JTextField txtQuantity;
    private JTextArea txtNote;
    private JButton btnConfirm;
    private JButton btnCancel;
    private Booking booking;
    private int menuId;
    private AddBookedDishFrm parent;

    public AddBookedDishDetailFrm(Booking booking, int menuId, String dishName, AddBookedDishFrm parent) {
        this.booking = booking;
        this.menuId = menuId;
        this.parent = parent;
        initComponents();
        lblDishName.setText(dishName);
    }

    private void initComponents() {
        JLabel lblDishNameLabel = new JLabel();
        lblDishName = new JLabel();
        JLabel lblQuantity = new JLabel();
        txtQuantity = new JTextField();
        JLabel lblNote = new JLabel();
        JScrollPane jScrollPane1 = new JScrollPane();
        txtNote = new JTextArea();
        btnConfirm = new JButton();
        btnCancel = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Thêm thông tin gọi món");

        lblDishNameLabel.setText("Tên món:");
        lblQuantity.setText("Số lượng:");
        lblNote.setText("Ghi chú:");
        txtNote.setColumns(20);
        txtNote.setRows(5);
        jScrollPane1.setViewportView(txtNote);
        btnConfirm.setText("Xác nhận");
        btnCancel.setText("Hủy");

        btnConfirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnConfirmActionPerformed(evt);
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(lblDishNameLabel)
                            .addComponent(lblQuantity)
                            .addComponent(lblNote))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(lblDishName, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtQuantity)
                            .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnConfirm)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancel)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDishNameLabel)
                    .addComponent(lblDishName))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblQuantity)
                    .addComponent(txtQuantity, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lblNote)
                    .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(btnConfirm)
                    .addComponent(btnCancel))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }

    private void btnConfirmActionPerformed(ActionEvent evt) {
        try {
            int quantity = Integer.parseInt(txtQuantity.getText().trim());
            if (quantity <= 0) {
                JOptionPane.showMessageDialog(this, "Số lượng không hợp lệ");
                return;
            }
            String note = txtNote.getText().trim().isEmpty() ? null : txtNote.getText().trim();
            BookedDish dish = new BookedDish(0, quantity, note, menuId, booking.getId());
            new BookedDishDAO().addBookedDish(dish);
            JOptionPane.showMessageDialog(this, "Gọi món thành công");
            parent.setVisible(true);
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Số lượng không hợp lệ");
        }
    }

    private void btnCancelActionPerformed(ActionEvent evt) {
        parent.setVisible(true);
        dispose();
    }
}
