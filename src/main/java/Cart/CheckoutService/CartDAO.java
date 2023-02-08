package Cart.CheckoutService;

import javax.sql.DataSource;

import Merchandising.MerchandiseService.*;
import User.AccountService.EndUserDAO;
import User.AccountService.EndUser;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class CartDAO {

    private DataSource ds;
    public CartDAO(DataSource ds){
        this.ds = ds;
    }

        //+addProduct(Manga manga,int quantity,EndUser user): void
        //+retrieveByUser(EndUser user): Cart
        //+updateProduct(Manga manga,int quantity,EndUser user): void
        //+removeProduct(Manga manga,EndUser user): void


    public HashMap<Manga,Integer> retrieveByUser(EndUser user) throws Exception{


        Connection conn = ds.getConnection();
        PreparedStatement prret = null;
        ResultSet rsret = null;

        try {
            prret = conn.prepareStatement("SELECT * FROM END_USER AS e WHERE e.usr_id=?");

            prret.setInt(1, user.getId());

            rsret = prret.executeQuery();

            if (rsret.next()) {
                ;
            } else {
                throw new Exception("utente non esistente");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }


        HashMap<Manga,Integer> mappa = new HashMap<Manga,Integer>();
        try{

            prret = conn.prepareStatement("SELECT m.id,m.name,m.editore,m.price,m.ISBN,m.quantity/* quantità del magazzino */,c.quantity /* quantità del carrello */,m.image FROM CART AS c,Manga AS m where c.user_id=? AND m.id=c.manga_id");
            prret.setInt(1,user.getId());
            rsret = prret.executeQuery();
            while(rsret.next()){
                int id = rsret.getInt(1);
                String name = rsret.getString(2);
                String brand = rsret.getString(3);
                double price = rsret.getDouble(4);
                String isbn = rsret.getString(5);
                int mangaQuantityFromWarehouse = rsret.getInt(6);
                int mangaQuantityFromCart= rsret.getInt(7);
                String imagep= rsret.getString(8);
                Manga m = new Manga(isbn,brand,"","","",0,null,id, name,"description", price,0.0,0.0,0.0, mangaQuantityFromWarehouse,"",imagep,null,null,"",null);
                mappa.put(m,mangaQuantityFromCart);
            }
            if(mappa.size()==0){
                throw new Exception("nessun Elemento presente nel carrello");
            }
            return mappa;

        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }finally {
            try{
                rsret.close();
                prret.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

    }
    public void removeProduct(Manga manga,EndUser user) throws Exception{


        Connection conn = ds.getConnection();

        PreparedStatement pr = null;
        ResultSet rs = null;

        pr = conn.prepareStatement("SELECT * FROM END_USER AS e WHERE e.usr_id=?");

        pr.setInt(1,user.getId());

        rs = pr.executeQuery();

        if(rs.next()){
            ;
        }else {
            throw new Exception("utente non esistente");
        }

        try {
            pr = conn.prepareStatement("SELECT * from Manga as m WHERE m.id=?");
            pr.setInt(1, manga.getId());

            rs = pr.executeQuery();
            if (rs.next()) {
                ;
            } else {
                throw new Exception("prodotto non esistente");
            }
        }catch (Exception e){
            throw e;
        }


        HashMap<Manga,Integer> ma = retrieveByUser(user);
        boolean b = false;
        for (Map.Entry <Manga, Integer> set : ma.entrySet()) {
            System.out.println(manga.getId());
            if(set.getKey().getId()==manga.getId()) {
                b = true;
                break;
            }
        }

        if(!b){
            throw new Exception("prodotto non inserito nel carrello di questo utente");
        }

        try{
            pr = conn.prepareStatement("DELETE FROM CART AS C WHERE C.user_id=? AND C.manga_id=?");
            pr.setInt(1,user.getId());
            pr.setInt(2,manga.getId());
            pr.executeUpdate();
            System.out.println("Dentro");
        }catch (SQLException e){
            System.out.println(e.getMessage() + 1);
        }finally {
            try{
                conn.close();
                pr.close();
            }catch (SQLException e){
                System.out.println(e.getMessage() + 2);
            }
        }
    }

    public void addProduct(Manga manga,int quantity,EndUser user) throws Exception{


        Connection conn = ds.getConnection();

        PreparedStatement pr = null;
        ResultSet rs = null;

        pr = conn.prepareStatement("SELECT * FROM END_USER AS e WHERE e.usr_id=?");

        pr.setInt(1,user.getId());

        rs = pr.executeQuery();

        if(rs.next()){
            ;
        }else {
            throw new Exception("utente non esistente");
        }

        try {
            pr = conn.prepareStatement("SELECT * from Manga as m WHERE m.id=?");
            pr.setInt(1, manga.getId());

            rs = pr.executeQuery();
            if (rs.next()) {
                ;
            } else {
                throw new Exception("prodotto non esistente");
            }
        }catch (Exception e){
            throw e;
        }



        if(quantity<=0 || quantity>manga.getQuantity()){
            throw new Exception("quantità inserita non valida");
        }

        try{
            HashMap<Manga,Integer> ma = retrieveByUser(user);
            for (Map.Entry <Manga, Integer> set : ma.entrySet()) {
                System.out.println(manga.getId());
                if(set.getKey().getId()==manga.getId()) {
                    updateProduct(manga,quantity,user);
                    return;
                }
            }
        }catch (Exception e){
            if(e.getMessage().equals("nessun Elemento presente nel carrello"));
            else{
                throw e;
            }
        }

        try{
            pr = conn.prepareStatement("INSERT INTO CART (user_id,manga_id,quantity) VALUES (?,?,?)");

            pr.setInt(1,user.getId());
            pr.setInt(2,manga.getId());
            pr.setInt(3,quantity);
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

    public void updateProduct(Manga manga,int quantity, EndUser user) throws Exception{


        Connection conn = ds.getConnection();

        PreparedStatement pr = null;
        ResultSet rs = null;

        pr = conn.prepareStatement("SELECT * FROM END_USER AS e WHERE e.usr_id=?");

        pr.setInt(1,user.getId());

        rs = pr.executeQuery();

        if(rs.next()){
            ;
        }else {
            throw new Exception("utente non esistente");
        }

        try {
            pr = conn.prepareStatement("SELECT * from Manga as m WHERE m.id=?");
            pr.setInt(1, manga.getId());

            rs = pr.executeQuery();
            if (rs.next()) {
                ;
            } else {
                throw new Exception("prodotto non esistente");
            }
        }catch (Exception e){
            throw e;
        }



        if(quantity<=0 || quantity>manga.getQuantity()){
            throw new Exception("quantità inserita non valida");
        }

        HashMap<Manga,Integer> mappa = retrieveByUser(user);

        boolean b = false;
        int qInCart=0;
        for (Map.Entry <Manga, Integer> set : mappa.entrySet()) {
            if(set.getKey().getId()==manga.getId()) {
                qInCart= set.getValue();
                b = true;
                break;
            }
        }

        if(!b){
            addProduct(manga,quantity,user);
            return;
        }


        try{

            pr = conn.prepareStatement("UPDATE CART SET quantity=? WHERE user_id=? AND manga_id=?");

            pr.setInt(1,quantity);
            pr.setInt(2,user.getId());
            pr.setInt(3,manga.getId());
            pr.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }finally {
            try{
                pr.close();
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }


    public void toEmptyCart(EndUser user) throws Exception {
        Connection conn =ds.getConnection();

        PreparedStatement pr = null;
        ResultSet rs = null;

        pr = conn.prepareStatement("SELECT * FROM END_USER AS e WHERE e.usr_id=?");

        pr.setInt(1,user.getId());

        rs = pr.executeQuery();

        if(rs.next()){
            ;
        }else {
            throw new Exception("utente non esistente");
        }

        try {
            pr = conn.prepareStatement("DELETE FROM CART AS C WHERE c.user_id=?");
            pr.setInt(1, user.getId());
            pr.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                pr.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
