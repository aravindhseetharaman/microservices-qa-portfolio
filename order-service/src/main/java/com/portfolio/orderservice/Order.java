package com.portfolio.orderservice;

public class Order {
    private Long id;
    private Long productId;
    private Long userId;
    private int quantity;
    private double totalPrice;
    private String status;

    public Order(Long id, Long productId, Long userId, 
                 int quantity, double totalPrice, String status) {
        this.id = id;
        this.productId = productId;
        this.userId = userId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public Long getId() { return id; }
    public Long getProductId() { return productId; }
    public Long getUserId() { return userId; }
    public int getQuantity() { return quantity; }
    public double getTotalPrice() { return totalPrice; }
    public String getStatus() { return status; }
    public void setId(Long id) { this.id = id; }
}
