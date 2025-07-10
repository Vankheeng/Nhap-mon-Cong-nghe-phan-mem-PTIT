/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.restaurant.entity;

/**
 *
 * @author Admin
 */
import java.time.LocalDateTime;

public class BookedTable {
    private int id;
    private LocalDateTime checkin;
    private LocalDateTime checkout;
    private String note;
    private int tableId;
    private int bookingId;

    public BookedTable(int id, LocalDateTime checkin, LocalDateTime checkout, String note, int tableId, int bookingId) {
        this.id = id;
        this.checkin = checkin;
        this.checkout = checkout;
        this.note = note;
        this.tableId = tableId;
        this.bookingId = bookingId;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public LocalDateTime getCheckin() { return checkin; }
    public void setCheckin(LocalDateTime checkin) { this.checkin = checkin; }
    public LocalDateTime getCheckout() { return checkout; }
    public void setCheckout(LocalDateTime checkout) { this.checkout = checkout; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
    public int getTableId() { return tableId; }
    public void setTableId(int tableId) { this.tableId = tableId; }
    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }
}
