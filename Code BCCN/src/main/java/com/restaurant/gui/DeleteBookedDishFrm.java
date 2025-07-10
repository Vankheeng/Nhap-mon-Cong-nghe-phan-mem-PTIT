package main.java.com.restaurant.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import main.java.com.restaurant.entity.Booking;
import main.java.com.restaurant.entity.BookedDish;
import main.java.com.restaurant.entity.Menu;
import main.java.com.restaurant.dao.MenuDAO;
import main.java.com.restaurant.dao.BookedDishDAO;

public class DeleteBookedDishFrm extends JFrame {
    private JTextField txtSearch;
    private JButton btnSearch;
    private JButton btnRefresh;
    private JTable table;
    private DefaultTableModel tableModel;
    private Booking booking;

    public DeleteBookedDishFrm(Booking booking) {
        this.booking = booking;
        initComponents();
        loadBookedDishes();
    }

    private void initComponents() {
        JLabel lblSearch = new JLabel();
        txtSearch = new JTextField();
        btnSearch = new JButton();
        btnRefresh = new JButton();
        JScrollPane jScrollPane1 = new JScrollPane();
        table = new JTable();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Xóa món đã gọi - Đặt bàn lúc " + booking.getBookingTime().toString());

        lblSearch.setText("Tìm kiếm món ăn:");
        txtSearch.setColumns(20);
        btnSearch.setText("Tìm kiếm");
        btnRefresh.setText("Làm mới");

        String[] columns = {"Tên món", "Số lượng", "Ghi chú", "", "ID"}; // Thêm cột ẩn "ID"
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 3) return JButton.class;
                if (columnIndex == 1) return Integer.class;
                if (columnIndex == 4) return Integer.class; // Cột ẩn "ID" là Integer
                return super.getColumnClass(columnIndex);
            }
        };
        table.setModel(tableModel);
        // Ẩn cột "ID"
        table.getColumnModel().getColumn(4).setMinWidth(0);
        table.getColumnModel().getColumn(4).setMaxWidth(0);
        table.getColumnModel().getColumn(4).setWidth(0);
        // Thiết lập renderer và editor cho cột "Xóa"
        table.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer("Xóa"));
        table.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor(new JCheckBox(), this));
        jScrollPane1.setViewportView(table);

        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        btnRefresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblSearch)
                        .addGap(18, 18, 18)
                        .addComponent(txtSearch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSearch)
                        .addGap(18, 18, 18)
                        .addComponent(btnRefresh)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSearch)
                    .addComponent(txtSearch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                    .addComponent(btnSearch)
                    .addComponent(btnRefresh))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }

    private void btnSearchActionPerformed(ActionEvent evt) {
        String searchText = txtSearch.getText().trim();
        List<BookedDish> dishes = new BookedDishDAO().getBookedDish(booking.getId());
        List<Menu> menus = new MenuDAO().searchMenu(searchText);
        if (menus.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không có món ăn nào trong kết quả tìm kiếm");
            return;
        }
        List<BookedDish> filteredDishes = dishes.stream()
            .filter(dish -> menus.stream().anyMatch(menu -> menu.getId() == dish.getDishId()))
            .toList();
        if (filteredDishes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không có món ăn nào trong kết quả tìm kiếm");
        }
        loadTableData(filteredDishes);
    }

    private void btnRefreshActionPerformed(ActionEvent evt) {
        loadBookedDishes();
        txtSearch.setText("");
    }

    public void loadBookedDishes() {
        List<BookedDish> dishes = new BookedDishDAO().getBookedDish(booking.getId());
        loadTableData(dishes);
    }

    private void loadTableData(List<BookedDish> dishes) {
        tableModel.setRowCount(0);
        MenuDAO menuDAO = new MenuDAO();
        for (BookedDish dish : dishes) {
            Menu menu = menuDAO.getMenu().stream()
                .filter(m -> m.getId() == dish.getDishId())
                .findFirst()
                .orElse(null);
            if (menu != null) {
                tableModel.addRow(new Object[]{
                    menu.getDishName(),
                    dish.getQuantity(),
                    dish.getNote() != null ? dish.getNote() : "",
                    "Xóa",
                    dish.getId() // Lưu bookedDishId vào cột ẩn
                });
            }
        }
    }

    // Lớp renderer để hiển thị JButton
    static class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer(String text) {
            setOpaque(true);
            setText(text);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
            } else {
                setForeground(table.getForeground());
                setBackground(UIManager.getColor("Button.background"));
            }
            setText((value == null) ? "Xóa" : value.toString());
            return this;
        }
    }

    // Lớp editor để xử lý sự kiện nhấp vào JButton
    static class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean isPushed;
        private DeleteBookedDishFrm parentFrame;
        private JTable table;
        private int row;

        public ButtonEditor(JCheckBox checkBox, DeleteBookedDishFrm parentFrame) {
            super(checkBox);
            this.parentFrame = parentFrame;
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            this.table = table;
            this.row = row;
            if (isSelected) {
                button.setForeground(table.getSelectionForeground());
                button.setBackground(table.getSelectionBackground());
            } else {
                button.setForeground(table.getForeground());
                button.setBackground(table.getBackground());
            }
            label = (value == null) ? "Xóa" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                int bookedDishId = (int) table.getModel().getValueAt(row, 4); // Lấy từ cột ẩn "ID"
                String dishName = (String) table.getModel().getValueAt(row, 0);
                new DeleteBookedDishDetailFrm(bookedDishId, dishName, parentFrame).setVisible(true);
            }
            isPushed = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        @Override
        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }
}