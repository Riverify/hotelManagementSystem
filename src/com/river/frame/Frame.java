package com.river.frame;

import com.river.dao.CustomerDao;
import com.river.dao.EmployeeDao;
import com.river.dao.impl.CustomerDaoImpl;
import com.river.dao.impl.EmployeeDaoImpl;
import com.river.entity.Customer;
import com.river.util.SwingUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


public class Frame {
    /**
     * 登陆界面
     */
    public static void loginFrame() {
        JFrame jFrame = SwingUtil.createNormalFrame("酒店客房管理系统 - 登陆界面", 2);
        jFrame.setLayout(new GridLayout(3, 3));

        JPanel jPanel1 = new JPanel();
        jPanel1.setBorder(new EmptyBorder(20, 80, 10, 20));

        JPanel jPanel2 = new JPanel(new FlowLayout());
        jPanel2.setBorder(new EmptyBorder(10, 100, 10, 100));

        JPanel jPanel3 = new JPanel(new GridLayout(2, 1, 10, 10));
        jPanel3.setBorder(new EmptyBorder(1, 250, 20, 250));

        JLabel jLabel_title = SwingUtil.createNormalLabel("酒店客房管理登陆界面", 50);
        JLabel jLabel_id = SwingUtil.createNormalLabel("手机号：", 45);
        JLabel jLabel_psw = SwingUtil.createNormalLabel("密码   ：", 45);


        JTextField jTextField = SwingUtil.createNormalTextField("", 45, 10);
        JPasswordField jPasswordField = SwingUtil.createNormalPasswordField("", 45, 10);

        JButton button_login = SwingUtil.createNormalButton("登陆", 35, 30);
        JButton button_exit = SwingUtil.createNormalButton("退出", 35, 30);
        JButton button_register = SwingUtil.createNormalButton("注册", 30, 15);


        button_login.addActionListener(e -> {
            EmployeeDao employeeDao = new EmployeeDaoImpl();
            int text = Integer.parseInt(jTextField.getText());

            String ename = employeeDao.findById(text).getEname();
            if (ename.equals("")) {
                System.out.println("no man");
            }
            if (ename.equals("SMITH")) {
                System.out.println("good");
            } else {
                System.out.println("fk");
            }
        });

        jLabel_title.setHorizontalAlignment(SwingConstants.CENTER);
//        jLabel_id.setHorizontalAlignment(SwingConstants.RIGHT);
//        jLabel_psw.setHorizontalAlignment(SwingConstants.RIGHT);
//        jTextField.setHorizontalAlignment(SwingConstants.LEFT);
//        jTextField.setHorizontalAlignment(SwingConstants.LEFT);

        jPanel1.add(jLabel_title);
        jPanel1.add(button_register);
        jPanel2.add(jLabel_id);
        jPanel2.add(jTextField);
        jPanel2.add(jLabel_psw);
        jPanel2.add(jPasswordField);
        jPanel3.add(button_login);
        jPanel3.add(button_exit);

        jFrame.add(jPanel1);
        jFrame.add(jPanel2);
        jFrame.add(jPanel3);

        button_exit.addActionListener(e -> {
            JOptionPane.showMessageDialog(jFrame, "感谢使用本系统!");
            System.exit(0);
        });

        button_register.addActionListener(e -> {
            registerFrame();
        });

        button_login.addActionListener(e -> {
            String phone = jTextField.getText();
            String psw = String.valueOf(jPasswordField.getPassword());

            if (phone.isEmpty() || psw.isEmpty()) {
                JOptionPane.showMessageDialog(jFrame, "请输入手机号或密码");
            } else if (phone.length() != 11) {
                JOptionPane.showMessageDialog(jFrame, "请输入正确的号码");
            }

            Customer customer = new Customer();
            customer.setPhone(phone);
            customer.setPassword(psw);

            CustomerDao customerDao = new CustomerDaoImpl();
            int n = 0;
            try {
                n = customerDao.checkLogin(customer);
            } catch (Exception exception) {
                exception.printStackTrace();
            }


            if (n == 1) {
            } else {
                JOptionPane.showMessageDialog(jFrame, "手机号或密码错误!");
            }

        });

        jFrame.setVisible(true);
    }

    // ================================================================================================================
    // ================================================================================================================
    // ================================================================================================================

    /**
     * 注册界面
     */
    private static void registerFrame() {
        JFrame jFrame = SwingUtil.createNormalFrame("注册", 1.5);
        jFrame.setLayout(new GridLayout(2, 1));

        JPanel jPanel1 = new JPanel(new FlowLayout());
        jPanel1.setBorder(new EmptyBorder(20, 300, 10, 300));

        JPanel jPanel2 = new JPanel(new GridLayout(2, 1, 10, 10));
        jPanel2.setBorder(new EmptyBorder(10, 100, 10, 100));

        JLabel jLabel_name = SwingUtil.createNormalLabel("姓名     ：", 45);
        JLabel jLabel_phone = SwingUtil.createNormalLabel("手机号  ：", 45);
        JLabel jLabel_idnum = SwingUtil.createNormalLabel("身份证  ：", 45);
        JLabel jLabel_psw = SwingUtil.createNormalLabel("密码     ：", 45);


        JTextField jTextField_name = SwingUtil.createNormalTextField("", 45, 10);
        JTextField jTextField_phone = SwingUtil.createNormalTextField("", 45, 10);
        JTextField jTextField_idnum = SwingUtil.createNormalTextField("", 45, 10);
        JPasswordField jPasswordField = SwingUtil.createNormalPasswordField("", 45, 10);

        JButton button_register = SwingUtil.createNormalButton("注册", 55, 30);
        JButton button_exit = SwingUtil.createNormalButton("返回", 55, 30);


        jPanel1.add(jLabel_name);
        jPanel1.add(jTextField_name);

        jPanel1.add(jLabel_phone);
        jPanel1.add(jTextField_phone);

        jPanel1.add(jLabel_idnum);
        jPanel1.add(jTextField_idnum);

        jPanel1.add(jLabel_psw);
        jPanel1.add(jPasswordField);


        jPanel2.add(button_register);
        jPanel2.add(button_exit);


        jFrame.add(jPanel1);
        jFrame.add(jPanel2);


        jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//        jFrame.setResizable(true);
        jFrame.setVisible(true);

        button_exit.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(null, "注册未完成，确认返回?", "确认", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                jFrame.dispose();
            }
        });

        button_register.addActionListener(e -> {
            String name = "";
            String phone = "";
            String idnum = "";
            String psw = "";
            try {
                name = jTextField_name.getText();
                phone = jTextField_phone.getText();
                idnum = jTextField_idnum.getText();
                psw = String.valueOf(jPasswordField.getPassword());

            } catch (Exception exception) {
                JOptionPane.showMessageDialog(jFrame, "错误!");
            }

            if (name.isEmpty() || phone.isEmpty() || idnum.isEmpty() || psw.isEmpty()) {
                JOptionPane.showMessageDialog(jFrame, "信息不能为空!");
            } else {
                Customer customer = new Customer();
                customer.setName(name);
                customer.setPhone(phone);
                customer.setIdnum(idnum);
                customer.setPassword(psw);

                CustomerDao customerDao = new CustomerDaoImpl();
                int n = customerDao.register(customer);
                if (n == 1) {
                    JOptionPane.showMessageDialog(jFrame, "注册成功!");
                    jFrame.dispose();
                }
            }
        });

    }
}
