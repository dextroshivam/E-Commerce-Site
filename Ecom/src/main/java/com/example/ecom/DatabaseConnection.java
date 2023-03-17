package com.example.ecom;
import java.sql.*;

public class DatabaseConnection {
    String dbmsUrl="jdbc:mysql://localhost:3306/ecom";
    String username="root";
    String password="root";

    private Statement getStatement(){
        try {
            Connection conn = DriverManager.getConnection(dbmsUrl, username, password);
            return conn.createStatement();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet getQueryTable(String query){
        Statement statement=getStatement();
        try{
            return statement.executeQuery(query);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public boolean insertUpdate(String query){
        Statement statement=getStatement();
        try{
            statement.executeUpdate(query);
            return  true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    public static void main(String[] args) {
        DatabaseConnection dbconn=new DatabaseConnection();
        String query="select * from user";
        ResultSet rs=dbconn.getQueryTable(query);

        if(rs!=null){
            System.out.println("Connection made successfully");
        }
    }
}
