package dao;

import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ReportDAO {

    public List<BorrowReport> getBorrowReport() {
        List<BorrowReport> list = new ArrayList<>();
        String sql = "SELECT studentId, bookId, borrowDate, returnDate, quantity FROM Borrow ORDER BY borrowDate DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new BorrowReport(
                        rs.getString("studentId"),
                        rs.getString("bookId"),
                        rs.getDate("borrowDate"),
                        rs.getDate("returnDate"),
                        rs.getInt("quantity")
                ));
            }

        } catch (Exception e) { e.printStackTrace(); }

        return list;
    }
}
