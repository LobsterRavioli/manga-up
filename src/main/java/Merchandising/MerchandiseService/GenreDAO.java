package Merchandising.MerchandiseService;

import Merchandising.MerchandiseService.Genre;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GenreDAO {

    private DataSource ds;
    public GenreDAO(DataSource ds){
        this.ds = ds;
    }
/*
    public Genre retrieveByManga(int manga_id){
        PreparedStatement pr = null;
        ResultSet rs = null;
        try(Connection conn = ds.getConnection()){
            pr = conn.prepareStatement("SELECT HG.genre_id FROM HASGENRE AS HG WHERE HG.manga_id=?");
            pr.setInt(1,manga_id);
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
*/


    public Genre retrieve (String name) throws Exception{
        if(name == null)
            throw new Exception("il nome non può essere nullo");

        PreparedStatement pr = null;
        ResultSet rs= null;
        try(Connection conn = ds.getConnection()){
            pr = conn.prepareStatement("SELECT * FROM GENRE AS G WHERE g.nome=?");
            pr.setString(1,name);

            rs=pr.executeQuery();

            if(rs.next()) {
                Genre g = new Genre(rs.getString(1));
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


    public ArrayList<Genre> retrieveAlL() throws Exception{


        PreparedStatement pr = null;
        ResultSet rs= null;
        try(Connection conn = ds.getConnection()){
            pr = conn.prepareStatement("SELECT * FROM Genre");

            ArrayList<Genre> lista = new ArrayList<Genre>();

            rs=pr.executeQuery();

            while(rs.next()) {
                Genre g = new Genre(rs.getString(1));
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
