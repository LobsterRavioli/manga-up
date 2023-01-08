package Merchandising.MerchandiseService.service_layer.implementations;

import Merchandising.MerchandiseService.beans.Product;
import Utilities.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Merchandising.MerchandiseService.beans.Manga;

public class ProductDAO implements Merchandising.MerchandiseService.service_layer.interfaces.ProductDAO {

    @Override
    public void create(Product p) {
        PreparedStatement pr = null;
        try(Connection conn = ConnectionPool.getConnection()){
            pr = conn.prepareStatement("INSERT INTO Product VALUES (?,?,?,?,?,?,?,?,?,?,?)");
            pr.setString(1,p.getName());
            pr.setString(2,p.getProducer());
            pr.setDouble(3,p.getPrice());
            pr.setDouble(4,p.getWeight());
            pr.setDouble(5,p.getHeight());
            pr.setDouble(6,p.getLength());

            if(p.getState().equals(Product.ProductState.USED))
                pr.setString(7,"USED");
            else
                pr.setString(7,"NEW");

            pr.setString(8,p.getDescription());
            pr.setString(9,p.getType_of_product());
            pr.setString(10,p.getCollections());
            pr.setInt(11,p.getQuantity());
            pr.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally{
            try{
                pr.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(int id) {
        PreparedStatement pr = null;
        try(Connection conn = ConnectionPool.getConnection()){
            pr = conn.prepareStatement("DELETE FROM Product as p WHERE p.id=?");
            pr.setInt(1,id);
            pr.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try{
                pr.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(Product p) {
        PreparedStatement pr = null;
        try(Connection conn = ConnectionPool.getConnection()){
            pr = conn.prepareStatement("UPDATE Product SET name=?,brand=?,price=?,weight=?,height=?,lenght=?,state=?,description=?,type_of_product=?,collections=?,quantity=?)");
            pr.setString(1,p.getName());
            pr.setString(2,p.getProducer());
            pr.setDouble(3,p.getPrice());
            pr.setDouble(4,p.getWeight());
            pr.setDouble(5,p.getHeight());
            pr.setDouble(6,p.getLength());

            if(p.getState().equals(Product.ProductState.USED))
                pr.setString(7,"USED");
            else
                pr.setString(7,"NEW");

            pr.setString(8,p.getDescription());
            pr.setString(9,p.getType_of_product());
            pr.setString(10,p.getCollections());
            pr.setInt(11,p.getQuantity());
            pr.setString(12,p.getImagePath());
            pr.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally{
            try{
                pr.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public Product retrieveById(int id) {
        PreparedStatement pr = null;
        ResultSet rs = null;
        try(Connection conn = ConnectionPool.getConnection()){
            pr = conn.prepareStatement("SELECT * from Product as p WHERE p.id=?");
            pr.setInt(1,id);
            rs = pr.executeQuery();
            if(rs.next()){
                int iD = rs.getInt(1);
                String name = rs.getString(2);
                String brand = rs.getString(3);
                double price = rs.getDouble(4);
                double weight = rs.getDouble(5);
                double height = rs.getDouble(6);
                double lenght = rs.getDouble(7);
                String type = rs.getString(8);

                Product.ProductState pS;

                if (type.equals("NEW"))
                    pS = Product.ProductState.NEW;
                else
                    pS = Product.ProductState.USED;

                String description = rs.getString(9);
                String type_of_p = rs.getString(10);
                String collections = rs.getString(11);
                int quantity = rs.getInt(12);
                String imagePath = rs.getString(13);
                Product p = new Product(iD,name,brand,description,price,height,lenght,weight,pS,type_of_p,collections,quantity,imagePath);
                return p;
            }else
                return null;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }finally {
            try{
                rs.close();
                pr.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public ArrayList<Product> retrieveByName(String n){
        PreparedStatement pr = null;
        ResultSet rs = null;
        try(Connection conn = ConnectionPool.getConnection()){
            pr = conn.prepareStatement("SELECT * from Product as p WHERE p.name=?");
            pr.setString(1,n);
            rs = pr.executeQuery();
            ArrayList<Product> lista = new ArrayList<Product>();
            while(rs.next()){
                int iD = rs.getInt(1);
                String name = rs.getString(2);
                String brand = rs.getString(3);
                double price = rs.getDouble(4);
                double weight = rs.getDouble(5);
                double height = rs.getDouble(6);
                double lenght = rs.getDouble(7);
                String type = rs.getString(8);

                Product.ProductState pS;

                if (type.equals("NEW"))
                    pS = Product.ProductState.NEW;
                else
                    pS = Product.ProductState.USED;

                String description = rs.getString(9);
                String type_of_p = rs.getString(10);
                String collections = rs.getString(11);
                int quantity = rs.getInt(12);
                String imagePath = rs.getString(13);
                Product p = new Product(iD,name,brand,description,price,height,lenght,weight,pS,type_of_p,collections,quantity,imagePath);
                lista.add(p);
            }
            if(lista.size()==0)
                return null;

            else return lista;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }finally {
            try{
                rs.close();
                pr.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }


    public ArrayList<Product> retrieveAll(){
        PreparedStatement pr = null;
        ResultSet rs = null;
        try(Connection conn = ConnectionPool.getConnection()){
            pr = conn.prepareStatement("SELECT * from Product as p");
            rs = pr.executeQuery();
            ArrayList<Product> lista = new ArrayList<Product>();
            while(rs.next()){
                int iD = rs.getInt(1);
                String name = rs.getString(2);
                String brand = rs.getString(3);
                double price = rs.getDouble(4);
                double weight = rs.getDouble(5);
                double height = rs.getDouble(6);
                double lenght = rs.getDouble(7);
                String type = rs.getString(8);

                Product.ProductState pS;

                if (type.equals("NEW"))
                    pS = Product.ProductState.NEW;
                else
                    pS = Product.ProductState.USED;

                String description = rs.getString(9);
                String type_of_p = rs.getString(10);
                String collections = rs.getString(11);
                int quantity = rs.getInt(12);
                String imagePath = rs.getString(13);
                Product p = new Product(iD,name,brand,description,price,height,lenght,weight,pS,type_of_p,collections,quantity,imagePath);
                lista.add(p);
            }
            if(lista.size()==0)
                return null;

            else return lista;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }finally {
            try{
                rs.close();
                pr.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public ArrayList<Product> retrieveByPrice(double priceStart,double priceEnd){
        PreparedStatement pr = null;
        ResultSet rs = null;
        try(Connection conn = ConnectionPool.getConnection()){
            if(priceStart<0) {
                pr = conn.prepareStatement("SELECT * from Product as p WHERE p.price<?");
                pr.setDouble(1, priceEnd);
            }
            else if (priceEnd<0){
                pr = conn.prepareStatement("SELECT * from Product as p WHERE p.price>?");
                pr.setDouble(1,priceStart);
            }
            else if(0<priceStart && 0<priceEnd) {
                pr = conn.prepareStatement("SELECT * from Product as p WHERE p.price BETWEEN ? AND ?");
                pr.setDouble(1, priceStart);
                pr.setDouble(2, priceEnd);
            }else{
                System.out.println("Deve essere fornito almeno uno dei due prezzi tra quello di partenza e quello limite");
                return null;
            }
            rs = pr.executeQuery();
            ArrayList<Product> lista = new ArrayList<Product>();
            while(rs.next()){
                int iD = rs.getInt(1);
                String name = rs.getString(2);
                String brand = rs.getString(3);
                double price = rs.getDouble(4);
                double weight = rs.getDouble(5);
                double height = rs.getDouble(6);
                double lenght = rs.getDouble(7);
                String type = rs.getString(8);

                Product.ProductState pS;

                if (type.equals("NEW"))
                    pS = Product.ProductState.NEW;
                else
                    pS = Product.ProductState.USED;

                String description = rs.getString(9);
                String type_of_p = rs.getString(10);
                String collections = rs.getString(11);
                int quantity = rs.getInt(12);
                String imagePath = rs.getString(13);
                Product p = new Product(iD,name,brand,description,price,height,lenght,weight,pS,type_of_p,collections,quantity,imagePath);
                lista.add(p);
            }
            if(lista.size()==0)
                return null;

            else return lista;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }finally {
            try{
                rs.close();
                pr.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

}
