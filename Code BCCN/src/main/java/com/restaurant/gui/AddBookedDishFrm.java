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
import main.java.com.restaurant.entity.Menu;
import main.java.com.restaurant.dao.MenuDAO;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.net.URL;

/**
 * GUI form for adding dishes to a booking, displaying a table of menu items with search and refresh functionality.
 */
public class AddBookedDishFrm extends JFrame {
    private JTextField txtSearch;
    private JButton btnSearch;
    private JButton btnRefresh;
    private JTable table;
    private DefaultTableModel tableModel;
    private Booking booking;

    public AddBookedDishFrm(Booking booking) {
        this.booking = booking;
        initComponents();
        loadMenuItems();
    }

    /**
     * Initializes the GUI components, including the search bar, buttons, and table.
     */
    private void initComponents() {
        JLabel lblSearch = new JLabel("Tìm kiếm món ăn:");
        txtSearch = new JTextField(20);
        btnSearch = new JButton("Tìm kiếm");
        btnRefresh = new JButton("Làm mới");
        JScrollPane jScrollPane1 = new JScrollPane();
        table = new JTable();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gọi món");

        // Define table columns and model
        String[] columns = {"Tên món", "Hình ảnh", "Phân loại", "Giá", "Thêm", "menuId"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // "Thêm" column is editable
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 4) return JButton.class; // "Thêm" column is a button
                if (columnIndex == 5) return Integer.class; // "menuId" column is an integer
                return super.getColumnClass(columnIndex);
            }
        };
        table.setModel(tableModel);

        // Hide menuId column
        table.getColumnModel().getColumn(5).setMinWidth(0);
        table.getColumnModel().getColumn(5).setMaxWidth(0);
        table.getColumnModel().getColumn(5).setWidth(0);

        // Set custom renderers and editors
        table.getColumnModel().getColumn(1).setCellRenderer(new ImageRenderer()); // Image column renderer
        table.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
        table.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox(), this));

        jScrollPane1.setViewportView(table);

        // Search button action
        btnSearch.addActionListener(e -> btnSearchActionPerformed(e));

        // Refresh button action
        btnRefresh.addActionListener(e -> btnRefreshActionPerformed(e));

        // Layout setup
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
                            .addGap(18)
                            .addComponent(txtSearch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGap(18)
                            .addComponent(btnSearch)
                            .addGap(18)
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
                        .addComponent(txtSearch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSearch)
                        .addComponent(btnRefresh))
                    .addGap(18)
                    .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
                    .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Handles the search button click, searching for menu items by name.
     */
    private void btnSearchActionPerformed(ActionEvent evt) {
        String searchText = txtSearch.getText().trim();
        if (searchText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên món ăn để tìm kiếm!");
            refreshMenuItems();
            return;
        }
        List<Menu> menus = new MenuDAO().searchMenu(searchText);
        if (menus.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không có món ăn nào trong kết quả tìm kiếm");
            refreshMenuItems();
        } else {
            loadTableData(menus);
        }
    }

    /**
     * Handles the refresh button click, reloading all menu items.
     */
    private void btnRefreshActionPerformed(ActionEvent evt) {
        refreshMenuItems();
    }

    /**
     * Loads all menu items into the table.
     */
    private void loadMenuItems() {
        List<Menu> menus = new MenuDAO().getMenu();
        loadTableData(menus);
    }

    /**
     * Populates the table with menu data.
     */
    private void loadTableData(List<Menu> menus) {
        tableModel.setRowCount(0);
        for (Menu menu : menus) {
            tableModel.addRow(new Object[]{
                menu.getDishName(),
                menu.getImage(), // Image file name (e.g., "dish1.jpg")
                menu.getDishType(),
                menu.getPrice(),
                "Thêm",
                menu.getId()
            });
        }
    }

    /**
     * Refreshes the table and clears the search field.
     */
    public void refreshMenuItems() {
        loadMenuItems();
        txtSearch.setText("");
    }

    /**
     * Renderer for displaying buttons in the "Thêm" column.
     */
    static class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setText("Thêm");
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
            setText((value == null) ? "Thêm" : value.toString());
            return this;
        }
    }

    /**
     * Editor for handling button clicks in the "Thêm" column.
     */
    static class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean isPushed;
        private AddBookedDishFrm parentFrame;
        private JTable table;
        private int row;

        public ButtonEditor(JCheckBox checkBox, AddBookedDishFrm parentFrame) {
            super(checkBox);
            this.parentFrame = parentFrame;
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
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
            label = (value == null) ? "Thêm" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                int menuId = (int) table.getModel().getValueAt(row, 5);
                String dishName = (String) table.getModel().getValueAt(row, 0);
                new AddBookedDishDetailFrm(parentFrame.booking, menuId, dishName, parentFrame).setVisible(true);
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

    /**
     * Renderer for displaying images in the "Hình ảnh" column.
     */
    static class ImageRenderer extends JLabel implements TableCellRenderer {
        public ImageRenderer() {
            setOpaque(true);
            setHorizontalAlignment(CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                      boolean isSelected, boolean hasFocus, int row, int column) {
            if (value != null) {
                try {
                    String imagePath = value.toString();
                    URL imageUrl = ImageRenderer.class.getResource("main/resources/images/" + imagePath);
                    if (imageUrl != null) {
                        Image img = ImageIO.read(imageUrl);
                        Image scaledImg = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                        setIcon(new ImageIcon(scaledImg));
                        setText("");
                    } else {
                        setIcon(null);
                        setText("Image not found");
                    }
                } catch (IOException e) {
                    setIcon(null);
                    setText("Error loading image");
                }
            } else {
                setIcon(null);
                setText("No image");
            }

            if (isSelected) {
                setBackground(table.getSelectionBackground());
                setForeground(table.getSelectionForeground());
            } else {
                setBackground(table.getBackground());
                setForeground(table.getForeground());
            }

            return this;
        }
    }
}