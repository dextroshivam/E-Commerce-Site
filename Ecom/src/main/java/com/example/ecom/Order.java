package com.example.ecom;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class Order {

    static TableView<Product> orderTable;
    public static boolean placeOrder(Customer customer,Product product){
        String placeOrderQuery="insert into orders (customer_id,product_id,status) values (" +customer.getId()+ "," +product.getId()+",'ordered')";
        try{
            DatabaseConnection dbcon=new DatabaseConnection();
            System.out.println(customer.getId()+" " + product.getId());
            return dbcon.insertUpdate(placeOrderQuery);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  false;
    }
    public static  int placeBulkOrder(ObservableList<Product> productObservableList , Customer customer){
        int count=0;
        for(Product product:productObservableList){
            if(placeOrder(customer,product))
                count++;
        }
        return  count;
    }
    public static Pane getOrders(){
        ObservableList<Product> productList=Product.getAllProducts();
        return  createTableFromList(productList);
    }

    public static Pane createTableFromList(ObservableList<Product> orderList){
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
        orderTable=new TableView<>();
        orderTable.setItems(orderList);
        orderTable.getColumns().addAll(idCol,name,price);

        Pane tablePane=new Pane();
        tablePane.getChildren().add(orderTable);
        return tablePane;
    }
    public static Pane getOrders(Customer customer){
        String ordersQuery="select orders.order_id, products.name,products.price from orders inner join products on orders.product_id=products.id where customer_id="+customer.getId()+"";
        ObservableList<Product> orderList=Product.getProducts(ordersQuery);
        return createTableFromList(orderList);
    }
}
