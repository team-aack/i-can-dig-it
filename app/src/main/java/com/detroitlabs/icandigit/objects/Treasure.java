package com.detroitlabs.icandigit.objects;

public class Treasure {
    public String itemType;
    public int itemAmount;

    public Treasure(String itemType, int itemAmount) {
        this.itemType = itemType;
        this.itemAmount = itemAmount;
    }

    public String getItemType() {
        return itemType;
    }

    public int getItemAmount() {
        return itemAmount;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public void setItemAmount(int itemAmount) {
        this.itemAmount = itemAmount;
    }
}
