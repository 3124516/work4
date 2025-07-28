package org.example.work2.DTO;

public class Order {
    private String productName;
    private int quantity;
    private double amount;
    private long timestamp; // 添加时间戳字段

    // 构造器
    public Order() {}

    public Order(String productName, int quantity, double amount) {
        this.productName = productName;
        this.quantity = quantity;
        this.amount = amount;
        this.timestamp = System.currentTimeMillis();
    }

    // Getter 和 Setter 方法
    // ...

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
