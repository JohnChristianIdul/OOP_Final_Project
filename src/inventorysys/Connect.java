/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inventorysys;

/**
 *
 * @author cath1
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.*;
import javax.swing.*;


public class Connect {
    Connection conn;

    /*This function/method will connect to the database*/
    //This comment is just to test if I can push and pull from the repository
    public Connect() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbinventorysystem", "root", "");
        } catch (SQLException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*This method will display the item*/
    public ArrayList<Item> displayItem(int inventory_id){
        ArrayList<Item> item = new ArrayList<>();
        String sql = "SELECT * FROM item WHERE inventoryID = '" + inventory_id + "' AND isVisible = 1";
        Statement stmt;
        ResultSet rs;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);    
            while(rs.next()){
              Item a = new Item(rs.getInt(1), rs.getString(2), rs.getString(3),rs.getInt(5), rs.getInt(4));
              item.add(a);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return item;
    }
    
    public ArrayList<Item> hiddenItems(int inventory_id){
        ArrayList<Item> item = new ArrayList<>();
        String sql ="select * from item where inventoryID = '"+inventory_id+"' AND isVisible = 0";
        Statement stmt;
        ResultSet rs;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);    
            while(rs.next()){
              Item a = new Item(rs.getInt(1), rs.getString(2), rs.getString(3),rs.getInt(5), rs.getInt(4));
              item.add(a);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return item;
    }
    
    public ArrayList<Inventory> displayInventory(){
        ArrayList<Inventory> it = new ArrayList<>();
        String sql ="select * from inventory";
        Statement stmt;
        ResultSet rs;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);    
            while(rs.next()){
              Inventory invent = new Inventory(rs.getInt(1), rs.getString(3));
              it.add(invent);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return it;
    }
    
    public void addItem(Item i, String inventoryName, int inventoryID){
        Statement stmt;
        String sql=null;
        ResultSet rs;
        boolean check = checkExisting(i);
        try {
            stmt = conn.createStatement();
            sql = "select count(*) from item where inventoryID = " + inventoryID;
            rs = stmt.executeQuery(sql);
            if(rs.next()){
                int count = rs.getInt(1);
                if(check == true){
                    JOptionPane.showMessageDialog(null,"Item already exists.");
                    return;
                }
                if(count < getInventoryCapacity(inventoryID) || getInventoryCapacity(inventoryID) == -1){
                        sql = "Insert into item (itemName,itemDescription,itemQuantity,inventoryID)values('"+i.getItemName()+"','"+i.getDescription()+"',"+i.getQuantity()+","+inventoryID+")";
                        stmt.executeUpdate(sql);
                        JOptionPane.showMessageDialog(null,"Successfully added");
                } else{
                    JOptionPane.showMessageDialog(null,"Inventory Full");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteItem(Item item){
        Statement stmt;
        String sql = null;
        
        try{
            stmt = conn.createStatement();
            sql = "delete from item where itemID = '" +item.getItemID()+"'";
            stmt.executeUpdate(sql);
            JOptionPane.showMessageDialog(null,"Successfully deleted");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Cannot delete item");
        }
    }
    
    public void hideItem(Item item) {
        Statement stmt;
        String sql = null;
        
        try {
            stmt = conn.createStatement();
            sql = "UPDATE item SET isVisible = 0 WHERE itemID = '" + item.getItemID() +"'";
            stmt.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Successfully Hidden");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: Unable to hide item");
        }
    }
    
    public void showItem(Item item) {
        Statement stmt;
        String sql = null;
        
        try {
            stmt = conn.createStatement();
            sql = "UPDATE item SET isVisible = 1 WHERE itemID = '"+item.getItemID()+"'";
            stmt.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Successfully displayed");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Unable to display item");
        }
    }
    
    public int getInventoryCapacity(int inventoryid){
        Statement stmt;
        String sql = null;
        ResultSet rs;
        int limit = 0;
        try {
            stmt = conn.createStatement();
            sql = "Select inventoryID, inventory.subscriberID, subscriber.subscriptionType,subscription.inventoryLimit from inventory, subscriber, subscription where inventoryID = " + inventoryid +" AND inventory.subscriberID = subscriber.subscriberID AND subscriber.subscriptionType = subscription.subscriptionType;";
            rs = stmt.executeQuery(sql);

            if(rs.next()){
                String capacity = rs.getString("subscriptionType");
                if(capacity.equals("Basic Subscriber")){
                    limit = 20;
                }else{
                    limit = -1;
                }
                return limit;
            }
        } catch (SQLException e) {
            
        }
        return limit;
    }

    public boolean checkExisting(Item item){
        Statement stmt;
        String sql;
        ResultSet rs;

        try {
            stmt = conn.createStatement();
            sql = "SELECT * FROM item WHERE itemID = '"+ item.getItemID()+"'";
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        }catch (SQLException e) {
            System.err.println("SQL error occurred: " + e.getMessage());
        }
            return false;
       }

}
