package view;

import dao.StudentDAO;
import model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StudentPanel extends JPanel {

    JTextField txtId = new JTextField();
    JTextField txtName = new JTextField();
    JTextField txtClass = new JTextField();

    JButton btnAdd = new JButton("Thêm sinh viên");

    JTable table;
    DefaultTableModel model;

    public StudentPanel() {

        setLayout(new BorderLayout());

        // ===== FORM =====
        JPanel form = new JPanel(new GridLayout(3, 2, 10, 10));
        form.setBorder(BorderFactory.createTitledBorder("Thông tin sinh viên"));

        form.add(new JLabel("Mã sinh viên:"));
        form.add(txtId);
        form.add(new JLabel("Tên sinh viên:"));
        form.add(txtName);
        form.add(new JLabel("Lớp:"));
        form.add(txtClass);

        JPanel top = new JPanel(new BorderLayout());
        top.add(form, BorderLayout.CENTER);
        top.add(btnAdd, BorderLayout.SOUTH);

        // ===== TABLE =====
        model = new DefaultTableModel(
                new String[]{"Mã SV", "Tên SV", "Lớp"}, 0
        );
        table = new JTable(model);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // ===== ACTION =====
        btnAdd.addActionListener(e -> addStudent());

        loadData(); // 🔥 QUAN TRỌNG
    }

    private void loadData() {
        model.setRowCount(0); // clear table
        List<Student> list = StudentDAO.getAll();

        for (Student s : list) {
            model.addRow(new Object[]{
                    s.getId(), s.getName(), s.getClazz()
            });
        }
    }

    private void addStudent() {
        if (StudentDAO.insert(
                txtId.getText(),
                txtName.getText(),
                txtClass.getText())) {

            JOptionPane.showMessageDialog(this, "Thêm thành công");
            loadData(); // 🔥 reload bảng
        }
    }
}
