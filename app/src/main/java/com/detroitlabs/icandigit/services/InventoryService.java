package com.detroitlabs.icandigit.services;

import com.detroitlabs.icandigit.objects.Treasure;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrewjb on 10/22/14.
 */
public class InventoryService {

    public static List<Treasure> itemInventory = new ArrayList<Treasure>();
    public static Treasure freshTreasure;

    // Initiate a dig to find a new item
    public static void startDig() {

        freshTreasure = InventoryService.newItem();

        // Console test
        System.out.println("************************\nFound: " + freshTreasure.getItemType() + "\n");
        System.out.println("Inventory total: " + itemInventory.size());
        for (int i = 0; i < itemInventory.size(); i++) {
            Treasure t = itemInventory.get(i);
            System.out.println("x" + t.getItemAmount() + " " + t.getItemType());
        }
    }

    public static Treasure newItem(){
        boolean exists = false;
        Treasure newThing = null;

        // Select a new Treasure to add to the Inventory
        int pickTreasure = (int) Math.floor(Math.random() * 10);
        switch (pickTreasure) {
            case 0: newThing = new Treasure("Pork",1); break;
            case 1: newThing = new Treasure("Chicken",1); break;
            case 2: newThing = new Treasure("Beef",1); break;
            case 3: newThing = new Treasure("Lamb",1); break;
            case 4: newThing = new Treasure("Shark",1); break;
            case 5: newThing = new Treasure("Haggis",1); break;
            case 6: newThing = new Treasure("Snake",1); break;
            case 7: newThing = new Treasure("Squirrel",1); break;
            case 8: newThing = new Treasure("Mystery Meat",1); break;
            case 9: newThing = new Treasure("Coffee",1); break;
        }

        // Check if this new thing already exists in the list
        for (int i = 0; i < itemInventory.size(); i++) {
            Treasure oldThing = itemInventory.get(i);
            if (newThing.getItemType() == oldThing.getItemType()) {
                exists = true;
                oldThing.setItemAmount(oldThing.itemAmount + 1);
            }
        }
        if (!exists)
            itemInventory.add(newThing);

        return newThing;
    }
}
