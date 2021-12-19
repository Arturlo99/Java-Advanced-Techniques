package com.company.dao;

import com.company.model.Payment;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PaymentDao implements Dao<Payment> {
    private Connection conn = null;

    public InstallationDao getInstallationDao() {
        return installationDao;
    }

    private InstallationDao installationDao;
    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {

            System.out.println(e.getMessage());

        }
    }
    public PaymentDao() {
        this.installationDao = new InstallationDao();
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
    public Payment get(long id) {
        String sql = "SELECT payment_date, payment_amount, installation_id FROM payment WHERE id=?";
        Payment payment = null;
        try {
            connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                payment = new Payment(id, LocalDate.parse(rs.getString("payment_date")),
                        rs.getFloat("payment_amount"), installationDao.get(rs.getLong("installation_id")));
            }
            pstmt.close();
            disconnect();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return payment;
    }

    @Override
    public List<Payment> getAll() {
        String sql = "SELECT id, payment_date, payment_amount, installation_id FROM payment";
        List<Payment> list = new ArrayList<>();
        try {
            connect();
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next())
                list.add(new Payment(resultSet.getLong("id"),
                        LocalDate.parse(resultSet.getString("payment_date")),
                        resultSet.getFloat("payment_amount"),
                        installationDao.get(resultSet.getLong("installation_id"))));
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void save(Payment payment) {
        String sql = "INSERT INTO payment( payment_date, payment_amount, installation_id) VALUES (?,?,?)";
        try {
            connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, payment.getPaymentDate().toString());
            pstmt.setFloat(2,payment.getPaymentAmount());
            pstmt.setLong(3, payment.getInstallation().getId());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Payment payment) {
        String sql = "UPDATE payment SET payment_date = ?, payment_amount = ?," +
                " installation_id = ? " + "WHERE id = ?";

        try {
            connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, payment.getPaymentDate().toString());
            pstmt.setFloat(2, payment.getPaymentAmount());
            pstmt.setLong(3, payment.getInstallation().getId());
            pstmt.setLong(4, payment.getId());
            pstmt.executeUpdate();
            pstmt.close();
            disconnect();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Payment payment) {
        String sql = "DELETE FROM payment WHERE id=?";
        try {
            connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, payment.getId());
            pstmt.executeUpdate();
            disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
