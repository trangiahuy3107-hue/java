package view;

import dao.BookDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class BookPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private JTextField txtId, txtTitle, txtAuthor, txtQty;
    private JButton btnAdd, btnUpdate;

    public BookPanel() {
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new String[]{"Mã sách","Tên sách","Tác giả","Số lượng"},0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridLayout(5,2,5,5));
        txtId = new JTextField(); txtTitle = new JTextField();
        txtAuthor = new JTextField(); txtQty = new JTextField();

        inputPanel.add(new JLabel("Mã sách:")); inputPanel.add(txtId);
        inputPanel.add(new JLabel("Tên sách:")); inputPanel.add(txtTitle);
        inputPanel.add(new JLabel("Tác giả:")); inputPanel.add(txtAuthor);
        inputPanel.add(new JLabel("Số lượng:")); inputPanel.add(txtQty);

        btnAdd = new JButton("Thêm"); btnUpdate = new JButton("Cập nhật số lượng");
        inputPanel.add(btnAdd); inputPanel.add(btnUpdate);

        add(inputPanel, BorderLayout.SOUTH);

        BookDAO.load(model);

        btnAdd.addActionListener(e -> {
            String id = txtId.getText().trim();
            String title = txtTitle.getText().trim();
            String author = txtAuthor.getText().trim();
            int qty;
            try { qty = Integer.parseInt(txtQty.getText().trim()); }
            catch(Exception ex){ JOptionPane.showMessageDialog(this,"Quantity không hợp lệ!"); return; }

            BookDAO.insert(id,title,author,qty);
            BookDAO.load(model);
        });

        btnUpdate.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if(selectedRow==-1){ JOptionPane.showMessageDialog(this,"Chọn sách để cập nhật!"); return; }

            String bookId = model.getValueAt(selectedRow,0).toString();
            int qty;
            try { qty = Integer.parseInt(txtQty.getText().trim()); }
            catch(Exception ex){ JOptionPane.showMessageDialog(this,"Quantity không hợp lệ!"); return; }

            BookDAO.updateQuantity(bookId,qty);
            BookDAO.load(model);
        });
    }

    public DefaultTableModel getModel() {
        return model;
    }
}
