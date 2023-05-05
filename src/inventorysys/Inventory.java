/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inventorysys;

/**
 *
 * @author cath1
 */
public class Inventory {
    private int inventoryID;
    private String inventoryName;

    public Inventory(int inventoryID, String inventoryName) {
        this.inventoryID = inventoryID;
        this.inventoryName = inventoryName;
    }

    public int getInventoryID() {
        return inventoryID;
    }

    public void setInventoryID(int inventoryID) {
        this.inventoryID = inventoryID;
    }

    public String getInventoryName() {
        return inventoryName;
    }

    public void setInventoryName(String inventoryName) {
        this.inventoryName = inventoryName;
    }
}
