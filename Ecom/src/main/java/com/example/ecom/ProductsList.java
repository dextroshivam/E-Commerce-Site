package com.example.ecom;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class ProductsList {

    public TableView<Product> productTable;
    public Pane getAllProducts(){
        ObservableList<Product> productList=Product.getAllProducts();
        return  createTableFromList(productList);
    }

    public Pane createTableFromList(ObservableList<Product> productList){
        TableColumn idCol=new TableColumn("Id");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn name=new TableColumn("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn price=new TableColumn("Price");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
//        ObservableList<Product> data = FXCollections.observableArrayList();
//        data.addAll(
//                new Product(1,"shivam",(double)10000.43),
//                new Product(2,"yes",(double)10400.324),
//                new Product(3,"rakhi",(double)1032000.242)
//        );
        productTable=new TableView<>();
        productTable.setItems(productList);
        productTable.getColumns().addAll(idCol,name,price);

        Pane tablePane=new Pane();
        tablePane.getChildren().add(productTable);
        return tablePane;
    }
    public  Pane itemsInCart(ObservableList<Product> productList){
        return createTableFromList(productList);
    }
    public Product getSelectedProduct(){
        return productTable.getSelectionModel().getSelectedItem();
    }
}
