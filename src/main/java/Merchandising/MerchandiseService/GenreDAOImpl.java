package Merchandising.MerchandiseService;

import Merchandising.MerchandiseService.beans.Genre;
import Merchandising.MerchandiseService.dao_layer.interfaces.GenreDAO;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GenreDAOImpl implements GenreDAO {

    private DataSource ds;
    public GenreDAOImpl(DataSource ds){
        this.ds = ds;
    }

    public ArrayList<Genre> retrieveByManga(int manga_id){
        PreparedStatement pr = null;
        ResultSet rs = null;
        try(Connection conn = ds.getConnection()){
            pr = conn.prepareStatement("SELECT HG.genre_id FROM HASGENRE AS HG WHERE HG.manga_id=?");
            pr.setInt(1,manga_id);
            rs=pr.executeQuery();

            ArrayList<Genre> generi = new ArrayList<Genre>();

            while(rs.next()){
                String n = rs.getString(1);
                Genre g = new Genre(n);
                generi.add(g);
            }

            if(generi.size()==0)
                return null;
            else
                return generi;

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

    /*public boolean orphanControl(int id){
        PreparedStatement pr = null;
        ResultSet rs = null;
        boolean flag=false;
        try(Connection conn = ds.getConnection()){
            pr = conn.prepareStatement("SELECT * FROM HASGENRE AS HG WHERE HG.genre_id=?");
            pr.setInt(1,id);

            rs=pr.executeQuery();

            if(rs.next()){
                flag=true;
            }

            return flag;

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }finally {
            try{
                pr.close();
                rs.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }*/

    public void create(Genre g){
        PreparedStatement pr = null;
        try(Connection conn = ds.getConnection()){
            pr = conn.prepareStatement("INSERT INTO Genre VALUES (?)");
            pr.setString(1,g.getName());
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

    public void delete(String nome){
        PreparedStatement pr = null;
        try(Connection conn = ds.getConnection()){
            pr = conn.prepareStatement("DELETE FROM Genre AS g WHERE g.nome=?");
            pr.setString(1,nome);
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
