package Merchandising.MerchandiseService;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CollectionDAO {

    private DataSource ds;
    public CollectionDAO(DataSource ds){
        this.ds = ds;
    }



    public Collection retrieve (String name) throws Exception{

        if(name == null)
            throw new Exception("il nome non può essere nullo");

        PreparedStatement pr = null;
        ResultSet rs= null;
        try(Connection conn = ds.getConnection()){
            pr = conn.prepareStatement("SELECT * FROM COLLECTION AS c WHERE c.nome=?");
            pr.setString(1,name);

            rs=pr.executeQuery();

            if(rs.next()) {
                Collection g = new Collection(rs.getString(1));
                return g;
            }
            return null;

        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }finally {
            try{
                pr.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }


    public ArrayList<Collection> retrieveAlL() throws Exception{


        PreparedStatement pr = null;
        ResultSet rs= null;
        try(Connection conn = ds.getConnection()){
            pr = conn.prepareStatement("SELECT * FROM COLLECTION");

            ArrayList<Collection> lista = new ArrayList<Collection>();

            rs=pr.executeQuery();

            while(rs.next()) {
                Collection g = new Collection(rs.getString(1));
                lista.add(g);
            }
            if(lista.size()==0){
                throw new Exception("non ci sono collezioni inserite nel db");
            }
            return lista;

        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }finally {
            try{
                pr.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
