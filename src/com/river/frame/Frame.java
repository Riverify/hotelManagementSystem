package com.river.frame;

import com.river.dao.CustomerDao;
import com.river.dao.DetailAllDao;
import com.river.dao.RoomInfoDao;
import com.river.dao.RoomOperationDao;
import com.river.dao.impl.CustomerDaoImpl;
import com.river.dao.impl.DetailAllDaoImpl;
import com.river.dao.impl.RoomInfoDaoImpl;
import com.river.dao.impl.RoomOperationImpl;
import com.river.entity.Customer;
import com.river.entity.DetailAll;
import com.river.entity.RoomInfo;
import com.river.entity.RoomOperation;
import com.river.util.SwingUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;


public class Frame {
    /**
     * 登陆界面
     */
    public static void loginFrame() {
        /*
        窗口绘制
         */
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


        JTextField jTextField = SwingUtil.createNormalTextField("", 40, 10);
        JPasswordField jPasswordField = SwingUtil.createNormalPasswordField("", 40, 10);

        JButton button_login = SwingUtil.createNormalButton("登陆", 35, 30);
        JButton button_exit = SwingUtil.createNormalButton("退出", 35, 30);
        JButton button_register = SwingUtil.createNormalButton("注册", 30, 15);

        jLabel_title.setHorizontalAlignment(SwingConstants.CENTER);

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

        jFrame.setVisible(true);


        /*
        监听事件
         */
        // 退出按钮
        button_exit.addActionListener(e -> {
            JOptionPane.showMessageDialog(jFrame, "感谢使用本系统!");
            System.exit(0);
        });

        // 进入注册页面
        button_register.addActionListener(e -> registerFrame());

        // 登陆按钮监听
        button_login.addActionListener(e -> {

            // 获取文本框和密码框的文本信息
            String phone = jTextField.getText();
            String psw = String.valueOf(jPasswordField.getPassword());



            // 封装信息
            Customer customer = new Customer();
            customer.setPhone(phone);
            customer.setPassword(psw);

            // 创建dao对象并创建接收数组
            CustomerDao customerDao = new CustomerDaoImpl();
            List<Customer> list = null;

            try {
                // 获取登陆查询信息
                list = customerDao.checkLogin(customer);
            } catch (Exception exception) {
                exception.printStackTrace();
            }

            // 若返回一个查询信息，则登陆成功,否则登陆失败
            assert list != null;
            if (list.size() == 1) {
                // TODO: 6/6/22 主界面
                if (list.get(0).getId() == 1) {
                    // TODO: 6/7/22 管理界面
                    adminFrame();
                }
                mainFrame();
            } else {
                // 纠正输入信息
                if (phone.isEmpty() || psw.isEmpty()) {
                    SwingUtil.showMessage(jFrame, "请输入手机号或密码");
                } else if (phone.length() > 11) {
                    SwingUtil.showMessage(jFrame, "请输入正确的号码");
                } else {
                    // 错误时清除密码框
                    jPasswordField.setText("");
                    SwingUtil.showMessage(jFrame, "手机号或密码错误!");
                }
            }
        });
    }

    // ================================================================================================================
    // ================================================================================================================
    // ================================================================================================================
    // ================================================================================================================
    // ================================================================================================================
    // ================================================================================================================


