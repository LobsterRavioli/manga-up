package Merchandising.MerchandiseService.dao_layer.implementations;

import Merchandising.MerchandiseService.beans.Product;
import Merchandising.MerchandiseService.dao_layer.exceptions.ExceededLengthException;
import Merchandising.MerchandiseService.dao_layer.exceptions.InvalidQuantityException;
import Merchandising.MerchandiseService.dao_layer.exceptions.WrongRangeException;
import Merchandising.MerchandiseService.dao_layer.interfaces.ProductDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

public class ProductDAOImpl implements ProductDAO {

    private DataSource ds;
    public ProductDAOImpl(DataSource ds){
        this.ds=ds;
    }
    @Override
    public void create(Product p) throws ExceededLengthException,WrongRangeException,InvalidQuantityException{

        if(p.getName().length()>50 || p.getName().length()<1 || p.getProducer().length()>50 || p.getProducer().length()<1 || p.getDescription().length()>255)
            throw new ExceededLengthException();

        if(p.getPrice()<0 ||p.getWeight()<1 || p.getHeight()<1 || p.getLength()<1)
            throw new WrongRangeException();

        if(p.getQuantity()>50 || p.getQuantity()<1)
            throw new InvalidQuantityException();

        PreparedStatement pr = null;
        try(Connection conn = ds.getConnection()){
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
            pr.setString(9,p.getCollections());
            pr.setInt(10,p.getQuantity());
            pr.setString(11,p.getImagePath());
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
        try(Connection conn = ds.getConnection()){
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
    public void update(int id,int quantity)throws InvalidQuantityException {
        if(quantity>50 || quantity<1)
            throw new InvalidQuantityException();

        PreparedStatement pr = null;
        try(Connection conn = ds.getConnection()){
            pr = conn.prepareStatement("UPDATE Product as p SET p.quantity=? WHERE p.id=?)");
            pr.setInt(1,quantity);
            pr.setInt(2,id);
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
        try(Connection conn = ds.getConnection()){
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
                String collections = rs.getString(10);
                int quantity = rs.getInt(11);
                String imagePath = rs.getString(12);
                Product p = new Product(iD,name,brand,description,price,height,lenght,weight,pS,collections,quantity,imagePath);
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
    public ArrayList<Product> retrieveByFilters(String name,String collections,float min_price, float max_price, Product.ProductState ps) throws WrongRangeException{
        if(name==null && collections==null && min_price==0 && max_price==0 && ps==null)
            return retrieveAll();
        if(max_price>0 && max_price<min_price)
            throw new WrongRangeException();

        if(name==null)
            name="";

        if(collections==null)
            collections="";

        PreparedStatement pr = null;
        ResultSet rs = null;
        String state = "";
        String ricerca = null;
        try(Connection conn = ds.getConnection()) {
            if (ps != null) {
                if (ps.equals(Product.ProductState.USED))
                    state = "USED";
                else state = "NEW";
            }else{
                state="";
            }
            ricerca= "SELECT * FROM PRODUCT AS p WHERE p.name LIKE '%"+name+"%' AND p.collections LIKE '%"+collections+"%' AND p.price BETWEEN ? AND ";
            if(max_price<=0) {
                ricerca = ricerca + "99999999999 AND p.state LIKE '%"+state+"%'";
                pr = conn.prepareStatement(ricerca);
                if (min_price <= 0)
                    pr.setFloat(1, 0);
                else {
                    pr.setFloat(1, min_price);
                }
            }
            else{
                ricerca = ricerca+"? AND p.state LIKE '%"+state+"%'";
                pr = conn.prepareStatement(ricerca);
                if (min_price <= 0)
                    pr.setFloat(1, 0);
                else {
                    pr.setFloat(1, min_price);
                }
                pr.setFloat(2,max_price);
            }
            rs = pr.executeQuery();
            ArrayList<Product> lista = new ArrayList<Product>();

            while(rs.next()){
                int iD = rs.getInt(1);
                String nome = rs.getString(2);
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
                String collect = rs.getString(10);
                int quantity = rs.getInt(11);
                String imagePath = rs.getString(12);
                Product p = new Product(iD,nome,brand,description,price,height,lenght,weight,pS,collect,quantity,imagePath);
                lista.add(p);
            }
            System.out.println(lista.size());
            if(lista.size()==0) {
                return null;
            }


            else return lista;
        }catch (SQLException e){
            System.out.println(e.getMessage());
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
        try(Connection conn = ds.getConnection()){
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
                String collect = rs.getString(10);
                int quantity = rs.getInt(11);
                String imagePath = rs.getString(12);
                Product p = new Product(iD,name,brand,description,price,height,lenght,weight,pS,collect,quantity,imagePath);
                lista.add(p);
            }
            if(lista.size()==0) {
                return null;
            }
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

    /*@Override
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
                int quantity = rs.getInt(11);
                String imagePath = rs.getString(12);
                Product p = new Product(iD,name,brand,description,price,height,lenght,weight,pS,type_of_p,quantity,imagePath);
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
    }*/

}
