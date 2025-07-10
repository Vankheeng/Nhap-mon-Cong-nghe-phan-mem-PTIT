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
import main.java.com.restaurant.dao.BookedDishDAO;

public class DeleteBookedDishDetailFrm extends JFrame {
    private JLabel lblConfirm;
    private JButton btnDelete;
    private JButton btnCancel;
    private int bookedDishId;
    private DeleteBookedDishFrm parent;

    public DeleteBookedDishDetailFrm(int bookedDishId, String dishName, DeleteBookedDishFrm parent) {
        this.bookedDishId = bookedDishId;
        this.parent = parent;
        initComponents();
        lblConfirm.setText("Bạn có chắc chắn muốn xóa món: " + dishName + "?");
    }

    private void initComponents() {
        lblConfirm = new JLabel();
        btnDelete = new JButton();
        btnCancel = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Xóa món");

        btnDelete.setText("Xóa");
        btnCancel.setText("Hủy");

        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnDeleteActionPerformed(evt);
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
                    .addComponent(lblConfirm, GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnDelete)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancel)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblConfirm)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDelete)
                    .addComponent(btnCancel))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }

    private void btnDeleteActionPerformed(ActionEvent evt) {
        new BookedDishDAO().deleteBookedDish(bookedDishId);
        JOptionPane.showMessageDialog(this, "Xóa món thành công");
        parent.loadBookedDishes();
        parent.setVisible(true);
        dispose();
    }

    private void btnCancelActionPerformed(ActionEvent evt) {
        parent.setVisible(true);
        dispose();
    }
}