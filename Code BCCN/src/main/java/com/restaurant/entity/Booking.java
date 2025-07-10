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

public class Booking {
    private int id;
    private LocalDateTime bookingTime;
    private String status;
    private int numPeople;

    public Booking(int id, LocalDateTime bookingTime, String status, int numPeople) {
        this.id = id;
        this.bookingTime = bookingTime;
        this.status = status;
        this.numPeople = numPeople;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public LocalDateTime getBookingTime() { return bookingTime; }
    public void setBookingTime(LocalDateTime bookingTime) { this.bookingTime = bookingTime; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public int getNumPeople() { return numPeople; }
    public void setNumPeople(int numPeople) { this.numPeople = numPeople; }
}
