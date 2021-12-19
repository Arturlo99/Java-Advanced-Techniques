package com.company.dao;


import com.company.model.PriceList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PriceListDao implements Dao<PriceList> {
    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {

            System.out.println(e.getMessage());

        }
    }

    private Connection conn = null;

    public PriceListDao() {
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
    public PriceList get(long id) {
        String sql = "SELECT id, service_type, price FROM price_list WHERE id=?";
        PriceList priceList = null;
        try {
            connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                priceList = new PriceList(rs.getLong("id"), rs.getString("service_type"),
                        rs.getFloat("price"));
            }
            pstmt.close();
            disconnect();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return priceList;
    }

    @Override
    public List<PriceList> getAll() {
        String sql = "SELECT id, service_type, price FROM price_list";
        List<PriceList> list = new ArrayList<>();
        try {
            connect();
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next())
                list.add(new PriceList(resultSet.getLong("id"), resultSet.getString("service_type"),
                        resultSet.getFloat("price")));
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void save(PriceList priceList) {
        String sql = "INSERT INTO price_list( service_type, price) VALUES (?,?)";
        try {
            connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, priceList.getServiceType());
            pstmt.setFloat(2, priceList.getPrice());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(PriceList priceList) {
        String sql = "UPDATE price_list SET  service_type = ?, price = ? " + "WHERE id = ?";

        try {
            connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, priceList.getServiceType());
            pstmt.setFloat(2, priceList.getPrice());
            pstmt.setLong(3, priceList.getId());
            pstmt.executeUpdate();
            pstmt.close();
            disconnect();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(PriceList priceList) {
        String sql = "DELETE FROM price_list WHERE id=?";
        try {
            connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, priceList.getId());
            pstmt.executeUpdate();
            disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
