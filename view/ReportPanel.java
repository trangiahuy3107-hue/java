package view;

import dao.BorrowReport;
import dao.ReportDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ReportPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private ReportDAO dao;

    public ReportPanel() {
        dao = new ReportDAO();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        model = new DefaultTableModel(new String[]{"Mã SV","Mã sách","Ngày mượn","Ngày trả","Số lượng"},0);
        table = new JTable(model);
        add(new JScrollPane(table));

        refreshReport();
    }

    public void refreshReport() {
        model.setRowCount(0);
        List<BorrowReport> reports = dao.getBorrowReport();
        for(BorrowReport br : reports){
            model.addRow(new Object[]{
                    br.getStudentId(),
                    br.getBookId(),
                    br.getBorrowDate(),
                    br.getReturnDate(),
                    br.getQuantity()
            });
        }
    }
}
