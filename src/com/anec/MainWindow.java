package com.anec;

import javax.swing.*;
import java.awt.*;
import java.time.DateTimeException;

public class MainWindow extends JFrame {

    private final JLabel labelFirstTime = new JLabel("От");
    private final JTextField textFieldFirstTime = new JTextField();

    private final JLabel labelSecondTime = new JLabel("До");
    private final JTextField textFieldSecondTime = new JTextField();

    private final JButton buttonCalculateTimeInterval = new JButton("Рассчитать");
    private final JButton buttonAbout = new JButton("О программе");

    public MainWindow() {
        initialize();
    }

    private static void setSystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initialize() {
        setTitle("Time Calculator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(386, 138);
        setMinimumSize(new Dimension(200, 100));
        setLocationRelativeTo(null);
        setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getClassLoader()
                .getResource("com/anec/icon/icon.png")));
        setSystemLookAndFeel();

        Container container = this.getContentPane();
        container.setLayout(new GridLayout(3, 2, 20, 5));

        labelFirstTime.setHorizontalAlignment(SwingConstants.CENTER);
        container.add(labelFirstTime);
        container.add(textFieldFirstTime);

        labelSecondTime.setHorizontalAlignment(SwingConstants.CENTER);
        container.add(labelSecondTime);
        container.add(textFieldSecondTime);

        buttonCalculateTimeInterval.addActionListener(e -> {
            if (textFieldFirstTime.getText().trim().equals("") || textFieldSecondTime.getText().trim().equals("")) {
                JOptionPane.showMessageDialog(null, "Время не введено", this.getTitle(),
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            int[] result = getTimeInterval();
            if (result == null) return;

            int hours = result[0];
            int minutes = result[1];
            String hourWord = TimeCalculator.generateWord(hours, TimeCalculator.getHourWords());
            String minuteWord = TimeCalculator.generateWord(minutes, TimeCalculator.getMinuteWords());

            JOptionPane.showMessageDialog(null, "Результат: " +
                            (hours == 0 ? "" : hours + " " + hourWord + " ") +
                            (minutes == 0 && hours != 0 ? "" : minutes + " " + minuteWord),
                    this.getTitle(), JOptionPane.INFORMATION_MESSAGE);
        });
        container.add(buttonCalculateTimeInterval);
        buttonAbout.addActionListener(e -> JOptionPane.showMessageDialog(null,
                """
                        Эта программа предназначена для рассчёта времени

                                                         Версия: 0.1.5

                                                        Created by Anec
                        """, "О программе", JOptionPane.PLAIN_MESSAGE));
        container.add(buttonAbout);

        updateUiComponents();
    }

    private int[] getTimeInterval() {
        int[] result;
        try {
            result = TimeCalculator.getTimeInterval(textFieldFirstTime.getText(), textFieldSecondTime.getText());
        } catch (DateTimeException e) {
            JOptionPane.showMessageDialog(null, "Время введено неправильно",
                    this.getTitle(), JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return result;
    }

    private void updateUiComponents() {
        for (Component c : this.getComponents()) {
            SwingUtilities.updateComponentTreeUI(c);
        }
    }
}
