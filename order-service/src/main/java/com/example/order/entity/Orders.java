package com.example.order.entity;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name="orders")
public class Orders {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    long id;

    long itemId;

    int quantity;

    String location;

    @Column(name="done_at",insertable=false,updatable=false)
    LocalDateTime doneAt;

    public Orders() {
        super();
    }
    public Orders(long itemId, int quantity, String location) {
        super();
        this.itemId = itemId;
        this.quantity = quantity;
        this.location = location;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getItemId() {
        return itemId;
    }
    public void setItemId(long itemId) {
        this.itemId = itemId;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public LocalDateTime getDoneAt() {
        return doneAt;
    }
    public void setDoneAt(LocalDateTime doneAt) {
        this.doneAt = doneAt;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
}