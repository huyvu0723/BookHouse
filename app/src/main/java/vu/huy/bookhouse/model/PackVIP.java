package vu.huy.bookhouse.model;

public class PackVIP {
    private int packId;
    private int packDay;
    private double packCost;

    public PackVIP() {
    }
    public PackVIP(int packId, int packDay, double packCost) {
        this.packId = packId;
        this.packDay = packDay;
        this.packCost = packCost;
    }

    public int getPackId() {
        return packId;
    }

    public void setPackId(int packId) {
        this.packId = packId;
    }

    public int getPackDay() {
        return packDay;
    }

    public void setPackDay(int packDay) {
        this.packDay = packDay;
    }

    public double getPackCost() {
        return packCost;
    }

    public void setPackCost(double packCost) {
        this.packCost = packCost;
    }

    @Override
    public String toString() {
        return packCost + "đ / " + packDay + " day";
    }
}
