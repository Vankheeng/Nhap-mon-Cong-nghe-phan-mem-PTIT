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
import main.java.com.restaurant.entity.BookedDish;
import main.java.com.restaurant.util.DatabaseConnection;

public class BookedDishDAO {
    public List<BookedDish> getBookedDish(int bookingId) {
        List<BookedDish> dishes = new ArrayList<>();
        String sql = "SELECT * FROM BookedDish WHERE bookingId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookingId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    dishes.add(new BookedDish(
                        rs.getInt("id"),
                        rs.getInt("quantity"),
                        rs.getString("note"),
                        rs.getInt("dishId"),
                        rs.getInt("bookingId")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dishes;
    }

    public void addBookedDish(BookedDish dish) {
        String sql = "INSERT INTO BookedDish (quantity, note, dishId, bookingId) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, dish.getQuantity());
            stmt.setString(2, dish.getNote());
            stmt.setInt(3, dish.getDishId());
            stmt.setInt(4, dish.getBookingId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBookedDish(BookedDish dish) {
        String sql = "UPDATE BookedDish SET quantity = ?, note = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, dish.getQuantity());
            stmt.setString(2, dish.getNote());
            stmt.setInt(3, dish.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteBookedDish(int id) {
        String sql = "DELETE FROM BookedDish WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}