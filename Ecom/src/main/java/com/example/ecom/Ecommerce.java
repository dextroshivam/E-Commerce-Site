package com.example.ecom;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class Ecommerce extends Application {
    private final int width=600,height=600,headerLine=10;
    ProductsList productsList=new ProductsList();
    Pane bodyPane;
    Button signInButton=new Button("Sign In");
    Label welcomeLable=new Label("Welcome Customer!");
    Customer loggedInCustomer = null;
    ObservableList<Product> cartItemList= FXCollections.observableArrayList();
    private void addItemsToCart(Product product){
        if(cartItemList.contains(product))return;
        cartItemList.add(product);
        System.out.println("Products in Cart : " + cartItemList.stream().count());
    }

    private GridPane headerBar(){
        GridPane header=new GridPane();
        TextField searchbar=new TextField();
        Button searchButton = new Button("search");
        Button cartButton=new Button("Cart");
        Button showOrders=new Button("Your Orders");


        header.add(searchbar,0,0);
        header.add(searchButton,1,0);
        header.setHgap(10);
        header.add(signInButton,2,0);
        header.add(welcomeLable,3,0);
        header.add(cartButton,4,0);
        header.add(showOrders,5,0);

        showOrders.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                    bodyPane.getChildren().clear();
                    bodyPane.getChildren().add(Order.getOrders());

            }
        });
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(productsList.getAllProducts());
            }
        });
        signInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(loginPane());
            }
        });
        cartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(productsList.itemsInCart(cartItemList));
            }
        });
        return header;
    }
    private GridPane loginPane(){
        Label usernameLable=new Label("Username :");
        Label passwordLable=new Label("Password :");
        TextField usernameTextField=new TextField();
        usernameTextField.setPromptText("Enter Username");
        PasswordField passwordField=new PasswordField();
        passwordField.setPromptText("Enter your password");
        Button loginButton=new Button("Login");
        Label loginLableMessage=new Label("Login Here ");

//        checking login of a user
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String username=usernameTextField.getText();
                String password=passwordField.getText();
                loggedInCustomer=Login.userLogin(username,password);
                if(loggedInCustomer!= null){
                    loginLableMessage.setText("Login Successfull");
                    welcomeLable.setText("Welcome " + loggedInCustomer.getName());
                }else{
                    loginLableMessage.setText("Login Failed");
                }
            }
        });

        GridPane loginPane=new GridPane();
        loginPane.setTranslateY(100);
        loginPane.setTranslateX(50);
        loginPane.setVgap(10);
        loginPane.setHgap(10);
        loginPane.add(usernameLable,0,0);
        loginPane.add(passwordLable,0,1);
        loginPane.add(usernameTextField,1,0);
        loginPane.add(passwordField,1,1);
        loginPane.add(loginButton,0,2);
        loginPane.add(loginLableMessage,1,2);
        return loginPane;
    }
    private GridPane footerPane(){
        Button buyNowButton=new Button("Buy Now ");
        Button addToCartButton=new Button("Add to Cart");
        Button placeOrderButton=new Button("Place Order");
        GridPane footer = new GridPane();
        footer.setHgap(5);
        footer.setTranslateY(500);
        footer.add(buyNowButton, 0, 0);
        footer.add(addToCartButton,1,0);
        footer.add(placeOrderButton,2,0);

        buyNowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product = productsList.getSelectedProduct();
                boolean orderStatus=false;
                System.out.println(loggedInCustomer.getId()+" "+product.getId());
                if(loggedInCustomer!=null && product!=null){
                    orderStatus= Order.placeOrder(loggedInCustomer,product);
                }
                if(orderStatus){
                    showDailog("Order Placed Succesfully!");
                }else{

                }
            }
        });
        addToCartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product selectedProduct=productsList.getSelectedProduct();
                addItemsToCart(selectedProduct);
            }
        });
        placeOrderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int OrderCount=0;
                if(!cartItemList.isEmpty() && loggedInCustomer!=null)
                    OrderCount=Order.placeBulkOrder(cartItemList,loggedInCustomer);
                if(OrderCount>0){
                    showDailog(OrderCount + " Orders Placed Successfully");
                }
            }
        });
        return footer;
    }
    private Pane createContent(){
        Pane root=new Pane();
        root.setPrefSize(width,height+headerLine*2);

        bodyPane=new Pane();
        bodyPane.setPrefSize(width,height);
        bodyPane.setTranslateY(50);
        bodyPane.setTranslateX(10);
        bodyPane.getChildren().add(loginPane());
        root.getChildren().addAll(headerBar(),
                bodyPane,
                footerPane()
        );
        return root;
    }

    private void showDailog(String message){
        Dialog<String> dialog=new Dialog<>();
        dialog.setTitle("Order Status");
        ButtonType type=new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.setContentText(message);
        dialog.getDialogPane().getButtonTypes().add(type);
        dialog.showAndWait();
    }
    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(Ecommerce.class.getResource("hello-view.fxml"));
        
        Scene scene = new Scene(createContent(), Color.BLACK);
        stage.setTitle("E-Commerce");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}