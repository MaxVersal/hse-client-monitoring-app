package hse.ru.activetracker;

import hse.ru.activetracker.controller.ControllerEjb;
import hse.ru.activetracker.ui.MainFrame;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ActivityTrackerApp extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel timerLabel;
    private ControllerEjb controllerEjb;
    private Timer timer;
    private int secondsPassed;

    private JLabel statusLabel;

    public ActivityTrackerApp() {
        JLabel usernameLabel = new JLabel("Username");
        JLabel passwordLabel = new JLabel("Password");

        usernameField = new JTextField(15);
        passwordField = new JPasswordField();

        JButton okButton = new JButton("Login");
        JButton cancelButton = new JButton("Cancel");
        statusLabel = new JLabel(" ");

        GridLayout gridLayOut = new GridLayout(2, 1);
        gridLayOut.setHgap(25);
        gridLayOut.setVgap(10);
        JPanel p3 = new JPanel(gridLayOut);
        p3.add(usernameLabel);
        p3.add(passwordLabel);

        JPanel p4 = new JPanel(gridLayOut);
        p4.add(usernameField);
        p4.add(passwordField);

        JPanel p1 = new JPanel();
        p1.add(p3);
        p1.add(p4);

        JPanel p2 = new JPanel();
        p2.add(okButton);
        p2.add(cancelButton);

        JPanel p5 = new JPanel(new BorderLayout());
        p5.add(p2, BorderLayout.CENTER);
        p5.add(statusLabel, BorderLayout.NORTH);
        statusLabel.setForeground(Color.RED);
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);

        setLayout(new BorderLayout());
        add(p1, BorderLayout.CENTER);
        add(p5, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        cancelButton.addActionListener(actionEvent -> System.exit(0));

        okButton.addActionListener(actionEvent -> {
            if (usernameField.getText().length() < 5) {
                statusLabel.setText("Login length less then 5!");
                return;
            }
            if (usernameField.getText().length() > 20) {
                statusLabel.setText("Login length more then 20!");
                return;
            }
            if (passwordField.getPassword().length < 5) {
                statusLabel.setText("Password length less then 5!");
                return;
            }
            if (passwordField.getPassword().length > 20) {
                statusLabel.setText("Password length more then 20!");
                return;
            }
            String username = usernameField.getText();
            char[] password = passwordField.getPassword();

            // Проверка на правильность логина и пароля
            boolean isAuthenticated = authenticate(username, password);

            if (isAuthenticated) {
//                okButton.setEnabled(true);
//                timerLabel.setVisible(true);
//                add(timerLabel, BorderLayout.CENTER);
//                trackingButton.requestFocus();
                JOptionPane.showMessageDialog(ActivityTrackerApp.this, "Authentication successful!");
                setVisible(false);
                SwingUtilities.invokeLater(() -> new MainFrame(controllerEjb));
            } else {
                JOptionPane.showMessageDialog(ActivityTrackerApp.this, "Authentication failed!");
            }
        });
        setVisible(true);
    }

    private boolean authenticate(String username, char[] password) {
        // Здесь должна быть реализация вашей функции проверки авторизации
        // Например, проверка в базе данных или внешней службе
        // Пока здесь просто возвращается true для демонстрационных целей
        controllerEjb = new ControllerEjb(username);
        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ActivityTrackerApp::new);
    }
}
