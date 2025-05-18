package com.dead97531.beadando.model;

import jakarta.persistence.*;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double price;
    private String seatNumber;
    private boolean sold = false;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }
    public boolean isSold() { return sold; }
    public void setSold(boolean sold) { this.sold = sold; }
    public Event getEvent() { return event; }
    public void setEvent(Event event) { this.event = event; }
}