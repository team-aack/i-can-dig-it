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
        int pickTreasure = (int) Math.floor(Math.random() * 100);
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
            case 10: newThing = new Treasure("Cat",1); break;
            case 11: newThing = new Treasure("Harpoon",1); break;
            case 12: newThing = new Treasure("Amber Conville",1); break;
            case 13: newThing = new Treasure("Lamborghini",1); break;
            case 14: newThing = new Treasure("Henry Balanon",1); break;
            case 15: newThing = new Treasure("Harvey",1); break;
            case 16: newThing = new Treasure("Jeff Burt",1); break;
            case 17: newThing = new Treasure("Headphones",1); break;
            case 18: newThing = new Treasure("Galaxy S3",1); break;
            case 19: newThing = new Treasure("MacBook Pro",1); break;
            case 20: newThing = new Treasure("Netscape",1); break;
            case 21: newThing = new Treasure("Depleted Uranium",1); break;
            case 22: newThing = new Treasure("Fossil Fuel",1); break;
            case 23: newThing = new Treasure("Sand",1); break;
            case 24: newThing = new Treasure("Rock",1); break;
            case 25: newThing = new Treasure("Redbull",1); break;
            case 26: newThing = new Treasure("Funny Hat",1); break;
            case 27: newThing = new Treasure("Beer",1); break;
            case 28: newThing = new Treasure("Thermos",1); break;
            case 29: newThing = new Treasure("Labs Sticker",1); break;
            case 30: newThing = new Treasure("d4",1); break;
            case 31: newThing = new Treasure("d6",1); break;
            case 32: newThing = new Treasure("d8",1); break;
            case 33: newThing = new Treasure("d10",1); break;
            case 34: newThing = new Treasure("d12",1); break;
            case 35: newThing = new Treasure("d20",1); break;
            case 36: newThing = new Treasure("Cake",1); break;
            case 37: newThing = new Treasure("Taco",1); break;
            case 38: newThing = new Treasure("Burrito",1); break;
            case 39: newThing = new Treasure("Paper Towel",1); break;
            case 40: newThing = new Treasure("Kegerator",1); break;
            case 41: newThing = new Treasure("Keg",1); break;
            case 42: newThing = new Treasure("Garbage",1); break;
            case 43: newThing = new Treasure("Ghetto Blaster",1); break;
            case 44: newThing = new Treasure("iPhone 6",1); break;
            case 45: newThing = new Treasure("Moto X",1); break;
            case 46: newThing = new Treasure("Rope",1); break;
            case 47: newThing = new Treasure("Shirt",1); break;
            case 48: newThing = new Treasure("Velvet",1); break;
            case 49: newThing = new Treasure("Syringe",1); break;
            case 50: newThing = new Treasure("Domino",1); break;
            case 51: newThing = new Treasure("Thumb Tack",1); break;
            case 52: newThing = new Treasure("Car Phone",1); break;
            case 53: newThing = new Treasure("Chair",1); break;
            case 54: newThing = new Treasure("Toaster",1); break;
            case 55: newThing = new Treasure("Flask",1); break;
            case 56: newThing = new Treasure("Beaker",1); break;
            case 57: newThing = new Treasure("Ribuk's Cube",1); break;
            case 58: newThing = new Treasure("Guitar",1); break;
            case 59: newThing = new Treasure("Drum Kit",1); break;
            case 60: newThing = new Treasure("Shovel",1); break;
            case 61: newThing = new Treasure("Crave Case",1); break;
            case 62: newThing = new Treasure("Briefcase",1); break;
            case 63: newThing = new Treasure("Salt Shaker",1); break;
            case 64: newThing = new Treasure("American Coney",1); break;
            case 65: newThing = new Treasure("Laffayete Coney",1); break;
            case 66: newThing = new Treasure("Margarita",1); break;
            case 67: newThing = new Treasure("Sheet Music",1); break;
            case 68: newThing = new Treasure("Zip Drive",1); break;
            case 69: newThing = new Treasure("Zip Disk",1); break;
            case 70: newThing = new Treasure("Turing Machine",1); break;
            case 71: newThing = new Treasure("Magnifying Glass",1); break;
            case 72: newThing = new Treasure("Trumpet",1); break;
            case 73: newThing = new Treasure("Sushi",1); break;
            case 74: newThing = new Treasure("Spaghetti",1); break;
            case 75: newThing = new Treasure("Oatmeal",1); break;
            case 76: newThing = new Treasure("Mugen",1); break;
            case 77: newThing = new Treasure("Pumpkin",1); break;
            case 78: newThing = new Treasure("Candy Corn",1); break;
            case 79: newThing = new Treasure("Apple Pie",1); break;
            case 80: newThing = new Treasure("Pumpkin Pie",1); break;
            case 81: newThing = new Treasure("Pink Polo",1); break;
            case 82: newThing = new Treasure("Leather Jacket",1); break;
            case 83: newThing = new Treasure("SD Card",1); break;
            case 84: newThing = new Treasure("3D Printer",1); break;
            case 85: newThing = new Treasure("Gold",1); break;
            case 86: newThing = new Treasure("Silver",1); break;
            case 87: newThing = new Treasure("Copper",1); break;
            case 88: newThing = new Treasure("Clay",1); break;
            case 89: newThing = new Treasure("Graph Paper",1); break;
            case 90: newThing = new Treasure("Pencil",1); break;
            case 91: newThing = new Treasure("Pen",1); break;
            case 92: newThing = new Treasure("Paint Brush",1); break;
            case 93: newThing = new Treasure("Paint",1); break;
            case 94: newThing = new Treasure("Whiteboard",1); break;
            case 95: newThing = new Treasure("Television",1); break;
            case 96: newThing = new Treasure("iPad",1); break;
            case 97: newThing = new Treasure("Table",1); break;
            case 98: newThing = new Treasure("Slows",1); break;
            case 99: newThing = new Treasure("Thunderbold Display",1); break;
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
