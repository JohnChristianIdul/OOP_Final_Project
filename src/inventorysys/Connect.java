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
        System.out.println(check);
        try {
            stmt = conn.createStatement();
            sql = "select count(*) from item where inventoryID = " + inventoryID;
            rs = stmt.executeQuery(sql);
            int limit = getInventoryCapacity(inventoryID);
            System.out.println(limit);
            if(rs.next()){
                int count = rs.getInt(1);
                System.out.println(count);
                if(check == true){
                    JOptionPane.showMessageDialog(null,"Item already exists.");
                    return;
                } else if(count < getInventoryCapacity(inventoryID) || getInventoryCapacity(inventoryID) == -1){
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
                if(capacity.equals("Basic")){
                    limit = 20;
                    return limit;
                }else{
                    limit = -1;
                    return limit;
                }
            }
        } catch (SQLException e) {
            
        }
        return 0;
    }

    public boolean checkExisting(Item item){
        Statement stmt;
        String sql;
        ResultSet rs;

        try {
            stmt = conn.createStatement();
            sql = "SELECT * FROM item WHERE itemName = '"+ item.getItemName()+"'";
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

    public ArrayList<Item1> displayItem(){
        ArrayList<Item1> item = new ArrayList<>();
        String sql ="select * from item";
        Statement stmt;
        ResultSet rs;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()){
              Item1 a;
              a = new Item1(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getInt(5),rs.getInt(6));
              item.add(a);
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return item;
    }

    public ArrayList<Item1> displaySearchItem(String searchItem) throws SQLException {
    ArrayList<Item1> item = new ArrayList<>();
    boolean flag = false;
    try {
        int id = Integer.parseInt(searchItem);
        String sql = "select * from item where itemID = '" + id + "' ";
        Statement stmt;
        ResultSet rs;

        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
            Item1 a;
            a = new Item1(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getInt(5),rs.getInt(6));
            item.add(a);
            flag = true;
        }
        if(flag){
            JOptionPane.showMessageDialog(null, "Item is found");
        }else{
            JOptionPane.showMessageDialog(null, "Item not found");
        }
    } catch (NumberFormatException e) {
        String sql = "select * from item where itemName like '%" + searchItem + "%'";
        Statement stmt;
        ResultSet rs;

        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
            Item1 a;
            a = new Item1(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getInt(5),rs.getInt(6));
            item.add(a);
            flag = true;
        }
        if(flag){
            JOptionPane.showMessageDialog(null, "Item is found");
        }else{
            
            JOptionPane.showMessageDialog(null, "Item not found");
        }
    } catch (SQLException e) {
        Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, e);
    }
    return item; // Return the 'item' ArrayList instead of 'null'
    }
    
    public void updateItem(String origname, String origDescription,int origQuantity,String name, String description1, int quantity1) {
    String sql1 = "SELECT * FROM item WHERE itemname = '" + name + "'";
    String sql = "UPDATE item SET itemname = '" + name + "', itemdescription = '" + description1 + "', itemquantity = " + quantity1 + " WHERE itemname = '" + origname + "'";
    Statement stmt;

    try {
        stmt = conn.createStatement();
        ResultSet resultSet = stmt.executeQuery(sql1);
        
        
        if(origname.equals(name) && (origDescription == null || origDescription.equals(description1)) && origQuantity == quantity1){
            JOptionPane.showMessageDialog(null, "Nothing changed");
        }
        else if (!resultSet.next() || origname.equals(name)) { // If there's no record with the new name, proceed with the update
            stmt.executeUpdate(sql);
            JOptionPane.showMessageDialog(null,"Item updated!");
        }else{
            JOptionPane.showMessageDialog(null, "Name already exist!");
        }

    } catch (SQLException ex) {
        Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
    }
}      
    
}
