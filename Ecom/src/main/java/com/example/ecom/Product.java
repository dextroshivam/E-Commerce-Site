package com.example.ecom;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class Product {

    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleDoubleProperty price;

    public Product(int id,String name,double price) {
        this.id=new SimpleIntegerProperty(id);
        this.name=new SimpleStringProperty(name);
        this.price=new SimpleDoubleProperty(price);
    }

    public int getId(){
        return id.get();
    }
    public String getName(){
        return name.get();
    }
    public double getPrice(){
        return price.get();
    }

    public static ObservableList<Product> getAllProducts() {
        String getProductsQuery="select * from products";
        return getProducts(getProductsQuery);
    }

    public static ObservableList<Product> getProducts(String query){
        DatabaseConnection dbcon=new DatabaseConnection();
        ResultSet rs=dbcon.getQueryTable(query);
        ObservableList<Product> result= FXCollections.observableArrayList();
        try{
            if(rs!=null){
                while (rs.next()){
                    result.add(new Product(
                            rs.getInt("id"),rs.getString("name"),rs.getDouble("price")
                    ));
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
