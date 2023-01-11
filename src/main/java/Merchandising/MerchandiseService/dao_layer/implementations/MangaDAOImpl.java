package Merchandising.MerchandiseService.dao_layer.implementations;

import Merchandising.MerchandiseService.beans.Manga;
import Merchandising.MerchandiseService.beans.Product;
import Merchandising.MerchandiseService.dao_layer.interfaces.MangaDAO;
import Utilities.ConnectionPool;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

public class MangaDAOImpl implements MangaDAO {

    private DataSource ds;
    public MangaDAOImpl(DataSource ds){
        this.ds = ds;
    }
    @Override
    public void create(Manga manga) {
        PreparedStatement pr = null;
        try(Connection conn = ds.getConnection()){
            pr = conn.prepareStatement("INSERT INTO Manga VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            pr.setString(1,manga.getName());
            pr.setString(2,manga.getBrand());
            pr.setDouble(3,manga.getPrice());
            pr.setDouble(4,manga.getWeight());
            pr.setDouble(5,manga.getHeight());
            pr.setDouble(6,manga.getLength());

            if(manga.getState().equals(Product.ProductState.USED))
                pr.setString(7,"USED");
            else
                pr.setString(7,"NEW");

            pr.setString(8,manga.getDescription());
            pr.setString(9,manga.getCollections());
            pr.setInt(10,manga.getQuantity());
            pr.setString(11,manga.getIsbn());
            pr.setString(12,manga.getBinding());
            pr.setString(13,manga.getVolume());
            pr.setDate(14,manga.getExitDate());
            pr.setInt(15,manga.getPages());
            pr.setString(16,manga.getInterior());
            pr.setString(17,manga.getLanguage());
            pr.setString(18,manga.getImagePath());

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
            pr = conn.prepareStatement("DELETE FROM Manga as p WHERE p.id=?");
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
    public void update(Manga p) {
        PreparedStatement pr = null;
        try(Connection conn = ds.getConnection()){
            pr = conn.prepareStatement("UPDATE Manga SET name=?,brand=?,price=?,weight=?,height=?,lenght=?,state=?,description=?,collections=?,quantity=?,ISBN=?,book_binding=?,volume=?,release_date=?,page_number=?,interior=?,lang=?,image=?)");
            pr.setString(1,p.getName());
            pr.setString(2,p.getBrand());
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
            pr.setString(11,p.getIsbn());
            pr.setString(12,p.getBinding());
            pr.setString(13,p.getVolume());
            pr.setDate(14,p.getExitDate());
            pr.setInt(15,p.getPages());
            pr.setString(16,p.getInterior());
            pr.setString(17,p.getLanguage());
            pr.setString(18,p.getImagePath());
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
    public Manga retrieveById(int id) {
        PreparedStatement pr = null;
        ResultSet rs = null;
        try(Connection conn = ds.getConnection()){
            pr = conn.prepareStatement("SELECT * from Manga as m WHERE m.id=?");
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
                String state = rs.getString(8);

                Product.ProductState pS;

                if (state.equals("NEW"))
                    pS = Product.ProductState.NEW;
                else
                    pS = Product.ProductState.USED;

                String description = rs.getString(9);
                String collections = rs.getString(10);
                int quantity = rs.getInt(11);
                String isbn = rs.getString(12);
                String binding = rs.getString(13);
                String volume = rs.getString(14);
                Date exit_date = rs.getDate(15);
                int page = rs.getInt(16);
                String interior = rs.getString(17);
                String language = rs.getString(18);
                String imapePath = rs.getString(19);

                Manga p = new Manga(isbn,brand,binding,language,volume,page,exit_date,iD,name,description,price,height,lenght,weight,collections,quantity,pS,interior,imapePath);
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
    public ArrayList<Manga> retrieveByName(String n){
        PreparedStatement pr = null;
        ResultSet rs = null;
        try(Connection conn = ds.getConnection()){
            pr = conn.prepareStatement("SELECT * from Manga as m WHERE m.name=?");
            pr.setString(1,n);
            rs = pr.executeQuery();
            ArrayList<Manga> lista = new ArrayList<Manga>();
            while(rs.next()) {
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
                String isbn = rs.getString(12);
                String binding = rs.getString(13);
                String volume = rs.getString(14);
                Date exit_date = rs.getDate(15);
                int page = rs.getInt(16);
                String interior = rs.getString(17);
                String language = rs.getString(18);
                String imapePath = rs.getString(19);

                Manga p = new Manga(isbn, brand, binding, language, volume, page, exit_date, iD, name, description, price, height, lenght, weight, collections, quantity, pS, interior, imapePath);
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


    public ArrayList<Manga> retrieveAll(){
        PreparedStatement pr = null;
        ResultSet rs = null;
        try(Connection conn = ds.getConnection()){
            pr = conn.prepareStatement("SELECT * from Manga as m");
            rs = pr.executeQuery();
            ArrayList<Manga> lista = new ArrayList<Manga>();
            while(rs.next()) {
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
                String isbn = rs.getString(12);
                String binding = rs.getString(13);
                String volume = rs.getString(14);
                Date exit_date = rs.getDate(15);
                int page = rs.getInt(16);
                String interior = rs.getString(17);
                String language = rs.getString(18);
                String imapePath = rs.getString(19);

                Manga p = new Manga(isbn, brand, binding, language, volume, page, exit_date, iD, name, description, price, height, lenght, weight, collections, quantity, pS, interior, imapePath);
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
    public ArrayList<Manga> retrieveByPrice(double priceStart,double priceEnd){
        PreparedStatement pr = null;
        ResultSet rs = null;
        try(Connection conn = ds.getConnection()){
            if(priceStart<0) {
                pr = conn.prepareStatement("SELECT * from Manga as m WHERE m.price<?");
                pr.setDouble(1, priceEnd);
            }
            else if (priceEnd<0){
                pr = conn.prepareStatement("SELECT * from Manga as m WHERE m.price>?");
                pr.setDouble(1,priceStart);
            }
            else if(0<priceStart && 0<priceEnd) {
                pr = conn.prepareStatement("SELECT * from Manga as m WHERE m.price BETWEEN ? AND ?");
                pr.setDouble(1, priceStart);
                pr.setDouble(2, priceEnd);
            }else{
                System.out.println("Deve essere fornito almeno uno dei due prezzi tra quello di partenza e quello limite");
                return null;
            }
            rs = pr.executeQuery();
            ArrayList<Manga> lista = new ArrayList<Manga>();
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
                String collections = rs.getString(10);
                int quantity = rs.getInt(11);
                String isbn = rs.getString(12);
                String binding = rs.getString(13);
                String volume = rs.getString(14);
                Date exit_date = rs.getDate(15);
                int page = rs.getInt(16);
                String interior = rs.getString(17);
                String language = rs.getString(18);
                String imapePath = rs.getString(19);

                Manga p = new Manga(isbn,brand,binding,language,volume,page,exit_date,iD,name,description,price,height,lenght,weight,collections,quantity,pS,interior,imapePath);
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
