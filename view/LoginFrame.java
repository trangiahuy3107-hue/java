package view;

import javax.swing.*;
import dao.UserDAO;

public class LoginFrame extends JFrame {
    JTextField txtUser;
    JPasswordField txtPass;

    public LoginFrame() {
        setTitle("Đăng nhập");
        setSize(300,200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        txtUser = new JTextField();
        txtPass = new JPasswordField();
        JButton btnLogin = new JButton("Đăng nhập");

        btnLogin.addActionListener(e -> {
            if (UserDAO.login(txtUser.getText(), txtPass.getText())) {
                new MainFrame().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Sai tài khoản!");
            }
        });

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(new JLabel("Username"));
        add(txtUser);
        add(new JLabel("Password"));
        add(txtPass);
        add(btnLogin);
    }
}
