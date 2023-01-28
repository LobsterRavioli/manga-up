package Cart.CheckoutService;

import javax.sql.DataSource;

import Merchandising.MerchandiseService.Manga;
import Merchandising.MerchandiseService.MangaDAO;
import Merchandising.MerchandiseService.ProductState;
import User.AccountService.EndUserDAO;
import User.AccountService.EndUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

        EndUserDAO ed = new EndUserDAO(ds);

        if(ed.findById(user.getId())==null)
            throw new Exception();

        PreparedStatement pr = null;
        Manga actual = null;
        ResultSet rs = null;
        MangaDAO man = new MangaDAO(ds);
        HashMap<Manga,Integer> mappa = new HashMap<Manga,Integer>();
        try(Connection conn = ds.getConnection()){
            pr = conn.prepareStatement("SELECT m.id,m.name,m.editore,m.price,m.ISBN,m.quantity,c.quantity,m.image FROM CART AS c,Manga AS m where c.user_id=? AND m.id=c.manga_id");
            pr.setInt(1,user.getId());
            rs = pr.executeQuery();
            while(rs.next()){
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String brand = rs.getString(3);
                double price = rs.getDouble(4);
                String isbn = rs.getString(5);
                int quantitym = rs.getInt(6);
                int quantityc= rs.getInt(7);

                if(quantityc>quantitym){
                    int temp = quantityc;
                    quantityc=quantitym;
                    updateProduct(new Manga(id),quantityc-temp,user);
                }
                String imagep= rs.getString(8);
                Manga m = new Manga(isbn,brand,"","","",0,null,id, name,"description", price,0.0,0.0,0.0, quantitym,"",imagep,null,null,"",null);
                mappa.put(m,quantityc);
                System.out.println("Sono qui");
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
                rs.close();
                pr.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

    }
    public void removeProduct(Manga manga,EndUser user) throws Exception{
        EndUserDAO eD = new EndUserDAO(ds);
        MangaDAO m = new MangaDAO(ds);

        if(eD.findById(user.getId())==null){
            throw new Exception("utente non esistente");
        }

        if(m.retrieveById(manga.getId())==null)
            throw new Exception("prodotto non esistente");

        HashMap<Manga,Integer> mappa = retrieveByUser(user);
        boolean b = false;
        for (Map.Entry <Manga, Integer> set : mappa.entrySet()) {
            if(set.getKey().getId()!=manga.getId()) {
                b = true;
                break;
            }
        }

        if(!b){
            throw new Exception("prodotto non inserito nel carrello di questo utente");
        }

        PreparedStatement pr = null;
        ResultSet rs = null;
        try(Connection conn = ds.getConnection()){
            pr = conn.prepareStatement("DELETE FROM CART AS C WHERE C.user_id=? AND C.manga_id=?");
            pr.setInt(1,user.getId());
            pr.setInt(2,manga.getId());
            pr.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try{
                rs.close();
                pr.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void addProduct(Manga manga,int quantity,EndUser user) throws Exception{
        EndUserDAO eD = new EndUserDAO(ds);
        MangaDAO m = new MangaDAO(ds);


        if(eD.findById(user.getId())==null){
            throw new Exception("utente non esistente");
        }

        if(m.retrieveById(manga.getId())==null)
            throw new Exception("prodotto non esistente");


        if(quantity<0 || quantity>manga.getQuantity()){
            throw new Exception("quantità inserita non valida");
        }


        PreparedStatement pr = null;
        ResultSet rs = null;
        try(Connection conn = ds.getConnection()){
            pr = conn.prepareStatement("INSERT INTO CART (user_id,manga_id,quantity) VALUES (?,?,?)");

            pr.setInt(1,user.getId());
            pr.setInt(2,manga.getId());
            pr.setInt(3,quantity);
            pr.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try{
                rs.close();
                pr.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void updateProduct(Manga manga,int quantity, EndUser user) throws Exception{

        EndUserDAO eD = new EndUserDAO(ds);
        MangaDAO m = new MangaDAO(ds);

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

        if(eD.findById(user.getId())==null){
            throw new Exception("utente non esistente");
        }

        if(m.retrieveById(manga.getId())==null)
            throw new Exception("prodotto non esistente");

        if(quantity==0){
            removeProduct(manga,user);
            return;
        }

        if(quantity>manga.getQuantity()){
            throw new Exception("quantità inserita non valida");
        }



        PreparedStatement pr = null;
        ResultSet rs = null;
        try(Connection conn = ds.getConnection()){

            pr = conn.prepareStatement("UPDATE CART SET quantity=? WHERE user_id=? AND manga_id=?");

            pr.setInt(1,quantity);
            pr.setInt(2,user.getId());
            pr.setInt(3,manga.getId());
            pr.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try{
                rs.close();
                pr.close();
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public void toEmptyCart(EndUser user) throws Exception {
        EndUserDAO ed = new EndUserDAO(ds);

        if (ed.findById(user.getId()) == null)
            throw new Exception("utente non esistente");

        PreparedStatement pr = null;
        ResultSet rs = null;
        HashMap<Manga, Integer> mappa = new HashMap<Manga, Integer>();
        try (Connection conn = ds.getConnection()) {
            pr = conn.prepareStatement("DELTE FROM CART AS C WHERE c.user_id=?");
            pr.setInt(1, user.getId());
            rs = pr.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                pr.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
