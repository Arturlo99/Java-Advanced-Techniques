package main.java.models;

import main.java.bilboards.Order;
import java.time.Duration;

public class OrderWithId {
    private int orderId;

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getAdvertText() {
        return advertText;
    }

    public void setAdvertText(String advertText) {
        this.advertText = advertText;
    }

    public Duration getDisplayPeriod() {
        return displayPeriod;
    }

    public void setDisplayPeriod(Duration displayPeriod) {
        this.displayPeriod = displayPeriod;
    }

    private String advertText;
    private Duration displayPeriod;

    public OrderWithId(int orderId, Order order) {
        this.orderId = orderId;
        this.advertText = order.advertText;
        this.displayPeriod = order.displayPeriod;
    }

    public int getOrderId() {
        return orderId;
    }
}