    /**
     * 管理房间界面
     */
    private static void adminFrame() {
        /*
        窗口绘制
         */
        JFrame jFrame = SwingUtil.createNormalFrame("房间管理界面");
        jFrame.setLayout(new GridLayout(2, 1, 10, 10));

        JPanel jPanel_topScreen = new JPanel();
        JPanel jPanel_menu = new JPanel();
        JPanel jPanel_bottomScreen = new JPanel();


        // 显示顶部文本域
        JTextArea textArea_Info = SwingUtil.createNormalTextArea(15, 65, 30);
        textArea_Info.setEditable(false);   // 禁止用户编辑
        JScrollPane scrollPane = new JScrollPane(textArea_Info);

        // temp text
        DetailAllDao dao = new DetailAllDaoImpl();
        List<DetailAll> list = dao.findAll();

        for (DetailAll detailAll : list) {
            textArea_Info.append(detailAll.toString());
            textArea_Info.append("——————————————————————————————————————————————————————————————");
        }

        jPanel_topScreen.add(scrollPane);


        // 显示菜单栏按钮

        jPanel_menu.setBorder(new EmptyBorder(10, 10, 10, 400));

        JButton button_refresh = SwingUtil.createNormalButton("刷新全部", 10, 30);
        JButton button_current = SwingUtil.createNormalButton("进行中信息", 10, 30);
        JButton button_10rows = SwingUtil.createNormalButton("最近10条", 10, 30);

        jPanel_menu.add(button_refresh);
        jPanel_menu.add(button_current);
        jPanel_menu.add(button_10rows);


        // jPanel_Bottom
        // label
        JLabel label_business = SwingUtil.createNormalLabel("正在进行中的订单流水号", 30);
        JLabel label_change = SwingUtil.createNormalLabel("更换为", 30);


        // 订单房间管理panel
        JPanel jPanel_roomChange = new JPanel(new FlowLayout());


        // 显示订单下拉框
        // 创建订单流水对象，专门储存订单号
        RoomOperation roomBusiness = new RoomOperation();

        RoomOperationDao roomOperationDao = new RoomOperationImpl();
        List<RoomOperation> list_business = roomOperationDao.selcetBusiness(roomBusiness);

        JComboBox<Integer> box_business = new JComboBox<>();
        box_business.setFont(new Font("Arial", Font.PLAIN, 30));
        box_business.setBorder(new EmptyBorder(4, 10, 4, 10)); // 设置边距

        // 将查询得到的list_business中的每一个对象的business获取并给予下拉框
        for (RoomOperation listBusiness : list_business) {
            box_business.addItem(listBusiness.getBusiness());
        }

        // 展示当前订单的房间号
        JTextField textField_nowRoom = SwingUtil.createNormalTextField("", 30, 3);
        textField_nowRoom.addFocusListener(new JTextFieldHintListener(textField_nowRoom, "未选择")); // 提示字符
        textField_nowRoom.setEditable(false);   // 禁止用户编辑


        // 显示可用房间下拉框
        // 创建房间信息对象，专门可用房间号
        RoomInfo roomInfo = new RoomInfo();

        RoomInfoDao roomInfoDao = new RoomInfoDaoImpl();
        List<RoomInfo> empytRoomList = roomInfoDao.selectEmptyRoom(roomInfo);


        // 设置box的基本信息
        JComboBox<Integer> box_room = new JComboBox<>();
        box_room.setFont(new Font("Arial", Font.PLAIN, 30));
        box_room.setBorder(new EmptyBorder(4, 10, 4, 10)); // 设置边距

        // 将查询得到的list_business中的每一个对象的business获取并给予下拉框
        for (RoomInfo empytRoom : empytRoomList) {
            box_room.addItem(empytRoom.getRoomno());
        }


        // 更换按钮
        JButton button_changeRoom = SwingUtil.createNormalButton("更换", 30, 15);

        // 删除按钮
        JButton button_delete = SwingUtil.createNormalButton("退房", 30, 15);
        // TODO: 6/7/22 退房需要同时改变订单流水中的outdate


        jPanel_roomChange.add(label_business);
        jPanel_roomChange.add(box_business);
        jPanel_roomChange.add(textField_nowRoom);
        jPanel_roomChange.add(label_change);
        jPanel_roomChange.add(box_room);
        jPanel_roomChange.add(button_changeRoom);
        jPanel_roomChange.add(button_delete);

        // 将menu和更换房间放入bottomScreen
        jPanel_bottomScreen.add(jPanel_menu);
        jPanel_bottomScreen.add(jPanel_roomChange);


        jFrame.add(jPanel_topScreen);
        jFrame.add(jPanel_bottomScreen);


        jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        jFrame.setVisible(true);

        /*
        监听事件
         */
        // 刷新文本域
        button_refresh.addActionListener(e -> {
            DetailAllDao d = new DetailAllDaoImpl();
            List<DetailAll> l = d.findAll();

            textArea_Info.setText("");  // 清空文本域

            int count = 0; // 计数器
            for (DetailAll detailAll : l) {
                count++;
                textArea_Info.append(detailAll.toString());
                textArea_Info.append("——————————————————————————————————————————————————————————————");
            }

            textArea_Info.append("\n刷新完毕:共" + count + "条记录！\n");
        });

        // 刷新部分　——　最近10行
        button_10rows.addActionListener(e -> {
            DetailAllDao d = new DetailAllDaoImpl();
            List<DetailAll> l = d.findAll();

            textArea_Info.setText("");  // 清空文本域

            int count = 0; // 计数器
            for (int i = l.size() - 1; i >= 0; i--) {
                count++;
                textArea_Info.append(l.get(i).toString());
                textArea_Info.append("——————————————————————————————————————————————————————————————");
                // 最多输出前10条
                if (count == 10) {
                    break;
                }
            }
            textArea_Info.append("\n刷新完毕:共" + count + "条记录！\n");
        });

    }

