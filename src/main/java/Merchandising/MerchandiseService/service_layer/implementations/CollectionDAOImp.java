package Merchandising.MerchandiseService.service_layer.implementations;

import Merchandising.MerchandiseService.beans.Collection;
import Merchandising.MerchandiseService.service_layer.interfaces.CollectionDAO;
import Utilities.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CollectionDAOImp implements CollectionDAO {

    @Override
    public void create(Collection collection) {
        PreparedStatement p = null;
        try (Connection conn = ConnectionPool.getConnection()) {
            p = conn.prepareStatement("INSERT INTO Collection VALUES (?,?)");
            p.setString(1, collection.getTitolo());
            p.setString(2, collection.getDescrizione());
            p.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                p.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(String titolo) {
        PreparedStatement p = null;
        try (Connection conn = ConnectionPool.getConnection()) {
            p = conn.prepareStatement("DELETE FROM Collection as c WHERE c.titolo=?");
            p.setString(1, titolo);
            p.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                p.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(Collection collection, String OldTitle) {
        PreparedStatement p = null;
        try (Connection conn = ConnectionPool.getConnection()) {
            p = conn.prepareStatement("UPDATE Collection SET titolo=?,descrizione=? WHERE titolo=?");
            p.setString(1, collection.getTitolo());
            p.setString(2, collection.getDescrizione());
            p.setString(3, OldTitle);
            p.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                p.close();
            } catch (SQLException e) {

            }
        }
    }


    @Override
    public Collection retrieve(String titolo) {
        PreparedStatement p = null;
        ResultSet rs = null;
        try (Connection conn = ConnectionPool.getConnection()) {
            p = conn.prepareStatement("SELECT * FROM Connection as c WHERE c.titolo=?");
            p.setString(1, titolo);
            rs = p.executeQuery();
            if (rs.next()) {
                String t = rs.getString(1);
                String d = rs.getString(2);
                Collection c = new Collection(t, d);
                return c;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                p.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
