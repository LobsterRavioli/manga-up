package Merchandising.MerchandiseService.dao_layer.implementations;

import Merchandising.MerchandiseService.beans.Autore;
import Merchandising.MerchandiseService.beans.RuoloAutore;
import Merchandising.MerchandiseService.dao_layer.interfaces.AutoreDAO;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AutoreDAOImpl implements AutoreDAO {

    private DataSource ds;
    public AutoreDAOImpl(DataSource ds){
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

    public void delete(int  id){
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

    public Autore retrieve(int id){
        PreparedStatement pr = null;
        try(Connection conn = ds.getConnection()){
            pr = conn.prepareStatement("SELECT * FROM Author AS a WHERE a.id=?");
            pr.setInt(1,id);
            ResultSet rs = pr.executeQuery();
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

}
