package Merchandising.MerchandiseService.dao_layer.implementations;

import Merchandising.MerchandiseService.beans.Genre;
import Merchandising.MerchandiseService.dao_layer.interfaces.GenreDAO;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreDAOImpl implements GenreDAO {

    private DataSource ds;
    public GenreDAOImpl(DataSource ds){
        this.ds = ds;
    }

    public Genre retrieve(String name){
        PreparedStatement pr = null;
        ResultSet rs = null;
        try(Connection conn = ds.getConnection()){
            pr = conn.prepareStatement("SELECT * FROM GENRE as G WHERE G.name=?");
            pr.setString(1,name);
            rs=pr.executeQuery();
            if(rs.next()){
                String n = rs.getString(1);
                Genre g = new Genre(n);
                return g;
            }
            return null;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }finally {
            try{
                pr.close();
                rs.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

}
