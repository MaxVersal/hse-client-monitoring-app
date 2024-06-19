package hse.ru.activetracker.ui;

import hse.ru.activetracker.controller.ControllerEjb;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

/**
 * todo Document type MainFrame
 */
public class MainFrame extends JFrame {

    private JLabel helloLabel;
    private JLabel timerLabel;
    private JLabel projectLabel;
    private JButton startButton;
    private JButton sendButton;
    private JComboBox<String> projectsComboBox;
    private Timer timer;
    private final Object lock = new Object();
    private int secondsPassed;
    private final ControllerEjb controllerEjb;

    public MainFrame(ControllerEjb controllerEjb) {
        this.controllerEjb = controllerEjb;
        setTitle("Activity Tracking");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(260, 260);
        setLocation(650, 390);
        setResizable(false);
        setVisible(true);

        GridLayout gridLayout = new GridLayout(6, 1);
        gridLayout.setHgap(10);
        gridLayout.setVgap(15);
        JPanel panel = new JPanel(gridLayout);
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));

        helloLabel = new JLabel(" ");
        helloLabel.setAlignmentY(0.5f);
        helloLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        helloLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(helloLabel);

        projectLabel = new JLabel("Choose your project:");
        projectLabel.setAlignmentX(0.5f);
        projectLabel.setHorizontalTextPosition(JLabel.CENTER);
        projectLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(projectLabel);

        projectsComboBox = new JComboBox();
        projectsComboBox.setAlignmentX(0.5f);
        projectsComboBox.setSize(250, 30);
        projectsComboBox.addItem("Project Alpha");
        projectsComboBox.addItem("Project Delta");
        projectsComboBox.addItem("Project Gamma");
        panel.add(projectsComboBox);

        timerLabel = new JLabel("00:00:00");
        timerLabel.setAlignmentX(0.5f);
        timerLabel.setHorizontalTextPosition(JLabel.CENTER);
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(timerLabel);

        startButton = new JButton("Start");
        panel.add(startButton);

        sendButton = new JButton("Send info to the server");
        panel.add(sendButton);
        sendButton.setVisible(false);

        panel.setAlignmentX(0.5f);
        panel.setAlignmentY(0.5f);
        getContentPane().add(panel);

        startButton.addActionListener(e -> {
            if (startButton.getText().equals("Start")) {
                try {
                    startTracking();
                    sendButton.setVisible(true);
                } catch (NativeHookException ex) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Native hook error " + ex.getMessage());
                    throw new RuntimeException(ex);
                }
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                synchronized (lock){
                    //test
                    if(startButton.getText().equals("Start Tracking")){
                        try {
                            stopTracking();
                        } catch (NativeHookException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    System.exit(0);
                }
            }
        });
        sendButton.addActionListener(action -> {
            // Создаем панель с меткой и текстовым полем
            JPanel commentaryPanel = new JPanel(new GridLayout(2, 1));
            JLabel label = new JLabel("Commentary for session");
            JTextField textField = new JTextField();

            commentaryPanel.add(label);
            commentaryPanel.add(textField);

            // Показываем диалоговое окно
            int result = JOptionPane.showConfirmDialog(null, commentaryPanel, "Input", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            // Проверяем результат и обрабатываем введенные данные
            if (result == JOptionPane.OK_OPTION) {
                String userInput = textField.getText();
                // Действия с введенными данными
                System.out.println("User input: " + userInput);
                try {
                    controllerEjb.send(Objects.requireNonNull(projectsComboBox.getSelectedItem()).toString());
                    sendButton.setVisible(false);
                    stopTracking();
                    timerInit();
                    timerLabel.setText("00:00:00");
                    startButton.setEnabled(true);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Error on sending: " + e.getMessage());
                }
            }
        });

    }

    private void startTracking() throws NativeHookException {
        //startButton.setText("Stop Tracking");
        startButton.setEnabled(false);
        projectsComboBox.setEditable(false);
        timerInit();
        try {
            controllerEjb.init();
            timer.start();
        } catch (Exception e) {
            GlobalScreen.unregisterNativeHook();
            timer.stop();
            JOptionPane.showMessageDialog(MainFrame.this, e.getMessage());
        }
    }

    private void stopTracking() throws NativeHookException {
        timer.stop();
        GlobalScreen.unregisterNativeHook();
    }

    private void timerInit() {
        secondsPassed = 0;
        timer = new Timer(1000, e -> {
            secondsPassed++;
            int hours = secondsPassed / 3600;
            int minutes = (secondsPassed % 3600) / 60;
            int seconds = secondsPassed % 60;
            String timeStr = String.format("%02d:%02d:%02d", hours, minutes, seconds);
            timerLabel.setText(timeStr);
        });
    }
}
