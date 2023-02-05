package Merchandising.MerchandiseService;

import Utilities.ConnectionPool;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;


public class MangaDAO {

    private DataSource ds;
    public MangaDAO(DataSource ds){
        this.ds = ds;
    }
    public void create(Manga manga) throws Exception {
        Connection conn = ds.getConnection();
        PreparedStatement pr = null;
        ResultSet rs = null;

        if(manga == null)
            throw new Exception("il manga passato come parametro è nullo");

        if(manga.getName()==null || manga.getName().equals("") || manga.getName().length()>50)
            throw new Exception("il nome del manga è nullo");


            try {
                pr = conn.prepareStatement("SELECT * from Manga as m WHERE m.name=?");
                pr.setString(1, manga.getName());
                rs = pr.executeQuery();
                if (rs.next())
                    throw new Exception("esiste già un manga con lo stesso nome nel db");
            }catch(SQLException e){
                throw e;
            }


        if(manga.getPublisher()==null || manga.getPublisher().equals("") || manga.getPublisher().length()>50)
            throw new Exception("l'editore del manga risulta nullo");

        if(manga.getPrice()<=0)
            throw new Exception("il prezzo inserito deve essere maggiore di 0");

        if(manga.getWeight()<1)
            throw new Exception("il peso inserito deve essere maggiore o uguale a 1");

        if(manga.getLength()<1)
            throw new Exception("la lunghezza inserita deve essere maggiore o uguale a 1");

        if(manga.getHeight()<1)
            throw new Exception("l'altezza inserita deve essere maggiore o uguale a 1");

        if(manga.getState()==null || (!manga.getState().equals(ProductState.USED) && !manga.getState().equals(ProductState.NEW)))
            throw new Exception("lo stato inserito non risulta corretto");

        if(manga.getDescription()==null || manga.getDescription().length()>255)
            throw new Exception("La descrizione inserita non risulta valida");

        if(manga.getIsbn() == null || manga.getIsbn().length()!=13)
            throw new Exception("L'isbn passato come parametro non risulta valido");

        if(manga.getBinding() == null || manga.getBinding().equals("") || manga.getBinding().length()>30)
            throw new Exception("la rilegatura passata risulta nulla");

        if(manga.getVolume() == null || manga.getVolume().equals("") || manga.getVolume().length()>20)
            throw new Exception("il volume passato risulta nullo");

        if(manga.getExitDate() == null || manga.getExitDate().after(new java.util.Date()))
            throw new Exception("la data passata non risulta valida "+ manga.getExitDate());

        if(manga.getPages()<1)
            throw new Exception("numero di pagine non corretto");

        if(manga.getQuantity()<1)
            throw new Exception("numero di unità richieste non corretto");

        if(manga.getInterior()== null || manga.getInterior().equals("") || manga.getInterior().length()>20)
            throw new Exception("il colore degli interni non risulta correttamente inserito");

        if(manga.getLanguage()==null || manga.getLanguage().equals("") || manga.getLanguage().length()>20)
            throw new Exception("la lingua non risulta correttamente inserita");

        if(manga.getStoryMaker()==null || manga.getStoryMaker().equals("") || manga.getStoryMaker().length()>25)
            throw new Exception("lo storymaker non risulta correttamente inserito");

        if(manga.getGenre()==null || manga.getGenre().getName()==null || manga.getGenre().getName().equals(""))
            throw new Exception("il genere inserito non risulta valido");

            try {
                pr = conn.prepareStatement("SELECT * FROM GENRE AS G WHERE g.nome=?");
                pr.setString(1, manga.getGenre().getName());

                rs = pr.executeQuery();

                if (rs.next()) {
                } else {
                    throw new Exception("il genere inserito non risulta valido");
                }
            }catch(SQLException e){
                throw e;
            }

        if(manga.getCollection()==null || manga.getCollection().getName()==null || manga.getCollection().getName().equals(""))
            throw new Exception("la collezione inserita non risulta valida");

            try{
                pr = conn.prepareStatement("SELECT * FROM COLLECTION AS c WHERE c.nome=?");
                pr.setString(1,manga.getCollection().getName());

                rs=pr.executeQuery();

                if(rs.next()){
                }else {
                    throw new Exception("la collezione inserita non risulta valida");
                }
            }catch(SQLException e){
                    throw e;
            }

           try {
               pr = conn.prepareStatement("INSERT INTO Manga (name,editore,price,weight,height,lenght,state,description,ISBN,book_binding,volume,release_date,page_number,quantity,interior,lang,image,collection_id,genre_id,storyMaker) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
               pr.setString(1, manga.getName());
               pr.setString(2, manga.getPublisher());
               pr.setDouble(3, manga.getPrice());
               pr.setDouble(4, manga.getWeight());
               pr.setDouble(5, manga.getHeight());
               pr.setDouble(6, manga.getLength());

               if (manga.getState().equals(ProductState.USED))
                   pr.setString(7, "USED");
               else
                   pr.setString(7, "NEW");

               pr.setString(8, manga.getDescription());
               pr.setString(9, manga.getIsbn());
               pr.setString(10, manga.getBinding());
               pr.setString(11, manga.getVolume());
               pr.setDate(12, manga.getExitDate());
               pr.setInt(13, manga.getPages());
               pr.setInt(14, manga.getQuantity());
               pr.setString(15, manga.getInterior());
               pr.setString(16, manga.getLanguage());
               pr.setString(17, manga.getImagePath());
               pr.setString(18, manga.getCollection().getName());
               pr.setString(19, manga.getGenre().getName());
               pr.setString(20, manga.getStoryMaker());

               pr.executeUpdate();
           }catch (SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                pr.close();
                rs.close();
                conn.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void delete(int id) throws Exception {
        Connection conn = ds.getConnection();
        PreparedStatement pr = null;
        ResultSet rs = null;
        pr = conn.prepareStatement("SELECT * from Manga as m WHERE m.id=?");
        pr.setInt(1,id);
        rs = pr.executeQuery();
        if(rs.next()){
            ;
        }else{
            throw new Exception("Il prodotto selezionato per la rimozione non è presente nel db");
        }

        try{
            pr = conn.prepareStatement("DELETE FROM Manga as p WHERE p.id=?");
            pr.setInt(1,id);
            pr.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try{
                pr.close();
                rs.close();
                conn.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void updateQuantity(int quantity,int id) throws Exception {

        Connection conn = ds.getConnection();
        PreparedStatement pr = null;
        ResultSet rs = null;
        pr = conn.prepareStatement("SELECT * from Manga as m WHERE m.id=?");
        pr.setInt(1,id);
        rs = pr.executeQuery();
        if(rs.next()){
            ;
        }else{
            throw new Exception("Il prodotto selezionato per l'aggiornamento non è presente nel db'");
        }


        if(quantity<=0)
            throw new Exception("quantità inserita non valida");

        try{
            pr = conn.prepareStatement("UPDATE Manga AS m SET m.quantity = m.quantity + ? WHERE m.id=?");
            pr.setInt(1,quantity);
            pr.setInt(2,id);
            pr.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally{
            try{
                pr.close();
                rs.close();
                conn.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

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
                String publisher = rs.getString(3);
                double price = rs.getDouble(4);
                double weight = rs.getDouble(5);
                double height = rs.getDouble(6);
                double lenght = rs.getDouble(7);
                String state = rs.getString(8);

                ProductState pS;

                if (state.equals("NEW"))
                    pS = ProductState.NEW;
                else
                    pS = ProductState.USED;

                String description = rs.getString(9);
                String ISBN = rs.getString(10);
                String binding = rs.getString(11);
                String volume = rs.getString(12);
                Date exit_date = rs.getDate(13);
                int p_number = rs.getInt(14);
                int quantity = rs.getInt(15);
                String interior = rs.getString(16);
                String language = rs.getString(17);
                String imagePath = rs.getString(18);
                Collection coll = new Collection(rs.getString(19));
                Genre genre = new Genre(rs.getString(20));
                String storyMaker = rs.getString(21);

                Manga m = new Manga(ISBN,publisher,binding,language,volume,p_number,exit_date,iD,name,description,price,height,lenght,weight,quantity,interior,imagePath,coll,pS,storyMaker,genre);
                return m;
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


    public Manga retrieveByName(String name) throws Exception {
        if(name==null)
            throw new Exception("il nome non può essere nullo");

        PreparedStatement pr = null;
        ResultSet rs = null;
        try(Connection conn = ds.getConnection()){
            pr = conn.prepareStatement("SELECT * from Manga as m WHERE m.name=?");
            pr.setString(1,name);
            rs = pr.executeQuery();
            if(rs.next()){
                int iD = rs.getInt(1);
                String n = rs.getString(2);
                String publisher = rs.getString(3);
                double price = rs.getDouble(4);
                double weight = rs.getDouble(5);
                double height = rs.getDouble(6);
                double lenght = rs.getDouble(7);
                String state = rs.getString(8);

                ProductState pS;

                if (state.equals("NEW"))
                    pS = ProductState.NEW;
                else
                    pS = ProductState.USED;

                String description = rs.getString(9);
                String ISBN = rs.getString(10);
                String binding = rs.getString(11);
                String volume = rs.getString(12);
                Date exit_date = rs.getDate(13);
                int p_number = rs.getInt(14);
                int quantity = rs.getInt(15);
                String interior = rs.getString(16);
                String language = rs.getString(17);
                String imagePath = rs.getString(18);
                Collection coll = new Collection(rs.getString(19));
                Genre genre = new Genre(rs.getString(20));
                String storyMaker = rs.getString(21);

                Manga m = new Manga(ISBN,publisher,binding,language,volume,p_number,exit_date,iD,n,description,price,height,lenght,weight,quantity,interior,imagePath,coll,pS,storyMaker,genre);
                return m;
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

    public ArrayList<Manga> filterForUsers(String name, float min_price, float max_price,String collection,boolean orderSubject, boolean order_criteria) throws Exception {

        if((name==null || name.equals("")) && (collection==null || collection.equals("")) && min_price==0 && max_price==0)
            return retrieveAll();

        if(max_price>=0 && max_price<min_price)
            throw new Exception("range di valori inserito non corretto");

        if(name==null)
            name="";


        Connection conn = ds.getConnection();

        PreparedStatement pr = null;
        ResultSet rs = null;

        pr = conn.prepareStatement("SELECT * FROM COLLECTION AS c WHERE c.nome=?");
        pr.setString(1,collection);

        rs=pr.executeQuery();

        if(rs.next()) {
        }else{
            throw new Exception("Collezione inserita non esistente nel db");
        }



        String ricerca = null;

        try{
            ricerca = "SELECT * FROM Manga AS m WHERE m.name LIKE '%" + name + "%' AND m.collection_id LIKE '%" + collection + "%' AND m.price BETWEEN ? AND ";
            if (max_price <= 0) {
                ricerca = ricerca + "99999999999 ";

                if(orderSubject){
                    ricerca = ricerca+"ORDER BY m.name";
                }else{
                    ricerca = ricerca+"ORDER BY m.price";
                }


                if(order_criteria){
                    ricerca = ricerca + " ASC";
                }else {
                    ricerca = ricerca + " DESC";
                }

                pr = conn.prepareStatement(ricerca);
                if (min_price <= 0)
                    pr.setFloat(1, 0);
                else {
                    pr.setFloat(1, min_price);
                }
            } else {
                ricerca = ricerca + "? ";

                if(orderSubject){
                    ricerca = ricerca+"ORDER BY m.name";
                }else{
                    ricerca = ricerca+"ORDER BY m.price";
                }


                if(order_criteria){
                    ricerca = ricerca + " ASC";
                }else {
                    ricerca = ricerca + " DESC";
                }

                pr = conn.prepareStatement(ricerca);
                if (min_price <= 0)
                    pr.setFloat(1, 0);
                else {
                    pr.setFloat(1, min_price);
                }
                pr.setFloat(2, max_price);
            }

            ArrayList<Manga> lista = new ArrayList<Manga>();
            rs = pr.executeQuery();
            while (rs.next()) {
                int iD = rs.getInt(1);
                String n = rs.getString(2);
                String publisher = rs.getString(3);
                double price = rs.getDouble(4);
                double weight = rs.getDouble(5);
                double height = rs.getDouble(6);
                double lenght = rs.getDouble(7);
                String state = rs.getString(8);

                ProductState pS;

                if (state.equals("NEW"))
                    pS = ProductState.NEW;
                else
                    pS = ProductState.USED;

                String description = rs.getString(9);
                String ISBN = rs.getString(10);
                String binding = rs.getString(11);
                String volume = rs.getString(12);
                Date exit_date = rs.getDate(13);
                int p_number = rs.getInt(14);
                int quantity = rs.getInt(15);
                String interior = rs.getString(16);
                String language = rs.getString(17);
                String imagePath = rs.getString(18);
                Collection coll = new Collection(rs.getString(19));
                Genre genre = new Genre(rs.getString(20));
                String storyMaker = rs.getString(21);

                Manga m = new Manga(ISBN,publisher,binding,language,volume,p_number,exit_date,iD,n,description,price,height,lenght,weight,quantity,interior,imagePath,coll,pS,storyMaker,genre);
                lista.add(m);
            }

            System.out.println(lista.size()+" Sono il filtro per gestori");

            if (lista.size() == 0) {
                throw new Exception("non sono stati trovati prodotti affini ai filtri di ricerca specificati");
            }
            else return lista;

        }catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println(ricerca);
            return null;
        }finally {
            try{
                rs.close();
                pr.close();
                conn.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }


    public ArrayList<Manga> retrieveAll() throws Exception{
        PreparedStatement pr = null;
        ResultSet rs = null;
        try(Connection conn = ds.getConnection()){
            pr = conn.prepareStatement("SELECT * from Manga as m");
            rs = pr.executeQuery();
            ArrayList<Manga> lista = new ArrayList<Manga>();
            while(rs.next()) {
                int iD = rs.getInt(1);
                String n = rs.getString(2);
                String publisher = rs.getString(3);
                double price = rs.getDouble(4);
                double weight = rs.getDouble(5);
                double height = rs.getDouble(6);
                double lenght = rs.getDouble(7);
                String state = rs.getString(8);

                ProductState pS;

                if (state.equals("NEW"))
                    pS = ProductState.NEW;
                else
                    pS = ProductState.USED;

                String description = rs.getString(9);
                String ISBN = rs.getString(10);
                String binding = rs.getString(11);
                String volume = rs.getString(12);
                Date exit_date = rs.getDate(13);
                int p_number = rs.getInt(14);
                int quantity = rs.getInt(15);
                String interior = rs.getString(16);
                String language = rs.getString(17);
                String imagePath = rs.getString(18);
                Collection coll = new Collection(rs.getString(19));
                Genre genre = new Genre(rs.getString(20));
                String storyMaker = rs.getString(21);

                Manga m = new Manga(ISBN,publisher,binding,language,volume,p_number,exit_date,iD,n,description,price,height,lenght,weight,quantity,interior,imagePath,coll,pS,storyMaker,genre);
                lista.add(m);
            }
            if(lista.size()==0)
                throw new Exception("non sono stati trovati prodotti nel db");

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

    public ArrayList<Manga> filterForEndUsers(String name,String collection) throws Exception {

        if((name==null || name.equals("")) && (collection==null || collection.equals("")))
            return retrieveAll();

        if(name==null)
            name="";


        Connection conn = ds.getConnection();

        PreparedStatement pr = null;
        ResultSet rs = null;

        pr = conn.prepareStatement("SELECT * FROM COLLECTION AS c WHERE c.nome=?");
        pr.setString(1,collection);

        rs=pr.executeQuery();

        if(rs.next()) {
        }else{
            throw new Exception("Collezione inserita non esistente nel db");
        }


        String ricerca = null;

        try{

            ricerca = "SELECT * FROM Manga AS m WHERE m.name LIKE '%" + name + "%' AND m.collection_id ='"+collection+"'";

            pr = conn.prepareStatement(ricerca);

            ArrayList<Manga> lista = new ArrayList<Manga>();
            rs = pr.executeQuery();

            while (rs.next()) {
                int iD = rs.getInt(1);
                String n = rs.getString(2);
                String publisher = rs.getString(3);
                double price = rs.getDouble(4);
                double weight = rs.getDouble(5);
                double height = rs.getDouble(6);
                double lenght = rs.getDouble(7);
                String state = rs.getString(8);

                ProductState pS;

                if (state.equals("NEW"))
                    pS = ProductState.NEW;
                else
                    pS = ProductState.USED;

                String description = rs.getString(9);
                String ISBN = rs.getString(10);
                String binding = rs.getString(11);
                String volume = rs.getString(12);
                Date exit_date = rs.getDate(13);
                int p_number = rs.getInt(14);
                int quantity = rs.getInt(15);
                String interior = rs.getString(16);
                String language = rs.getString(17);
                String imagePath = rs.getString(18);
                Collection coll = new Collection(rs.getString(19));
                Genre genre = new Genre(rs.getString(20));
                String storyMaker = rs.getString(21);

                Manga m = new Manga(ISBN,publisher,binding,language,volume,p_number,exit_date,iD,n,description,price,height,lenght,weight,quantity,interior,imagePath,coll,pS,storyMaker,genre);
                lista.add(m);
            }
            System.out.println(lista.size()+" Sono il filtro per utente finale");

            if (lista.size() == 0) {
                throw new Exception("non sono stati trovati prodotti affini ai filtri di ricerca specificati");
            }
            else return lista;

        }catch (SQLException e){
            System.out.println(ricerca);
            e.printStackTrace();
            return null;
        }finally {
            try{
                rs.close();
                pr.close();
                conn.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    /*@Override
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
    */


    public boolean checkQuantity(Manga manga){
        PreparedStatement pr = null;
        ResultSet rs = null;
        boolean result = true;
        try(Connection conn = ds.getConnection()){
            pr = conn.prepareStatement("SELECT quantity FROM Manga WHERE id=?");
            pr.setInt(1,manga.getId());
            rs = pr.executeQuery();
            if(rs.next()){
                int quantity = rs.getInt(1);
                if(manga.getQuantity() > quantity){
                    result = false;
                }
            }
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
        return result;
    }

    public void updateQuantity(Manga manga){
        PreparedStatement pr = null;
        Manga m = retrieveById(manga.getId());
        try(Connection conn = ds.getConnection()){
            pr = conn.prepareStatement("UPDATE Manga SET quantity=? WHERE id=?");
            pr.setInt(1,manga.getQuantity());
            pr.setInt(2,manga.getId());
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
}
