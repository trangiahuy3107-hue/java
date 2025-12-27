package view;

import dao.BorrowDAO;
import dao.BookDAO;

import javax.swing.*;
import java.awt.*;

public class BorrowPanel extends JPanel {

    private JTextField txtStudentId, txtBookId, txtQuantity;
    private JButton btnBorrow, btnReturn;
    private BorrowDAO borrowDAO;
    private ReportPanel reportPanel;
    private BookPanel bookPanel;

    public BorrowPanel(ReportPanel reportPanel, BookPanel bookPanel) {
        this.reportPanel = reportPanel;
        this.bookPanel = bookPanel;
        borrowDAO = new BorrowDAO();

        setLayout(new GridLayout(5,2,5,5));

        txtStudentId = new JTextField(); txtBookId = new JTextField(); txtQuantity = new JTextField();
        btnBorrow = new JButton("Mượn sách"); btnReturn = new JButton("Trả sách");

        add(new JLabel("Mã SV:")); add(txtStudentId);
        add(new JLabel("Mã sách:")); add(txtBookId);
        add(new JLabel("Số lượng:")); add(txtQuantity);
        add(btnBorrow); add(btnReturn);

        btnBorrow.addActionListener(e -> {
            String studentId = txtStudentId.getText().trim();
            String bookId = txtBookId.getText().trim();
            int qty;
            try { qty = Integer.parseInt(txtQuantity.getText().trim()); }
            catch(Exception ex){ JOptionPane.showMessageDialog(this,"Số lượng không hợp lệ!"); return; }

            if(borrowDAO.borrowBook(studentId,bookId,qty)){
                JOptionPane.showMessageDialog(this,"✅ Mượn thành công!");
                reportPanel.refreshReport();
                bookPanel.getModel().setRowCount(0);
                BookDAO.load(bookPanel.getModel());
            } else {
                JOptionPane.showMessageDialog(this,"❌ Mượn thất bại!");
            }
        });

        btnReturn.addActionListener(e -> {
            String studentId = txtStudentId.getText().trim();
            String bookId = txtBookId.getText().trim();

            if(borrowDAO.returnBook(studentId,bookId)){
                JOptionPane.showMessageDialog(this,"✅ Trả thành công!");
                reportPanel.refreshReport();
                bookPanel.getModel().setRowCount(0);
                BookDAO.load(bookPanel.getModel());
            } else {
                JOptionPane.showMessageDialog(this,"❌ Trả thất bại!");
            }
        });
    }
}
