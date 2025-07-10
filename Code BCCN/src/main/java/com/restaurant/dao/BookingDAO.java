package main.java.com.restaurant.dao;

import main.java.com.restaurant.entity.Booking;
import main.java.com.restaurant.entity.BookedTable;
import main.java.com.restaurant.util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingDAO {

    public Map<Booking, List<BookedTable>> fetchTableServing() {
        Map<Booking, List<BookedTable>> bookingTablesMap = new HashMap<>();
        Map<Integer, Booking> bookingMap = new HashMap<>();

        String sql = "SELECT b.id, b.bookingTime, b.status, b.numPeople, " +
                    "bt.id AS bookedTableId, bt.checkin, bt.checkout, bt.note, bt.tableId " +
                    "FROM Booking b " +
                    "LEFT JOIN bookedTable bt ON b.id = bt.bookingId " +
                    "WHERE b.status = 'serving'";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int bookingId = rs.getInt("id");

                Booking booking = bookingMap.get(bookingId);
                if (booking == null) {
                    booking = new Booking(
                        bookingId,
                        rs.getTimestamp("bookingTime").toLocalDateTime(),
                        rs.getString("status"),
                        rs.getInt("numPeople")
                    );
                    bookingMap.put(bookingId, booking);
                    bookingTablesMap.put(booking, new ArrayList<>());
                }

                int bookedTableId = rs.getInt("bookedTableId");
                if (!rs.wasNull()) {
                    LocalDateTime checkin = rs.getTimestamp("checkin") != null ? rs.getTimestamp("checkin").toLocalDateTime() : null;
                    LocalDateTime checkout = rs.getTimestamp("checkout") != null ? rs.getTimestamp("checkout").toLocalDateTime() : null;
                    String note = rs.getString("note");
                    int tableId = rs.getInt("tableId");

                    BookedTable bookedTable = new BookedTable(
                        bookedTableId,
                        checkin,
                        checkout,
                        note,
                        tableId,
                        bookingId
                    );
                    bookingTablesMap.get(booking).add(bookedTable);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookingTablesMap;
    }
}