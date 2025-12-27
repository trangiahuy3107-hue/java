package dao;

import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BorrowDAO {

    public boolean borrowBook(String studentId, String bookId, int quantity) {
        String checkBookSQL = "SELECT quantity FROM Books WHERE id=?";
        String insertBorrowSQL = "INSERT INTO Borrow(studentId, bookId, borrowDate, quantity) VALUES (?, ?, GETDATE(), ?)";
        String updateBookSQL = "UPDATE Books SET quantity = quantity - ? WHERE id=?";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            int currentQty;
            try (PreparedStatement ps = conn.prepareStatement(checkBookSQL)) {
                ps.setString(1, bookId);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) { conn.rollback(); return false; }
                currentQty = rs.getInt("quantity");
                if (currentQty < quantity) { conn.rollback(); return false; }
            }

            try (PreparedStatement ps = conn.prepareStatement(insertBorrowSQL)) {
                ps.setString(1, studentId);
                ps.setString(2, bookId);
                ps.setInt(3, quantity);
                ps.executeUpdate();
            }

            try (PreparedStatement ps = conn.prepareStatement(updateBookSQL)) {
                ps.setInt(1, quantity);
                ps.setString(2, bookId);
                ps.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public boolean returnBook(String studentId, String bookId) {
        String selectQtySQL = "SELECT quantity FROM Borrow WHERE studentId=? AND bookId=? AND returnDate IS NULL";
        String updateBorrowSQL = "UPDATE Borrow SET returnDate=GETDATE() WHERE studentId=? AND bookId=? AND returnDate IS NULL";
        String updateBookSQL = "UPDATE Books SET quantity = quantity + ? WHERE id=?";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            int totalQty = 0;
            try (PreparedStatement ps = conn.prepareStatement(selectQtySQL)) {
                ps.setString(1, studentId);
                ps.setString(2, bookId);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) totalQty += rs.getInt("quantity");
            }

            if (totalQty == 0) { conn.rollback(); return false; }

            try (PreparedStatement ps = conn.prepareStatement(updateBorrowSQL)) {
                ps.setString(1, studentId);
                ps.setString(2, bookId);
                ps.executeUpdate();
            }

            try (PreparedStatement ps = conn.prepareStatement(updateBookSQL)) {
                ps.setInt(1, totalQty);
                ps.setString(2, bookId);
                ps.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (Exception e) { e.printStackTrace(); return false; }
    }
}
