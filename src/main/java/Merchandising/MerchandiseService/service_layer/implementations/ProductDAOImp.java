package Merchandising.MerchandiseService.service_layer.implementations;

import Merchandising.MerchandiseService.beans.Product;
import Merchandising.MerchandiseService.service_layer.interfaces.ProductDAO;
import Utilities.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Merchandising.MerchandiseService.beans.Manga;

public class ProductDAOImp implements ProductDAO {


    @Override
    public void create(Product p) {
        PreparedStatement pr = null;
        try(Connection conn = ConnectionPool.getConnection()){
            pr = conn.prepareStatement("INSERT INTO Product VALUES (?,?,?,?,?,?,?,?)");
            pr.setString(1,p.getName());
            pr.setString(2,p.getProducer());
            pr.setDouble(3,p.getPrice());
            pr.setDouble(4,p.getWeight());
            pr.setDouble(5,p.getHeight());
            pr.setDouble(6,p.getLength());

            if(p.getState()== Product.ProductState.USED)
                pr.setString(7,"USED");
            else
                pr.setString(7,"NEW");

            pr.setString(8,p.getDescription());
            pr.executeUpdate();
            if(p instanceof Manga){
                pr = conn.prepareStatement("INSERT INTO Manga VALUES ()");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally{
            try{
                pr.close();
            }catch(SQLException e){

            }
        }
    }

    @Override
    public void delete(Product p) {

    }

    @Override
    public void update(Product p) {

    }

    @Override
    public Product retrieve(int id) {
        return null;
    }
}
