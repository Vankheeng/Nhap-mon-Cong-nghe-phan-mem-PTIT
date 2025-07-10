package main.java.com.restaurant.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;
import main.java.com.restaurant.dao.BookingDAO;
import main.java.com.restaurant.entity.Booking;
import main.java.com.restaurant.entity.BookedTable;

public class ManageBookedDishFrm extends JFrame {
    private JComboBox<String> cbServingBookings;
    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private Booking selectedBooking;
    private Map<Booking, List<BookedTable>> servingBookings; // Thêm để lưu thông tin booking và bàn
    private boolean isLoading = false; // Biến cờ để kiểm soát sự kiện khi tải dữ liệu

    public ManageBookedDishFrm() {
        initComponents();
        loadServingBookings();
    }

    private void initComponents() {
        JLabel lblServingBookings = new JLabel();
        cbServingBookings = new JComboBox<>();
        btnAdd = new JButton();
        btnUpdate = new JButton();
        btnDelete = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Manage Booked Dishes");

        lblServingBookings.setText("Quản lý gọi món:");
        btnAdd.setText("Thêm");
        btnUpdate.setText("Sửa");
        btnDelete.setText("Xóa");

        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        cbServingBookings.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (!isLoading) {
                    cbServingBookingsActionPerformed(evt);
                }
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.CENTER)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(lblServingBookings)
                    .addComponent(cbServingBookings, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdd)
                    .addComponent(btnUpdate)
                    .addComponent(btnDelete)
                )
                .addGap(50, 50, 50)
            )
        );
        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblServingBookings)
                .addGap(10, 10, 10)
                .addComponent(cbServingBookings, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(btnAdd)
                .addGap(10, 10, 10)
                .addComponent(btnUpdate)
                .addGap(10, 10, 10)
                .addComponent(btnDelete)
                .addGap(20, 20, 20)
        );

        pack();
        setLocationRelativeTo(null);
    }

    private void btnAddActionPerformed(ActionEvent evt) {
        if (selectedBooking != null) {
            new AddBookedDishFrm(selectedBooking).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn đặt bàn!");
        }
    }

    private void btnUpdateActionPerformed(ActionEvent evt) {
        if (selectedBooking != null) {
            new UpdateBookedDishFrm(selectedBooking).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn đặt bàn!");
        }
    }

    private void btnDeleteActionPerformed(ActionEvent evt) {
        if (selectedBooking != null) {
            new DeleteBookedDishFrm(selectedBooking).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn đặt bàn!");
        }
    }

    private void cbServingBookingsActionPerformed(ActionEvent evt) {
        int index = cbServingBookings.getSelectedIndex();
        if (index >= 0) {
            List<Booking> bookings = new ArrayList<>(servingBookings.keySet());
            selectedBooking = bookings.get(index);
        }
    }

    private void loadServingBookings() {
        isLoading = true; // Đặt cờ khi bắt đầu tải dữ liệu
        BookingDAO bookingDAO = new BookingDAO();
        servingBookings = bookingDAO.fetchTableServing(); // Lấy Map<Booking, List<BookedTable>>
        cbServingBookings.removeAllItems();
        for (Booking booking : servingBookings.keySet()) {
            List<BookedTable> bookedTables = servingBookings.get(booking);
            List<Integer> tableIds = bookedTables.stream()
                    .map(BookedTable::getTableId)
                    .sorted()
                    .collect(Collectors.toList());
            StringBuilder itemText = new StringBuilder("Bàn: ");
            for (Integer tableId : tableIds) {
                itemText.append(tableId).append(", ");
            }
            if (!tableIds.isEmpty()) {
                itemText.setLength(itemText.length() - 2); // Xóa dấu phẩy và khoảng trắng cuối
            }
            cbServingBookings.addItem(itemText.toString());
        }
        if (!servingBookings.isEmpty()) {
            List<Booking> bookings = new ArrayList<>(servingBookings.keySet());
            selectedBooking = bookings.get(0);
            cbServingBookings.setSelectedIndex(0); // Đặt selection để tránh sự kiện không mong muốn
        }
        isLoading = false; // Tắt cờ sau khi hoàn tất
    }
}