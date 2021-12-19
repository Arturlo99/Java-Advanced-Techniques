package main.java.models;

public class BillboardRow {
    private int id;
    private int capacity;
    private int freeSpots;

    public void setId(int id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public BillboardRow(int id, int capacity, int freeSpots) {
        this.id = id;
        this.capacity = capacity;
        this.freeSpots = freeSpots;
    }

    public int getId() {
        return id;
    }

    public int getFreeSpots() {
        return freeSpots;
    }

    public void setFreeSpots(int freeSpots) {
        this.freeSpots = freeSpots;
    }
}
