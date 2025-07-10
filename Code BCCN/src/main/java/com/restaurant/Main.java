/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.restaurant;

/**
 *
 * @author Admin
 */

import main.java.com.restaurant.gui.StaffHomeFrm;

/**
 * Lớp Main là điểm khởi đầu của ứng dụng RestaurantOrderManagement.
 * Lớp này khởi tạo và hiển thị giao diện chính (StaffHomeFrm) của ứng dụng.
 */
public class Main {
    public static void main(String[] args) {
        // Đảm bảo giao diện được hiển thị trên luồng sự kiện Swing
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StaffHomeFrm().setVisible(true);
            }
        });
    }
}