    // ================================================================================================================
    // ================================================================================================================
    // ================================================================================================================
    // ================================================================================================================
    // ================================================================================================================
    // ================================================================================================================


    /**
     * 主界面
     */
    private static void mainFrame() {

    }

    // ================================================================================================================
    // ================================================================================================================
    // ================================================================================================================
    // ================================================================================================================
    // ================================================================================================================
    // ================================================================================================================

    /**
     * 注册界面
     */
    private static void registerFrame() {
        /*
        窗口绘制
         */
        JFrame jFrame = SwingUtil.createNormalFrame("注册", 1.5);
        jFrame.setLayout(new GridLayout(2, 1));

        JPanel jPanel1 = new JPanel(new FlowLayout());
        jPanel1.setBorder(new EmptyBorder(20, 300, 10, 300));

        JPanel jPanel2 = new JPanel(new FlowLayout());
        jPanel2.setBorder(new EmptyBorder(10, 100, 10, 100));

        JLabel jLabel_name = SwingUtil.createNormalLabel("姓名     ：", 45);
        JLabel jLabel_phone = SwingUtil.createNormalLabel("手机号  ：", 45);
        JLabel jLabel_idnum = SwingUtil.createNormalLabel("身份证  ：", 45);
        JLabel jLabel_psw = SwingUtil.createNormalLabel("密码     ：", 45);


        JTextField jTextField_name = SwingUtil.createNormalTextField("", 40, 10);
        JTextField jTextField_phone = SwingUtil.createNormalTextField("", 40, 10);
        JTextField jTextField_idnum = SwingUtil.createNormalTextField("", 40, 10);
        JPasswordField jPasswordField = SwingUtil.createNormalPasswordField("", 40, 10);

        JButton button_register = SwingUtil.createNormalButton("注册", 10, 30);
        JButton button_exit = SwingUtil.createNormalButton("返回", 10, 30);


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

        jFrame.setVisible(true);

        /*
        监听事件
         */
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
                // 获得文本框和密码框内的文本并赋值
                name = jTextField_name.getText();
                phone = jTextField_phone.getText();
                idnum = jTextField_idnum.getText();
                psw = String.valueOf(jPasswordField.getPassword());

            } catch (Exception exception) {
                JOptionPane.showMessageDialog(jFrame, "错误!");
            }

            // 当表格所有元素都不为空时，开始向数据库的用户表插入信息
            if (name.isEmpty() || phone.isEmpty() || idnum.isEmpty() || psw.isEmpty()) {
                JOptionPane.showMessageDialog(jFrame, "信息不能为空!");
            } else {
                // 创建customer对象
                Customer customer = new Customer();
                customer.setName(name);
                customer.setPhone(phone);
                customer.setIdnum(idnum);
                customer.setPassword(psw);

                CustomerDao customerDao = new CustomerDaoImpl();

                // 检查是否存在相同的电话号码，若存在，注册失败
                Customer c1 = new Customer();
                c1.setPhone(phone);

                // phone unique
                if (!customerDao.checkRegister(c1).isEmpty()) {
                    jTextField_idnum.setText("");
                    jTextField_name.setText("");
                    jTextField_phone.setText("");
                    jPasswordField.setText("");
                    SwingUtil.showMessage(jFrame, "手机号已存在");
                }

                // phone length and idnum length
                if (phone.length() > 11 || idnum.length() > 18) {
                    SwingUtil.showMessage(jFrame, "手机号或身份证号格式不正确");
                }

                int n = 0;  // 返回结果行数
                try {
                    // 开始注册
                    n = customerDao.register(customer); // n＝１，即为成功
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

                // 注册后将账号密码带入验证是否可以登陆
                Customer c2 = new Customer();
                c2.setPhone(phone);
                c2.setPassword(psw);

                List<Customer> list = customerDao.checkLogin(c2);

                // 验证通过后，发送消息框
                if (n == 1 && list.size() == 1) {
                    SwingUtil.showMessage(jFrame, "注册信息提交成功，等待管理员审核!");
                    jFrame.dispose();
                } else {
                    jTextField_idnum.setText("");
                    jTextField_name.setText("");
                    jTextField_phone.setText("");
                    jPasswordField.setText("");
                    SwingUtil.showMessage(jFrame, "输入不正确，注册失败！");
                }
            }
        });
    }
}
