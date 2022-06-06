package com.river.util;

import javax.swing.*;
import java.awt.*;

public abstract class SwingUtil {
    /*
    NATIVE_RESOLUTION   :   显示器分辨率
    WIDTH               :   窗口长度
    HEIGHT              :   窗口宽度
     */
    final static int[] NATIVE_RESOLUTION = new int[]{2880, 1800};
    final static int WIDTH = 1920;
    final static int HEIGHT = 1200;

    /**
     * 自动生成简单且符合屏幕的一般大小窗口
     *
     * @param title 窗口标题
     * @return JFrame窗口对象
     */
    public static JFrame createNormalFrame(String title) {

        JFrame jFrame = new JFrame(title);

        jFrame.setResizable(false);

//        jFrame.setSize(WIDTH, HEIGHT);
//        jFrame.setLocation(new Point((NATIVE_RESOLUTION[0] - WIDTH) / 2, (NATIVE_RESOLUTION[1] - HEIGHT) / 2));
        jFrame.setBounds(((NATIVE_RESOLUTION[0] - WIDTH) / 2), ((NATIVE_RESOLUTION[1] - HEIGHT) / 2), WIDTH, HEIGHT);

        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        return jFrame;
    }

    public static JFrame createNormalFrame(String title, int rate) {

        JFrame jFrame = new JFrame(title);

        jFrame.setResizable(false);

//        jFrame.setSize(WIDTH, HEIGHT);
//        jFrame.setLocation(new Point((NATIVE_RESOLUTION[0] - WIDTH) / 2, (NATIVE_RESOLUTION[1] - HEIGHT) / 2));
        jFrame.setBounds(((NATIVE_RESOLUTION[0] - WIDTH / rate) / 2), ((NATIVE_RESOLUTION[1] - HEIGHT / rate) / 2), WIDTH / rate, HEIGHT / rate);

        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        return jFrame;
    }

    public static JFrame createNormalFrame(String title, double rate) {

        JFrame jFrame = new JFrame(title);

        jFrame.setResizable(false);

//        jFrame.setSize(WIDTH, HEIGHT);
//        jFrame.setLocation(new Point((NATIVE_RESOLUTION[0] - WIDTH) / 2, (NATIVE_RESOLUTION[1] - HEIGHT) / 2));
        jFrame.setBounds(((NATIVE_RESOLUTION[0] - (int) (WIDTH / rate)) / 2), ((NATIVE_RESOLUTION[1] - (int) (HEIGHT / rate)) / 2), (int) (WIDTH / rate), (int) (HEIGHT / rate));

        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        return jFrame;
    }

    /**
     * 自动生成简单且符合屏幕的一般大小Dialog
     *
     * @param parentFrame 父窗口
     * @param title       标题
     * @return JDialog对象
     */
    public static JDialog createNormalDialog(JFrame parentFrame, String title) {

        int smallRate = 2; // smallRate >= 1

        JDialog jDialog = new JDialog(parentFrame, title);
//        jFrame.setSize(WIDTH, HEIGHT);
//        jFrame.setLocation(new Point((NATIVE_RESOLUTION[0] - WIDTH) / 2, (NATIVE_RESOLUTION[1] - HEIGHT) / 2));
        jDialog.setBounds(((NATIVE_RESOLUTION[0] - WIDTH / smallRate) / 2), ((NATIVE_RESOLUTION[1] - HEIGHT / smallRate) / 2), WIDTH / smallRate, HEIGHT / smallRate);

        jDialog.setVisible(true);
        jDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        return jDialog;
    }

    public static JButton createNormalButton(String text) {

        int rate = 30;

        JButton jButton = new JButton(text);

        jButton.setPreferredSize(new Dimension(WIDTH / rate, HEIGHT / rate));
        jButton.setFont(new Font("Arial", Font.PLAIN, 50));

        return jButton;
    }

    public static JButton createNormalButton(String text, int rate, int size) {

        JButton jButton = new JButton(text);

        jButton.setPreferredSize(new Dimension(WIDTH / rate, HEIGHT / rate));
        jButton.setFont(new Font("Arial", Font.PLAIN, size));

        return jButton;
    }

    public static JLabel createNormalLabel(String text, int size) {
        JLabel jLabel = new JLabel(text);
        jLabel.setFont(new Font("Arial", Font.PLAIN, size));
        return jLabel;
    }

    public static JPasswordField createNormalPasswordField(String text, int size, int length) {
        JPasswordField jPasswordField = new JPasswordField(text, length);
        jPasswordField.setFont(new Font("Arial", Font.PLAIN, size));
        return jPasswordField;
    }

    public static JTextField createNormalTextField(String text, int size, int length) {
        JTextField jTextField = new JTextField(text, length);
        jTextField.setFont(new Font("Arial", Font.PLAIN, size));
        return jTextField;
    }

    public static void showMessage(Component parentComponent, String message) {
        JOptionPane.showMessageDialog(parentComponent, message);
    }

}
