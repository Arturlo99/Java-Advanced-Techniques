package com.company.dao;

import com.company.model.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDao implements Dao<Client> {
    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {

            System.out.println(e.getMessage());

        }
    }

    private Connection conn = null;

    public ClientDao() {
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
    public Client get(long id) {
        String sql = "SELECT client_number, first_name, last_name FROM client WHERE client_number=?";
        Client client = null;
        try {
            connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                client = new Client(rs.getLong("client_number"), rs.getString("first_name"), rs.getString("last_name"));
            }
            pstmt.close();
            disconnect();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return client;
    }

    @Override
    public List<Client> getAll() {
        String sql = "SELECT client_number, first_name, last_name FROM client";
        List<Client> list = new ArrayList<>();
        try {
            connect();
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next())
                list.add(new Client(resultSet.getLong("client_number"),
                        resultSet.getString("first_name"), resultSet.getString("last_name")));
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void save(Client client) {
        String sql = "INSERT INTO client( first_name, last_name) VALUES (?,?)";
        try {
            connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, client.getFirstName());
            pstmt.setString(2, client.getLastName());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Client client) {
        String sql = "UPDATE client SET first_name = ?, last_name = ? " + "WHERE client_number = ?";

        try {
            connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, client.getFirstName());
            pstmt.setString(2, client.getLastName());
            pstmt.setLong(3, client.getClientNumber());
            pstmt.executeUpdate();
            pstmt.close();
            disconnect();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Client client) {
        String sql = "DELETE FROM client WHERE client_number=?";
        try {
            connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, client.getClientNumber());
            pstmt.executeUpdate();
            disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
