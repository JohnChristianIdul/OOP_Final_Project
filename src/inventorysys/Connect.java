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
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventorysystem", "root", "");
        } catch (SQLException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*This method will display the item*/
    public ArrayList<Item> displayItem(String inventoryName){
        ArrayList<Item> item = new ArrayList<>();
        String sql ="select * from " + inventoryName + " where status = 1";
        Statement stmt;
        ResultSet rs;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);    
            while(rs.next()){
              Item a = new Item(rs.getInt(1), rs.getInt(2), rs.getString(3),rs.getInt(5), rs.getString(4));
              item.add(a);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return item;
    }
    
    public ArrayList<Item> hiddenItems(String inventoryName){
        ArrayList<Item> item = new ArrayList<>();
        String sql ="select * from " + inventoryName + " where status = 0";
        Statement stmt;
        ResultSet rs;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);    
            while(rs.next()){
              Item a = new Item(rs.getInt(1), rs.getInt(2), rs.getString(3),rs.getInt(5), rs.getString(4));
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
              Inventory invent = new Inventory(rs.getInt(1), rs.getString(2));
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
        
        try {
            stmt = conn.createStatement();
            sql = "select count(*) from " + inventoryName;
            rs = stmt.executeQuery(sql);
            if(rs.next()){
                int count = rs.getInt(1);
                if(count < getInventoryCapacity(inventoryID)){
                    sql = "Insert into " +inventoryName+ " (inventoryID,itemName,description,quantity)values('"+inventoryID+"','"+i.getItemName()+"','"+i.getDescription()+"','"+i.getQuantity()+"')";
                    stmt.executeUpdate(sql);
                    JOptionPane.showMessageDialog(null,"Successfully added");
                }else{
                    JOptionPane.showMessageDialog(null,"Inventory Full");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteItem(Item item, String inventoryName){
        Statement stmt;
        String sql = null;
        
        try{
            stmt = conn.createStatement();
            sql = "delete from " +inventoryName+ " where itemname = '" +item.getItemName()+"'";
            stmt.executeUpdate(sql);
            JOptionPane.showMessageDialog(null,"Successfully deleted");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Cannot delete item");
        }
    }
    
    public void hideItem(Item item, String inventoryName) {
        Statement stmt;
        String sql = null;
        
        try {
            stmt = conn.createStatement();
            sql = "UPDATE " + inventoryName + " SET status = 0 WHERE itemname = '" + item.getItemName() +"'";
            stmt.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Successfully Hidden");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: Unable to hide item");
        }
    }
    
    public void showItem(Item item, String inventoryName) {
        Statement stmt;
        String sql = null;
        
        try {
            stmt = conn.createStatement();
            sql = "UPDATE " + inventoryName + " SET status = 1 WHERE itemname = '" + item.getItemName() +"'";
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
        
        try {
            stmt = conn.createStatement();
            sql = "Select capacity from inventory where inventoryid = '" +inventoryid+"'";
            rs = stmt.executeQuery(sql);
            if(rs.next()){
                return rs.getInt(1);
            }
        } catch (SQLException e) {
        }
        return 0;
    }
}
