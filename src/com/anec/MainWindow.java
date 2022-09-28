package com.anec;

import javax.swing.*;
import java.awt.*;
import java.time.DateTimeException;

public class MainWindow extends JFrame {

    private final JLabel labelFirstTime = new JLabel("От");
    private final JTextField textFieldFirstTime = new JTextField();

    private final JLabel labelSecondTime = new JLabel("До");
    private final JTextField textFieldSecondTime = new JTextField();

    private final JButton buttonCalculate = new JButton("Рассчитать");
    private final JButton buttonAbout = new JButton("О программе");

    public MainWindow() {
        initialize();
    }

    private void initialize() {
        setTitle("Time Calculator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(386, 138);
        setMinimumSize(new Dimension(200, 100));
        setLocationRelativeTo(null);
        setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getClassLoader()
                .getResource("com/anec/icon/icon.png")));

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Container container = this.getContentPane();
        container.setLayout(new GridLayout(3, 2, 20, 5));

        labelFirstTime.setHorizontalAlignment(SwingConstants.CENTER);
        container.add(labelFirstTime);
        container.add(textFieldFirstTime);

        labelSecondTime.setHorizontalAlignment(SwingConstants.CENTER);
        container.add(labelSecondTime);
        container.add(textFieldSecondTime);

        buttonCalculate.addActionListener(e -> {
            if (textFieldFirstTime.getText().trim().equals("") || textFieldSecondTime.getText().trim().equals("")) {
                JOptionPane.showMessageDialog(null, "Время не введено", this.getTitle(),
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            int[] result;

            try {
                result = TimeCalculating.getTimeInterval(textFieldFirstTime.getText(), textFieldSecondTime.getText());
            } catch (DateTimeException ex) {
                JOptionPane.showMessageDialog(null, "Время введено неправильно", this.getTitle(),
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            String hourString;
            String minuteString;

            if (String.valueOf(result[0]).endsWith("1") && result[0] != 11) {
                hourString = "час";
            } else {
                char[] hoursArray = String.valueOf(result[0]).toCharArray();

                int value = Integer.parseInt(String.valueOf(hoursArray[hoursArray.length - 1]));
                if (value <= 4 && value >= 2 && !(result[0] > 11 && result[0] < 15)) {
                    hourString = "часа";
                } else {
                    hourString = "часов";
                }
            }

            if (String.valueOf(result[1]).endsWith("1") && result[1] != 11) {
                minuteString = "минута";
            } else {
                char[] minutesArray = String.valueOf(result[1]).toCharArray();

                int value = Integer.parseInt(String.valueOf(minutesArray[minutesArray.length - 1]));
                if (value <= 4 && value >= 2 && !(result[1] > 11 && result[1] < 15)) {
                    minuteString = "минуты";
                } else {
                    minuteString = "минут";
                }
            }

            JOptionPane.showMessageDialog(null, "Результат: " +
                            (result[0] == 0 ? "" : result[0] + " " + hourString + " ") +
                            (result[1] == 0 && result[0] != 0 ? "" : result[1] + " " + minuteString),
                    this.getTitle(), JOptionPane.INFORMATION_MESSAGE);
        });
        container.add(buttonCalculate);
        buttonAbout.addActionListener(e -> JOptionPane.showMessageDialog(null,
                """
                        Эта программа предназначена для рассчёта времени

                                                         Версия: 0.1.3

                                                        Created by Anec
                        """, "О программе", JOptionPane.PLAIN_MESSAGE));
        container.add(buttonAbout);

        for (Component c : this.getComponents()) {
            SwingUtilities.updateComponentTreeUI(c);
        }
    }
}
