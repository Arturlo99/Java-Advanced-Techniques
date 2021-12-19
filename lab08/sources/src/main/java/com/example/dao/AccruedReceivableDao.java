package com.example.dao;

import com.example.model.AccruedReceivable;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AccruedReceivableDao implements Dao<AccruedReceivable> {
	static {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {

			System.out.println(e.getMessage());

		}
	}

	private Connection conn = null;
	private InstallationDao installationDao;

	public AccruedReceivableDao() {
		this.installationDao = new InstallationDao();
	}

	private void connect() throws SQLException {
		if (conn != null)
			return;
		String url = "jdbc:sqlite:E:\\Studia\\6 semestr\\JAVA_L-workspace\\InternetProviderService\\internet_provider.db";
		conn = DriverManager.getConnection(url);
	}

	private void disconnect() throws SQLException {
		if (conn == null)
			return;
		conn.close();
		conn = null;
	}

	public List<AccruedReceivable> getFromTo(long clientNumber, String fromDate, String toDate) {
		String sql = "SELECT * FROM accrued_receivable ar INNER JOIN installation i ON ar.installation_id = i.id "
				+ "WHERE payment_deadline >= ? AND payment_deadline <= ? AND i.client_number = ?";
		List<AccruedReceivable> list = new ArrayList<>();
		try {
			connect();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, fromDate);
			pstmt.setString(2, toDate);
			pstmt.setLong(3, clientNumber);
			ResultSet resultSet = pstmt.executeQuery();
			while (resultSet.next()) {
				list.add(new AccruedReceivable(resultSet.getLong("id"),
				LocalDate.parse(resultSet.getString("payment_deadline")), resultSet.getFloat("amount_to_pay"),
				installationDao.get(resultSet.getLong("installation_id"))));
			}
			pstmt.close();
			disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public AccruedReceivable get(long id) {
		String sql = "SELECT payment_deadline, amount_to_pay, installation_id FROM accrued_receivable WHERE id=?";
		AccruedReceivable ar = null;
		try {
			connect();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				ar = new AccruedReceivable(id, LocalDate.parse(rs.getString("payment_deadline")),
						rs.getFloat("amount_to_pay"), installationDao.get(id));
			}
			pstmt.close();
			disconnect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ar;
	}

	@Override
	public List<AccruedReceivable> getAll() {
		String sql = "SELECT id, payment_deadline, amount_to_pay, installation_id FROM accrued_receivable";
		List<AccruedReceivable> list = new ArrayList<>();
		try {
			connect();
			Statement stmt = conn.createStatement();
			ResultSet resultSet = stmt.executeQuery(sql);
			while (resultSet.next())
				list.add(new AccruedReceivable(resultSet.getLong("id"),
						LocalDate.parse(resultSet.getString("payment_deadline")), resultSet.getFloat("amount_to_pay"),
						installationDao.get(resultSet.getLong("installation_id"))));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void save(AccruedReceivable ar) {
		String sql = "INSERT INTO accrued_receivable( payment_deadline, amount_to_pay, installation_id) VALUES (?,?,?)";
		try {
			connect();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ar.getPaymentDeadline().toString());
			pstmt.setFloat(2, ar.getAmountToPay());
			pstmt.setLong(3, ar.getInstallation().getId());
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void update(AccruedReceivable ar) {
		String sql = "UPDATE accrued_receivable SET payment_deadline = ?, amount_to_pay = ?, installation_id = ? "
				+ "WHERE id = ?";

		try {
			connect();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ar.getPaymentDeadline().toString());
			pstmt.setFloat(2, ar.getAmountToPay());
			pstmt.setLong(3, ar.getInstallation().getId());
			pstmt.setLong(4, ar.getId());
			pstmt.executeUpdate();
			pstmt.close();
			disconnect();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void delete(AccruedReceivable ar) {
		String sql = "DELETE FROM accrued_receivable WHERE id=?";
		try {
			connect();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, ar.getId());
			pstmt.executeUpdate();
			disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
