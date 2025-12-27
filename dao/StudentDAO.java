package dao;

import database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Student;

public class StudentDAO {

    public static boolean insert(String id, String name, String clazz) {

        String sql = "INSERT INTO Students(id, name, class) VALUES (?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, id);
            ps.setString(2, name);
            ps.setString(3, clazz);

            int row = ps.executeUpdate();
            System.out.println("Inserted students: " + row);

            return row > 0;

        } catch (Exception e) {
            System.out.println("❌ LỖI THÊM SINH VIÊN");
            e.printStackTrace(); // RẤT QUAN TRỌNG
        }
        return false;
    }
    public static List<Student> getAll() {

        List<Student> list = new ArrayList<>();

        String sql = "SELECT * FROM Students";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Student(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("class")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
