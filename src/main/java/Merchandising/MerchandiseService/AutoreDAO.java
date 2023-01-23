package Merchandising.MerchandiseService;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AutoreDAO {

    private DataSource ds;
    public AutoreDAO(DataSource ds){
        this.ds = ds;
    }

    public void create(Autore a){
        PreparedStatement pr = null;
        try(Connection conn = ds.getConnection()){
            pr = conn.prepareStatement("INSERT INTO Author VALUES (?,?)");
            pr.setString(1,a.getNome());
            RuoloAutore ruolo = a.getRuolo();
            if(ruolo == RuoloAutore.StoryBoarder)
                pr.setString(2,"StoryBoarder");
            else{
                pr.setString(2,"StoryMaker");
            }
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

    public void delete(int id){
        PreparedStatement pr = null;
        try(Connection conn = ds.getConnection()){
            pr = conn.prepareStatement("DELETE FROM Author AS a WHERE a.id=?");
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

    /*public boolean orphanControl(int id){
        PreparedStatement pr = null;
        ResultSet rs = null;
        boolean flag=false;
        try(Connection conn = ds.getConnection()){
            pr = conn.prepareStatement("SELECT * FROM HASAUTHOR AS HA WHERE HA.author_id=?");

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

    public ArrayList<Autore> retrieveByManga(int manga_id){
        PreparedStatement pr = null;
        ResultSet rs = null;
        try(Connection conn = ds.getConnection()){
            pr = conn.prepareStatement("SELECT HS.author_id FROM HASAUTHOR AS HS WHERE HS.manga_id=?");
            pr.setInt(1,manga_id);
            rs = pr.executeQuery();

            ArrayList<Autore> autori = new ArrayList<Autore>();

            while(rs.next()){
                int a_id = rs.getInt(1);
                Autore a = retrieve(a_id);
                autori.add(a);
            }
            if(autori.size()==0)
                return null;
            else
                return autori;
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

    public Autore retrieve(int id){
        PreparedStatement pr = null;
        ResultSet rs = null;
        try(Connection conn = ds.getConnection()){
            pr = conn.prepareStatement("SELECT * FROM AUTHOR AS a WHERE a.id=?");
            pr.setInt(1,id);

            rs = pr.executeQuery();

            if(rs.next()){
                String name = rs.getString("name");
                String ruolo = rs.getString("role");
                RuoloAutore rA;
                if(ruolo.equals("StoryBoarder"))
                    rA=RuoloAutore.StoryBoarder;
                else
                    rA=RuoloAutore.StoryMaker;

                Autore author = new Autore(id,name,rA);
                return author;
            }else {
                return null;
            }
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
