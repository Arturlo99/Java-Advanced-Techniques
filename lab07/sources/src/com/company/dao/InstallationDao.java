package com.company.dao;

import com.company.model.Installation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InstallationDao implements Dao<Installation> {
    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private Connection conn = null;
    private ClientDao clientDao;
    private PriceListDao priceListDao;

    public InstallationDao() {
        this.clientDao = new ClientDao();
        this.priceListDao = new PriceListDao();
    }

    public ClientDao getClientDao() {
        return clientDao;
    }

    public PriceListDao getPriceListDao() {
        return priceListDao;
    }

    private void connect() throws SQLException {
        if (conn != null)
            return;
        String url = "jdbc:sqlite:internet_provider.db";
        conn = DriverManager.getConnection(url);
    }

    private void disconnect() throws SQLException {
        if (conn == null)
            return;
        conn.close();
        conn = null;
    }

    @Override
    public Installation get(long id) {
        String sql = "SELECT id, router_number, service_type, city, address, postcode, client_number, price_list_id" +
                " FROM installation WHERE id=?";
        Installation installation = null;
        try {
            connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                installation = new Installation(rs.getLong("id"),
                        rs.getString("router_number"),
                        rs.getString("service_type"),
                        rs.getString("city"),
                        rs.getString("address"),
                        rs.getString("postcode"),
                        clientDao.get(rs.getLong("client_number")),
                        priceListDao.get(rs.getLong("price_list_id"))
                );
            }
            pstmt.close();
            disconnect();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return installation;
    }

    @Override
    public List<Installation> getAll() {
        String sql = "SELECT id, router_number, service_type, city, address, postcode, client_number, price_list_id FROM installation";
        List<Installation> list = new ArrayList<>();
        try {
            connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next())
                list.add(new Installation(rs.getLong("id"),
                        rs.getString("router_number"),
                        rs.getString("service_type"),
                        rs.getString("city"),
                        rs.getString("address"),
                        rs.getString("postcode"),
                        clientDao.get(rs.getLong("client_number")),
                        priceListDao.get(rs.getLong("price_list_id"))));
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void save(Installation installation) {
        String sql = "INSERT INTO installation(router_number, service_type, city, address, postcode," +
                " client_number, price_list_id) VALUES (?,?,?,?,?,?,?)";
        try {
            connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, installation.getRouterNumber());
            pstmt.setString(2, installation.getServiceType());
            pstmt.setString(3, installation.getCity());
            pstmt.setString(4, installation.getAddress());
            pstmt.setString(5, installation.getPostcode());
            pstmt.setLong(6, installation.getClient().getClientNumber());
            pstmt.setLong(7, installation.getPriceList().getId());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Installation installation) {
        String sql = "UPDATE installation SET router_number = ?, service_type = ?, city = ?, address = ?, postcode = ?," +
                "client_number = ?, price_list_id = ? " + "WHERE id = ?";

        try {
            connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, installation.getRouterNumber());
            pstmt.setString(2, installation.getServiceType());
            pstmt.setString(3, installation.getCity());
            pstmt.setString(4, installation.getAddress());
            pstmt.setString(5, installation.getPostcode());
            pstmt.setLong(6, installation.getClient().getClientNumber());
            pstmt.setLong(7, installation.getPriceList().getId());
            pstmt.setLong(8, installation.getId());
            pstmt.executeUpdate();
            pstmt.close();
            disconnect();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Installation installation) {
        String sql = "DELETE FROM installation WHERE id=?";
        try {
            connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, installation.getId());
            pstmt.executeUpdate();
            disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
