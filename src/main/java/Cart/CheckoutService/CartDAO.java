package Cart.CheckoutService;

import javax.sql.DataSource;

public class CartDAO {

    private DataSource ds;
    public CartDAO(DataSource ds){
        this.ds = ds;
    }
    /*
    public HashMap<Object,Integer> retrieveCart(int userID) throws UserNotAssociatedException{
        EndUserDAO ed = new EndUserDAO(ds);

        if(ed.findById(userID)==null)
            throw new UserNotAssociatedException();

        PreparedStatement pr = null;
        ResultSet rs = null;
        HashMap<Object,Integer> mappa = new HashMap<Object,Integer>();
        try(Connection conn = ds.getConnection()){
            pr = conn.prepareStatement("SELECT m.id,m.name,m.brand,m.price,m.isbn,hs.quantity FROM HASPRODUCTM AS hs,MANGA AS m where hs.user_id=?");
            rs = pr.executeQuery();
            while(rs.next()){
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String brand = rs.getString(3);
                double price = rs.getDouble(4);
                String isbn = rs.getString(5);
                int quantity = rs.getInt(6);
                Manga m = new Manga(isbn,brand,"","","",0,null,0,name,"description",price,0.0,0.0,0.0,"collections",-1, Product.ProductState.NEW,"interior","");
                mappa.put(m,quantity);
            }

            pr = conn.prepareStatement("SELECT p.id,p.name,p.brand,p.price,p.type_of_product,hs.quantity FROM HASPRODUCTP AS hs,PRODUCT AS p where hs.user_id=?");
            rs = pr.executeQuery();
            while(rs.next()){
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String brand = rs.getString(3);
                double price = rs.getDouble(4);
                String type_of_p = rs.getString(5);
                int quantity = rs.getInt(6);
                // Product p = new Product(id,name,brand,"",price,0.0,0.0,0.0, Product.ProductState.NEW,type_of_p,-1,"");
                // mappa.put(p,quantity);
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
    public boolean removeElement(int user_id,int prod_ID,Class type) throws NonExistentProductException,UserNotAssociatedException{
        EndUserDAO eD = new EndUserDAO(ds);
        ProductDAO p = new ProductDAO(ds);
        MangaDAO m = new MangaDAO(ds);

        if(type.equals(MangaDAO.class))
            if(m.retrieveById(prod_ID)==null)
                throw new NonExistentProductException();
            else{
                if(p.retrieveById(prod_ID)==null)
                    throw new NonExistentProductException();
            }

        if(eD.findById(user_id)==null){
            throw new UserNotAssociatedException();
        }

        PreparedStatement pr = null;
        ResultSet rs = null;
        try(Connection conn = ds.getConnection()){
            if(type.equals(Manga.class))
                pr = conn.prepareStatement("DELETE FROM HASPRODUCTM AS hs WHERE hs.user_id=? AND hs.manga_id=?");
            else{
                pr = conn.prepareStatement("DELETE FROM HASPRODUCTP AS hs WHERE hs.user_id=? AND hs.product_id=?");
            }

            pr.setInt(1,user_id);
            pr.setInt(2,prod_ID);
            pr.executeUpdate();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }finally {
            try{
                rs.close();
                pr.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public boolean addElement(int user_id,int prod_id,int quantity,Class c) throws NonExistentProductException,UserNotAssociatedException,InvalidQuantityException{
        EndUserDAO eD = new EndUserDAO(ds);
        ProductDAO p = new ProductDAO(ds);
        MangaDAO m = new MangaDAO(ds);

        if(c.equals(MangaDAO.class))
            if(m.retrieveById(prod_id)==null)
                throw new NonExistentProductException();
        else{
            if(p.retrieveById(prod_id)==null)
                throw new NonExistentProductException();
            }

        if(eD.findById(user_id)==null){
            throw new UserNotAssociatedException();
        }

        if(quantity>50 || quantity<0){
            throw new InvalidQuantityException();
        }


        PreparedStatement pr = null;
        ResultSet rs = null;
        try(Connection conn = ds.getConnection()){
            if(c.equals(Manga.class))
                pr = conn.prepareStatement("INSERT INTO HASPRODUCTM (user_id,manga_id,quantity) VALUES (?,?,?)");
            else{
                pr = conn.prepareStatement("INSERT INTO HASPRODUCTP (user_id,product_id,quantity) VALUES (?,?,?)");
            }

            pr.setInt(1,user_id);
            pr.setInt(2,prod_id);
            pr.setInt(3,quantity);
            pr.executeUpdate();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }finally {
            try{
                rs.close();
                pr.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public boolean updateElement(int user_id,int prod_id,int quantity, Class c) throws NonExistentProductException,UserNotAssociatedException,InvalidQuantityException{

        EndUserDAO eD = new EndUserDAO(ds);
        ProductDAO p = new ProductDAO(ds);
        MangaDAO m = new MangaDAO(ds);

        if(c.equals(MangaDAO.class))
            if(m.retrieveById(prod_id)==null)
                throw new NonExistentProductException();
            else{
                if(p.retrieveById(prod_id)==null)
                    throw new NonExistentProductException();
            }

        if(eD.findById(user_id)==null){
            throw new UserNotAssociatedException();
        }

        if(quantity>50 || quantity<0){
            throw new InvalidQuantityException();
        }

        PreparedStatement pr = null;
        ResultSet rs = null;
        try(Connection conn = ds.getConnection()){
            if(c.equals(Manga.class))
                pr = conn.prepareStatement("UPDATE HASPRODUCTM SET quantity=? WHERE user_id=? AND manga_id=?");
            else{
                pr = conn.prepareStatement("UPDATE HASPRODUCTP SET quantity=? WHERE user_id=? AND product_id=?");
            }

            pr.setInt(1,quantity);
            pr.setInt(2,user_id);
            pr.setInt(3,prod_id);
            pr.executeUpdate();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
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
}
