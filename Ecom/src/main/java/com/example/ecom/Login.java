package com.example.ecom;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {
    private static byte[] getSha(String input){
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return md.digest(input.getBytes(StandardCharsets.UTF_8));
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private static String getEncryptedPassword(String password){
        try{
            BigInteger num=new BigInteger(1,getSha(password));
            StringBuilder ans=new StringBuilder(num.toString(16));
            return ans.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static Customer userLogin(String useremail,String password){
        String query="select * from customers where email = '" + useremail +"' and password = '" + password+"'";
        DatabaseConnection dbcon=new DatabaseConnection();
        try{
            ResultSet rs=dbcon.getQueryTable(query);
            if(rs!=null && rs.next()){
                return new Customer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
//        System.out.println(userLogin("khansardar@email.com","root1"));
        System.out.println(getEncryptedPassword("1"));
    }

}
