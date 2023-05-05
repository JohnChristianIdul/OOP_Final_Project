/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inventorysys;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author cath1
 */
public class Item {
    private int itemID;
    private int inventoryID;
    private String itemName;
    private int Quantity;
    private String Description;

    
    public Item(){
    }
     
    public Item(String itemName, int Quantity, String Description) {
        this.itemName = itemName;
        this.Quantity = Quantity;
        this.Description = Description;
    }

    public Item(int Id,int inventoryID, String itemName, int quantity, String Description) {
        this.itemID = Id;
        this.inventoryID = inventoryID;
        this.itemName = itemName;
        this.Quantity = quantity;
        this.Description = Description;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public int getInventoryID() {
        return inventoryID;
    }

    public void setInventoryID(int inventoryID) {
        this.inventoryID = inventoryID;
    }
    
}
