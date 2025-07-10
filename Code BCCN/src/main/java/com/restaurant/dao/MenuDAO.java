/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.restaurant.dao;

/**
 *
 * @author Admin
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import main.java.com.restaurant.entity.Menu;
import main.java.com.restaurant.util.DatabaseConnection;

public class MenuDAO {
    public List<Menu> getMenu() {
        List<Menu> menus = new ArrayList<>();
        String sql = "SELECT * FROM Menu";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                menus.add(new Menu(
                    rs.getInt("id"),
                    rs.getString("dishName"),
                    rs.getString("dishType"),
                    rs.getDouble("price"),
                    rs.getString("description"),
                    rs.getString("image")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return menus;
    }

    public List<Menu> searchMenu(String dishName) {
        List<Menu> menus = new ArrayList<>();
        String sql = "SELECT * FROM Menu WHERE dishName LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + dishName + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    menus.add(new Menu(
                        rs.getInt("id"),
                        rs.getString("dishName"),
                        rs.getString("dishType"),
                        rs.getDouble("price"),
                        rs.getString("description"),
                        rs.getString("image")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return menus;
    }
}