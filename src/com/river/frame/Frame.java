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
import com.river.util.RoomUtil;
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
        jPanel1.setBorder(new EmptyBorder(20, 110, 10, 0));

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
        JButton button_findPassword = SwingUtil.createNormalButton("找密", 30, 15);

        jLabel_title.setHorizontalAlignment(SwingConstants.CENTER);

        jPanel1.add(jLabel_title);
        jPanel1.add(button_register);
        jPanel1.add(button_findPassword);
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

        // 进入找回密码页面
        button_findPassword.addActionListener(e -> findPasswordFrame());


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
                // 管理员后台
                if (list.get(0).getId() == 1) {
                    adminFrame(phone);
                } else {
                    // 非vip无法进入
                    if (!list.get(0).getVip()) {
                        SwingUtil.showMessage(jFrame, "请等待管理员审核你的vip状态");
                    } else {
                        // 主界面
                        mainFrame(phone);
                    }
                }
                // 无法成功登陆的情况
            } else {
                // 纠正输入信息
                if (phone.isEmpty() || psw.isEmpty()) {
                    SwingUtil.showMessage(jFrame, "请输入手机号或密码");
                } else if (phone.length() > 11) {
                    SwingUtil.showMessage(jFrame, "请输入正确的号码");
                } else {
                    // 错误时清除密码框
                    jPasswordField.setText("");
                    SwingUtil.showMessage(jFrame, "手机号或密码错误!\nTip:如果忘记，请联系管理员获得改密密钥");
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
     * 管理员主界面
     */
    private static void adminFrame(String phone) {
        /*
        窗口绘制
         */
        JFrame jFrame = SwingUtil.createNormalFrame("管理员界面", 1.5);
        // 主界面布局
        jFrame.setLayout(new GridLayout(1, 2, 1, 1));

        JPanel jPanel_text = new JPanel();
        JPanel jPanel_button = new JPanel();

        // jPanel_text部分
        JTextArea textArea_Info = SwingUtil.createNormalTextArea(20, 21, 30);
        textArea_Info.setEditable(false);   // 禁止用户编辑
        JScrollPane scrollPane = new JScrollPane(textArea_Info);

        // 添加数据
        Customer customer1 = new Customer(); // 使用customer储存信息
        customer1.setPhone(phone);
        // 使用dao与数据库沟通
        CustomerDao customerDao = new CustomerDaoImpl();
        List<Customer> customerList = customerDao.findOne(customer1);

        // 添加个人基本数据到文本域
        textArea_Info.append("欢迎使用客房管理系统\n\n\n");
        textArea_Info.append("------------------- 基 本 信 息 ------------------\n");
        textArea_Info.append(customerList.get(0).showInfo(customer1));
        textArea_Info.append("------------------------------------------------------\n");

        jPanel_text.add(scrollPane);

        // jPanel_button
        jPanel_button.setLayout(new GridLayout(2, 1, 10, 10));
        JButton button_room = SwingUtil.createNormalButton("房间变更管理");
        JButton button_userInfo = SwingUtil.createNormalButton("用户信息管理");


        jPanel_button.add(button_room);
        jPanel_button.add(button_userInfo);

        jFrame.add(jPanel_text);
        jFrame.add(jPanel_button);

        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        /*
        监听
         */
        button_room.addActionListener(e -> adminRoomManageFrame());

        button_userInfo.addActionListener(e -> adminCustomerManageFrame());

    }


    // ================================================================================================================
    // ================================================================================================================
    // ================================================================================================================
    // ================================================================================================================
    // ================================================================================================================
    // ================================================================================================================


    /**
     * 用户信息管理界面
     */
    private static void adminCustomerManageFrame() {
        /*
        窗口绘制
         */
        JFrame jFrame = SwingUtil.createNormalFrame("用户信息管理界面");
        jFrame.setLayout(new GridLayout(2, 1, 10, 10));

        JPanel jPanel_topScreen = new JPanel();
        JPanel jPanel_menu = new JPanel();
        JPanel jPanel_bottomScreen = new JPanel();


        // 显示顶部文本域
        JTextArea textArea_Info = SwingUtil.createNormalTextArea(15, 65, 30);
        textArea_Info.setEditable(false);   // 禁止用户编辑
        JScrollPane scrollPane = new JScrollPane(textArea_Info);

        CustomerDao customerDao = new CustomerDaoImpl();
        List<Customer> list = customerDao.findAll();

        for (Customer c : list) {
            textArea_Info.append(c.showMoreInfo(c));
            textArea_Info.append("——————————————————————————————————————————————————————————————\n");
        }

        jPanel_topScreen.add(scrollPane);


        // 显示菜单栏按钮

        jPanel_menu.setBorder(new EmptyBorder(10, 10, 10, 200));

        JButton button_refresh = SwingUtil.createNormalButton("刷新全部", 8, 30);
        JButton button_vip = SwingUtil.createNormalButton("仅显示vip", 8, 30);
        JButton button_notVip = SwingUtil.createNormalButton("仅显示非vip", 8, 30);

        jPanel_menu.add(button_refresh);
        jPanel_menu.add(button_vip);
        jPanel_menu.add(button_notVip);


        // jPanel_Bottom
        // label
        JLabel label_judge = SwingUtil.createNormalLabel("审核非vip账号：", 30);


        // 订单房间管理panel
        JPanel jPanel_change = new JPanel(new FlowLayout());


        // 显示非vip账号
        List<Customer> notVipList = customerDao.findNotVip();

        // 新建下拉框
        JComboBox<String> box_judge = new JComboBox<>();
        box_judge.setFont(new Font("Arial", Font.PLAIN, 30));
        box_judge.setBorder(new EmptyBorder(4, 10, 4, 10)); // 设置边距

        // 将查询得到的notVipList中的每一个对象的phone获取并给予下拉框
        for (Customer customer : notVipList) {
            box_judge.addItem(customer.getPhone());
        }

        // 展示当前手机号的号主
        JTextField textField_name = SwingUtil.createNormalTextField("", 30, 3);
        //textField_nowRoom.addFocusListener(new JTextFieldHintListener(textField_nowRoom, "无可选")); // 提示字符

        // 将当前手机号给予Customer
        Customer customer = new Customer();
        if (box_judge.getSelectedItem() != null) {
            customer.setPhone((String) box_judge.getSelectedItem());
            textField_name.setText(String.valueOf(customerDao.findNameByPhone(customer).get(0).getName()));
        }

        textField_name.setEditable(false);   // 禁止用户编辑


        // 通过按钮
        JButton button_pass = SwingUtil.createNormalButton("通过", 20, 18);

        // 全部通过按钮
        JButton button_passAll = SwingUtil.createNormalButton("全部通过", 20, 15);


        jPanel_change.add(label_judge);
        jPanel_change.add(box_judge);
        jPanel_change.add(textField_name);
        jPanel_change.add(button_pass);
        jPanel_change.add(button_passAll);

        // 将menu和更换房间放入bottomScreen
        jPanel_bottomScreen.add(jPanel_menu);
        jPanel_bottomScreen.add(jPanel_change);


        jFrame.add(jPanel_topScreen);
        jFrame.add(jPanel_bottomScreen);


        jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        jFrame.setVisible(true);

        /*
        监听
         */
        // 刷新所有用户信息
        button_refresh.addActionListener(e -> {
            // 清空文本域
            textArea_Info.setText("");
            List<Customer> l = customerDao.findAll();
            int count = 0;  // 计数器
            for (Customer c : l) {
                count++;
                textArea_Info.append(c.showMoreInfo(c));
                textArea_Info.append("——————————————————————————————————————————————————————————————\n");
            }
            textArea_Info.append("\n刷新完毕:总共" + count + "个账户！\n");
        });

        // 查看vip用户
        button_vip.addActionListener(e -> {
            // 清空文本域
            textArea_Info.setText("");
            List<Customer> l = customerDao.findVip();
            int count = 0;  // 计数器
            for (Customer c : l) {
                count++;
                textArea_Info.append(c.showMoreInfo(c));
                textArea_Info.append("——————————————————————————————————————————————————————————————\n");
            }
            textArea_Info.append("\n刷新完毕:总共" + count + "个vip账户！\n");
        });

        // 查看非vip用户
        button_notVip.addActionListener(e -> {
            // 清空文本域
            textArea_Info.setText("");
            List<Customer> l = customerDao.findNotVip();
            int count = 0;  // 计数器
            for (Customer c : l) {
                count++;
                textArea_Info.append(c.showMoreInfo(c));
                textArea_Info.append("——————————————————————————————————————————————————————————————\n");
            }
            textArea_Info.append("\n刷新完毕:总共" + count + "个非vip账户！\n");
        });

        button_pass.addActionListener(e -> {
            // 获取手机号
            String phone = (String) box_judge.getSelectedItem();
            int n = customerDao.passVip(phone);
            if (n == 1) {
                SwingUtil.showMessage(jFrame, "成功");
            } else {
                SwingUtil.showMessage(jFrame, "失败");
            }
        });

        // 全部通过
        button_passAll.addActionListener(e -> {
            int n = customerDao.passVipAll();
            SwingUtil.showMessage(jFrame, "共通过了" + n + "个账户");
        });

        // box_judge下拉框调动，姓名的改变
        box_judge.addActionListener(e -> {
            if (box_judge.getSelectedItem() != null) {
                Customer c = new Customer();
                c.setPhone((String) box_judge.getSelectedItem());
                textField_name.setText(customerDao.findOne(c).get(0).getName());
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
    private static void adminRoomManageFrame() {
        /*
        窗口绘制
         */
        JFrame jFrame = SwingUtil.createNormalFrame("房间变更管理界面");
        jFrame.setLayout(new GridLayout(2, 1, 10, 10));

        JPanel jPanel_topScreen = new JPanel();
        JPanel jPanel_menu = new JPanel();
        JPanel jPanel_bottomScreen = new JPanel();


        // 显示顶部文本域
        JTextArea textArea_Info = SwingUtil.createNormalTextArea(15, 65, 30);
        textArea_Info.setEditable(false);   // 禁止用户编辑
        JScrollPane scrollPane = new JScrollPane(textArea_Info);

        DetailAllDao dao = new DetailAllDaoImpl();
        List<DetailAll> list = dao.findAll();

        for (DetailAll detailAll : list) {
            textArea_Info.append(detailAll.toString());
            textArea_Info.append("——————————————————————————————————————————————————————————————\n");
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
        DetailAllDao detailAllDao = new DetailAllDaoImpl();
        List<DetailAll> list_business = detailAllDao.findAllStillIn();

        JComboBox<Integer> box_business = new JComboBox<>();
        box_business.setFont(new Font("Arial", Font.PLAIN, 30));
        box_business.setBorder(new EmptyBorder(4, 10, 4, 10)); // 设置边距

        // 将查询得到的list_business中的每一个对象的business获取并给予下拉框
        for (DetailAll listBusiness : list_business) {
            box_business.addItem(listBusiness.getBusiness());
        }

        // 展示当前订单的房间号
        JTextField textField_nowRoom = SwingUtil.createNormalTextField("", 30, 3);
        //textField_nowRoom.addFocusListener(new JTextFieldHintListener(textField_nowRoom, "无可选")); // 提示字符

        // 将当前单号对应房间号给予文本框
        RoomOperation nowRoom1 = new RoomOperation();
        if (box_business.getSelectedItem() != null) {
            nowRoom1.setBusiness((Integer) box_business.getSelectedItem());
            RoomOperationDao roomOperationDao1 = new RoomOperationImpl();
            textField_nowRoom.setText(String.valueOf(roomOperationDao1.selcetRoomnoByBusiness(nowRoom1).get(0).getRoomno()));
        }


        textField_nowRoom.setEditable(false);   // 禁止用户编辑


        // 显示可用房间下拉框
        // 创建房间信息对象，专门可用房间号
        RoomInfoDao roomInfoDao = new RoomInfoDaoImpl();
        List<RoomInfo> empytRoomList = roomInfoDao.selectEmptyRoom();


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
                textArea_Info.append("——————————————————————————————————————————————————————————————\n");
            }

            textArea_Info.append("\n刷新完毕:总共" + count + "条记录！\n");
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
                textArea_Info.append("——————————————————————————————————————————————————————————————\n");
                // 最多输出前10条
                if (count == 10) {
                    break;
                }
            }
            textArea_Info.append("\n刷新完毕:当前显示" + count + "条记录！\n");
        });

        // 仍在持续的单号
        button_current.addActionListener(e -> {
            DetailAllDao d = new DetailAllDaoImpl();
            List<DetailAll> l = d.findAllStillIn();

            textArea_Info.setText("");  // 清空文本域

            int count = 0; // 计数器
            for (DetailAll detailAll : l) {
                count++;
                textArea_Info.append(detailAll.toString());
                textArea_Info.append("——————————————————————————————————————————————————————————————\n");
            }
            textArea_Info.append("\n刷新完毕:共" + count + "条'预约'和'在住'的记录！\n");
        });

        // box_business下拉框调动textField_nowRoom房间号的改变
        box_business.addActionListener(e -> {
            RoomOperation nowRoom = new RoomOperation();
            if (box_business.getSelectedItem() != null) {
                nowRoom.setBusiness((Integer) box_business.getSelectedItem());
                RoomOperationDao roomOperationDao1 = new RoomOperationImpl();
                textField_nowRoom.setText(String.valueOf(roomOperationDao1.selcetRoomnoByBusiness(nowRoom).get(0).getRoomno()));
            }
        });

        // 换房按钮
        button_changeRoom.addActionListener(e -> {
            // 保证能获取到之前、之后的房间号
            if (textField_nowRoom.getText().equals("") || box_room.getSelectedItem() == null || box_business.getSelectedItem() == null) {
                SwingUtil.showMessage(jFrame, "无选中");
            } else {
                int beforeRoomNo = Integer.parseInt(textField_nowRoom.getText());
                int changeRoomNo = (int) box_room.getSelectedItem();
                int business = (int) box_business.getSelectedItem();

                if (beforeRoomNo == changeRoomNo) {
                    SwingUtil.showMessage(jFrame, "Error! Check the console");
                    System.err.println("存在roomInfo表和roomOperation不一致的问题，请手动前往数据库修改");
                } else {
                    RoomOperationDao r = new RoomOperationImpl();
                    int n = r.changeRoom(beforeRoomNo, changeRoomNo, business);
                    if (n != 0) {
                        RoomInfoDao r2 = new RoomInfoDaoImpl();
                        int n1 = r2.makeRoomEmpty(beforeRoomNo);
                        int n2 = r2.makeRoomOccupy(changeRoomNo);
                        // 校验
                        if (n1 == 1 && n2 == 1) {
                            SwingUtil.showMessage(jFrame, "更换成功！");
                            jFrame.dispose();
                        } else {
                            SwingUtil.showMessage(jFrame, "更新失败");
                        }
                    }
                }
            }
        });

        // 退房按钮
        button_delete.addActionListener(e -> {
            if (textField_nowRoom.getText().equals("")) {
                SwingUtil.showMessage(jFrame, "无选中");
            } else {
                // 获取数据
                int no = Integer.parseInt(textField_nowRoom.getText());

                RoomInfoDao r1 = new RoomInfoDaoImpl();
                int n1 = r1.makeRoomEmpty(no);
                RoomOperationDao r2 = new RoomOperationImpl();
                int n2 = r2.outRoom(no, (Integer) box_business.getSelectedItem());

                // 校验
                if (n1 == 1 && n2 == 1) {
                    SwingUtil.showMessage(jFrame, "退房成功!");
                    jFrame.dispose();
                } else {
                    SwingUtil.showMessage(jFrame, "出现问题");
                    System.out.println(n1 + " " + n2);
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
     * 主界面
     */
    private static void mainFrame(String phone) {
        /*
        窗口绘制
         */
        JFrame jFrame = SwingUtil.createNormalFrame("主界面", 1.5);
        // 主界面布局
        jFrame.setLayout(new GridLayout(1, 2, 1, 1));

        JPanel jPanel_text = new JPanel();
        JPanel jPanel_button = new JPanel();

        // jPanel_text部分
        JTextArea textArea_Info = SwingUtil.createNormalTextArea(20, 21, 30);
        textArea_Info.setEditable(false);   // 禁止用户编辑
        JScrollPane scrollPane = new JScrollPane(textArea_Info);

        // 添加数据
        Customer customer1 = new Customer(); // 使用customer储存信息
        customer1.setPhone(phone);
        // 使用dao与数据库沟通
        CustomerDao customerDao = new CustomerDaoImpl();
        List<Customer> customerList = customerDao.findOne(customer1);

        // 添加个人基本数据到文本域
        textArea_Info.append("欢迎使用客房管理系统\n\n\n");
        textArea_Info.append("------------------- 基 本 信 息 ------------------\n");
        textArea_Info.append(customerList.get(0).showInfo(customer1));
        textArea_Info.append("------------------------------------------------------\n");

        jPanel_text.add(scrollPane);

        // jPanel_button
        jPanel_button.setLayout(new GridLayout(3, 1, 10, 10));
        JButton button_room = SwingUtil.createNormalButton("房间变更");
        JButton button_userInfo = SwingUtil.createNormalButton("信息修改");
        JButton button_charge = SwingUtil.createNormalButton("余额充值");
        JButton button_help = SwingUtil.createNormalButton("帮助");


        jPanel_button.add(button_room);
        jPanel_button.add(button_userInfo);
        jPanel_button.add(button_charge);
        jPanel_button.add(button_help);

        jFrame.add(jPanel_text);
        jFrame.add(jPanel_button);

        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        /*
        监听
         */
        button_room.addActionListener(e -> roomManageFrame(phone));

        button_userInfo.addActionListener(e -> customerInfoManageFrame(phone));

        button_charge.addActionListener(e -> chargeFrame(phone));

        button_help.addActionListener(e ->
                SwingUtil.showMessage(jFrame, "本酒店客房采用会员制，只有会员能登陆进入。对于预约和订房，仅作参考使用，您需要有能匹配订房房间天数对应的余额" +
                        "才可以订房。\n一旦房间被你所定，其他人无法订下你所定的房间，即使其他人的预约进入时间晚于你的退房时间。退房时间并非一定" +
                        "是您的退房时间，您可以选择不退，但在结算时，会额外根据天数扣除您的余额。\n您可以在预约时间之前就退订房间，但是为了弥补" +
                        "您预约之后其他人无法预约的损失，您会被扣除少量违约占用费。")
        );
    }

    // ================================================================================================================
    // ================================================================================================================
    // ================================================================================================================
    // ================================================================================================================
    // ================================================================================================================
    // ================================================================================================================

    private static void helpFrame() {
        JFrame jFrame = new JFrame();
        String text = "本酒店客房采用会员制，只有会员能登陆进入。对于预约和订房，仅作参考使用，您需要有能匹配订房房间天数对应的余额" +
                "才可以订房。一旦房间被你所定，其他人无法订下你所定的房间，即使其他人的预约进入时间晚于你的退房时间。退房时间并非一定" +
                "是您的退房时间，您可以选择不退，但在结算时，会额外根据天数扣除您的余额。您可以在预约时间之前就退订房间，但是为了弥补" +
                "您预约之后其他人无法预约的损失，您会被扣除少量违约占用费。/n +" +
                "订房前，我们会评估您目前余额是否足够，然后在您退房前，我们不会减少您的余额，这一切会在您退房时计算。";

        JTextArea tips = SwingUtil.createNormalTextArea(30, 18, 15);

        jFrame.add(tips);
        jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        jFrame.setVisible(true);
    }

    // ================================================================================================================
    // ================================================================================================================
    // ================================================================================================================
    // ================================================================================================================
    // ================================================================================================================
    // ================================================================================================================

    private static void customerInfoManageFrame(String phone) {
        /*
        窗口绘制
         */
        JFrame jFrame = SwingUtil.createNormalFrame("用户信息管理界面");
        jFrame.setLayout(new GridLayout(2, 1, 10, 10));

        JPanel jPanel_topScreen = new JPanel();
        JPanel jPanel_menu = new JPanel();
        JPanel jPanel_bottomScreen = new JPanel();


        // 显示顶部文本域
        JTextArea textArea_Info = SwingUtil.createNormalTextArea(15, 65, 30);
        textArea_Info.setEditable(false);   // 禁止用户编辑
        JScrollPane scrollPane = new JScrollPane(textArea_Info);

        CustomerDao customerDao = new CustomerDaoImpl();
        List<Customer> list = customerDao.findAll();

        for (Customer c : list) {
            textArea_Info.append(c.showMoreInfo(c));
            textArea_Info.append("——————————————————————————————————————————————————————————————\n");
        }

        jPanel_topScreen.add(scrollPane);


        // 显示菜单栏按钮

        jPanel_menu.setBorder(new EmptyBorder(10, 10, 10, 200));

        JButton button_refresh = SwingUtil.createNormalButton("刷新全部", 8, 30);
        JButton button_vip = SwingUtil.createNormalButton("仅显示vip", 8, 30);
        JButton button_notVip = SwingUtil.createNormalButton("仅显示非vip", 8, 30);

        jPanel_menu.add(button_refresh);
        jPanel_menu.add(button_vip);
        jPanel_menu.add(button_notVip);


        // jPanel_Bottom
        // label
        JLabel label_judge = SwingUtil.createNormalLabel("审核非vip账号：", 30);


        // 订单房间管理panel
        JPanel jPanel_change = new JPanel(new FlowLayout());


        // 显示非vip账号
        List<Customer> notVipList = customerDao.findNotVip();

        // 新建下拉框
        JComboBox<String> box_judge = new JComboBox<>();
        box_judge.setFont(new Font("Arial", Font.PLAIN, 30));
        box_judge.setBorder(new EmptyBorder(4, 10, 4, 10)); // 设置边距

        // 将查询得到的notVipList中的每一个对象的phone获取并给予下拉框
        for (Customer customer : notVipList) {
            box_judge.addItem(customer.getPhone());
        }

        // 展示当前手机号的号主
        JTextField textField_name = SwingUtil.createNormalTextField("", 30, 3);
        //textField_nowRoom.addFocusListener(new JTextFieldHintListener(textField_nowRoom, "无可选")); // 提示字符

        // 将当前手机号给予Customer
        Customer customer = new Customer();
        if (box_judge.getSelectedItem() != null) {
            customer.setPhone((String) box_judge.getSelectedItem());
            textField_name.setText(String.valueOf(customerDao.findNameByPhone(customer).get(0).getName()));
        }

        textField_name.setEditable(false);   // 禁止用户编辑


        // 通过按钮
        JButton button_pass = SwingUtil.createNormalButton("通过", 20, 18);

        // 全部通过按钮
        JButton button_passAll = SwingUtil.createNormalButton("全部通过", 20, 15);


        jPanel_change.add(label_judge);
        jPanel_change.add(box_judge);
        jPanel_change.add(textField_name);
        jPanel_change.add(button_pass);
        jPanel_change.add(button_passAll);

        // 将menu和更换房间放入bottomScreen
        jPanel_bottomScreen.add(jPanel_menu);
        jPanel_bottomScreen.add(jPanel_change);


        jFrame.add(jPanel_topScreen);
        jFrame.add(jPanel_bottomScreen);


        jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        jFrame.setVisible(true);

        /*
        监听
         */
        // 刷新所有用户信息
        button_refresh.addActionListener(e -> {
            // 清空文本域
            textArea_Info.setText("");
            List<Customer> l = customerDao.findAll();
            int count = 0;  // 计数器
            for (Customer c : l) {
                count++;
                textArea_Info.append(c.showMoreInfo(c));
                textArea_Info.append("——————————————————————————————————————————————————————————————\n");
            }
            textArea_Info.append("\n刷新完毕:总共" + count + "个账户！\n");
        });

        // 查看vip用户
        button_vip.addActionListener(e -> {
            // 清空文本域
            textArea_Info.setText("");
            List<Customer> l = customerDao.findVip();
            int count = 0;  // 计数器
            for (Customer c : l) {
                count++;
                textArea_Info.append(c.showMoreInfo(c));
                textArea_Info.append("——————————————————————————————————————————————————————————————\n");
            }
            textArea_Info.append("\n刷新完毕:总共" + count + "个vip账户！\n");
        });

        // 查看非vip用户
        button_notVip.addActionListener(e -> {
            // 清空文本域
            textArea_Info.setText("");
            List<Customer> l = customerDao.findNotVip();
            int count = 0;  // 计数器
            for (Customer c : l) {
                count++;
                textArea_Info.append(c.showMoreInfo(c));
                textArea_Info.append("——————————————————————————————————————————————————————————————\n");
            }
            textArea_Info.append("\n刷新完毕:总共" + count + "个非vip账户！\n");
        });

        button_pass.addActionListener(e -> {
            // 获取手机号
            int n = customerDao.passVip(phone);
            if (n == 1) {
                SwingUtil.showMessage(jFrame, "成功");
            } else {
                SwingUtil.showMessage(jFrame, "失败");
            }
        });

        // 全部通过
        button_passAll.addActionListener(e -> {
            int n = customerDao.passVipAll();
            SwingUtil.showMessage(jFrame, "共通过了" + n + "个账户");
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
    private static void adminRoomManageFrame(String phone) {
        /*
        窗口绘制
         */
        JFrame jFrame = SwingUtil.createNormalFrame("房间变更管理界面");
        jFrame.setLayout(new GridLayout(2, 1, 10, 10));

        JPanel jPanel_topScreen = new JPanel();
        JPanel jPanel_menu = new JPanel();
        JPanel jPanel_bottomScreen = new JPanel();


        // 显示顶部文本域
        JTextArea textArea_Info = SwingUtil.createNormalTextArea(15, 65, 30);
        textArea_Info.setEditable(false);   // 禁止用户编辑
        JScrollPane scrollPane = new JScrollPane(textArea_Info);

        DetailAllDao dao = new DetailAllDaoImpl();
        List<DetailAll> list = dao.findAll();

        for (DetailAll detailAll : list) {
            textArea_Info.append(detailAll.toString());
            textArea_Info.append("——————————————————————————————————————————————————————————————\n");
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
        DetailAllDao detailAllDao = new DetailAllDaoImpl();
        List<DetailAll> list_business = detailAllDao.findAllStillIn();

        JComboBox<Integer> box_business = new JComboBox<>();
        box_business.setFont(new Font("Arial", Font.PLAIN, 30));
        box_business.setBorder(new EmptyBorder(4, 10, 4, 10)); // 设置边距

        // 将查询得到的list_business中的每一个对象的business获取并给予下拉框
        for (DetailAll listBusiness : list_business) {
            box_business.addItem(listBusiness.getBusiness());
        }

        // 展示当前订单的房间号
        JTextField textField_nowRoom = SwingUtil.createNormalTextField("", 30, 3);
        //textField_nowRoom.addFocusListener(new JTextFieldHintListener(textField_nowRoom, "无可选")); // 提示字符

        // 将当前单号对应房间号给予文本框
        RoomOperation nowRoom1 = new RoomOperation();
        if (box_business.getSelectedItem() != null) {
            nowRoom1.setBusiness((Integer) box_business.getSelectedItem());
            RoomOperationDao roomOperationDao1 = new RoomOperationImpl();
            textField_nowRoom.setText(String.valueOf(roomOperationDao1.selcetRoomnoByBusiness(nowRoom1).get(0).getRoomno()));
        }


        textField_nowRoom.setEditable(false);   // 禁止用户编辑


        // 显示可用房间下拉框
        // 创建房间信息对象，专门可用房间号
        RoomInfoDao roomInfoDao = new RoomInfoDaoImpl();
        List<RoomInfo> empytRoomList = roomInfoDao.selectEmptyRoom();


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
                textArea_Info.append("——————————————————————————————————————————————————————————————\n");
            }

            textArea_Info.append("\n刷新完毕:总共" + count + "条记录！\n");
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
                textArea_Info.append("——————————————————————————————————————————————————————————————\n");
                // 最多输出前10条
                if (count == 10) {
                    break;
                }
            }
            textArea_Info.append("\n刷新完毕:当前显示" + count + "条记录！\n");
        });

        // 仍在持续的单号
        button_current.addActionListener(e -> {
            DetailAllDao d = new DetailAllDaoImpl();
            List<DetailAll> l = d.findAllStillIn();

            textArea_Info.setText("");  // 清空文本域

            int count = 0; // 计数器
            for (DetailAll detailAll : l) {
                count++;
                textArea_Info.append(detailAll.toString());
                textArea_Info.append("——————————————————————————————————————————————————————————————\n");
            }
            textArea_Info.append("\n刷新完毕:共" + count + "条'预约'和'在住'的记录！\n");
        });

        // box_business下拉框调动textField_nowRoom房间号的改变
        box_business.addActionListener(e -> {
            RoomOperation nowRoom = new RoomOperation();
            if (box_business.getSelectedItem() != null) {
                nowRoom.setBusiness((Integer) box_business.getSelectedItem());
                RoomOperationDao roomOperationDao1 = new RoomOperationImpl();
                textField_nowRoom.setText(String.valueOf(roomOperationDao1.selcetRoomnoByBusiness(nowRoom).get(0).getRoomno()));
            }
        });

        // 换房按钮
        button_changeRoom.addActionListener(e -> {
            // 保证能获取到之前、之后的房间号
            if (textField_nowRoom.getText().equals("") || box_room.getSelectedItem() == null || box_business.getSelectedItem() == null) {
                SwingUtil.showMessage(jFrame, "无选中");
            } else {
                int beforeRoomNo = Integer.parseInt(textField_nowRoom.getText());
                int changeRoomNo = (int) box_room.getSelectedItem();
                int business = (int) box_business.getSelectedItem();

                if (beforeRoomNo == changeRoomNo) {
                    SwingUtil.showMessage(jFrame, "Error! Check the console");
                    System.err.println("存在roomInfo表和roomOperation不一致的问题，请手动前往数据库修改");
                } else {
                    RoomOperationDao r = new RoomOperationImpl();
                    int n = r.changeRoom(beforeRoomNo, changeRoomNo, business);
                    if (n != 0) {
                        RoomInfoDao r2 = new RoomInfoDaoImpl();
                        int n1 = r2.makeRoomEmpty(beforeRoomNo);
                        int n2 = r2.makeRoomOccupy(changeRoomNo);
                        // 校验
                        if (n1 == 1 && n2 == 1) {
                            SwingUtil.showMessage(jFrame, "更换成功！");
                            jFrame.dispose();
                        } else {
                            SwingUtil.showMessage(jFrame, "更新失败");
                        }
                    }
                }
            }
        });

        // 退房按钮
        button_delete.addActionListener(e -> {
            if (textField_nowRoom.getText().equals("")) {
                SwingUtil.showMessage(jFrame, "无选中");
            } else {
                // 获取数据
                int no = Integer.parseInt(textField_nowRoom.getText());

                RoomInfoDao r1 = new RoomInfoDaoImpl();
                int n1 = r1.makeRoomEmpty(no);
                RoomOperationDao r2 = new RoomOperationImpl();
                int n2 = r2.outRoom(no, (Integer) box_business.getSelectedItem());

                // 校验
                if (n1 == 1 && n2 == 1) {
                    SwingUtil.showMessage(jFrame, "退房成功!");
                    int money = RoomUtil.changeMoney(box_business.getSelectedItem().toString());
                    SwingUtil.showMessage(jFrame, "该账户余额" + money + "元");
                    jFrame.dispose();
                } else {
                    SwingUtil.showMessage(jFrame, "出现问题");
                    System.out.println(n1 + " " + n2);
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
     * 用户定房
     */
    private static void roomManageFrame(String phone) {
        /*
        窗口绘制
         */
        JFrame jFrame = SwingUtil.createNormalFrame("房间变更");
        jFrame.setLayout(new GridLayout(2, 1, 10, 10));

        JPanel jPanel_topScreen = new JPanel();
        JPanel jPanel_menu = new JPanel();
        JPanel jPanel_bottomScreen = new JPanel();


        // 显示顶部文本域
        JTextArea textArea_Info = SwingUtil.createNormalTextArea(15, 65, 30);
        textArea_Info.setEditable(false);   // 禁止用户编辑
        JScrollPane scrollPane = new JScrollPane(textArea_Info);

        DetailAllDao dao = new DetailAllDaoImpl();
        List<DetailAll> list = dao.findOne(phone);

        for (DetailAll detailAll : list) {
            textArea_Info.append(detailAll.toString());
            textArea_Info.append("——————————————————————————————————————————————————————————————\n");
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
        DetailAllDao detailAllDao = new DetailAllDaoImpl();
        List<DetailAll> list_business = detailAllDao.findAllStillIn(phone);

        JComboBox<Integer> box_business = new JComboBox<>();
        box_business.setFont(new Font("Arial", Font.PLAIN, 30));
        box_business.setBorder(new EmptyBorder(4, 10, 4, 10)); // 设置边距

        // 将查询得到的list_business中的每一个对象的business获取并给予下拉框
        for (DetailAll listBusiness : list_business) {
            box_business.addItem(listBusiness.getBusiness());
        }

        // 展示当前订单的房间号
        JTextField textField_nowRoom = SwingUtil.createNormalTextField("", 30, 3);
        //textField_nowRoom.addFocusListener(new JTextFieldHintListener(textField_nowRoom, "无可选")); // 提示字符

        // 将当前单号对应房间号给予文本框
        RoomOperation nowRoom1 = new RoomOperation();
        if (box_business.getSelectedItem() != null) {
            nowRoom1.setBusiness((Integer) box_business.getSelectedItem());
            RoomOperationDao roomOperationDao1 = new RoomOperationImpl();
            textField_nowRoom.setText(String.valueOf(roomOperationDao1.selcetRoomnoByBusiness(nowRoom1).get(0).getRoomno()));
        }


        textField_nowRoom.setEditable(false);   // 禁止用户编辑


        // 显示可用房间下拉框
        // 创建房间信息对象，专门可用房间号
        RoomInfoDao roomInfoDao = new RoomInfoDaoImpl();
        List<RoomInfo> emptyRoomList = roomInfoDao.selectEmptyRoom();


        // 设置box的基本信息
        JComboBox<Integer> box_room = new JComboBox<>();
        box_room.setFont(new Font("Arial", Font.PLAIN, 30));
        box_room.setBorder(new EmptyBorder(4, 10, 4, 10)); // 设置边距

        // 将查询得到的list_business中的每一个对象的business获取并给予下拉框
        for (RoomInfo emptyRoom : emptyRoomList) {
            box_room.addItem(emptyRoom.getRoomno());
        }


        // 更换按钮
        JButton button_changeRoom = SwingUtil.createNormalButton("更换", 30, 15);

        // 删除按钮
        JButton button_delete = SwingUtil.createNormalButton("退房", 30, 15);


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
            List<DetailAll> l = d.findOne(phone);

            textArea_Info.setText("");  // 清空文本域

            int count = 0; // 计数器
            for (DetailAll detailAll : l) {
                count++;
                textArea_Info.append(detailAll.toString());
                textArea_Info.append("——————————————————————————————————————————————————————————————\n");
            }

            textArea_Info.append("\n刷新完毕:总共" + count + "条记录！\n");
        });

        // 刷新部分　——　最近10行
        button_10rows.addActionListener(e -> {
            DetailAllDao d = new DetailAllDaoImpl();
            List<DetailAll> l = d.findOne(phone);

            textArea_Info.setText("");  // 清空文本域

            int count = 0; // 计数器
            for (int i = l.size() - 1; i >= 0; i--) {
                count++;
                textArea_Info.append(l.get(i).toString());
                textArea_Info.append("——————————————————————————————————————————————————————————————\n");
                // 最多输出前10条
                if (count == 10) {
                    break;
                }
            }
            textArea_Info.append("\n刷新完毕:当前显示" + count + "条记录！\n");
        });

        // 仍在持续的单号
        button_current.addActionListener(e -> {
            DetailAllDao d = new DetailAllDaoImpl();
            List<DetailAll> l = d.findAllStillIn(phone);

            textArea_Info.setText("");  // 清空文本域

            int count = 0; // 计数器
            for (DetailAll detailAll : l) {
                count++;
                textArea_Info.append(detailAll.toString());
                textArea_Info.append("——————————————————————————————————————————————————————————————\n");
            }
            textArea_Info.append("\n刷新完毕:共" + count + "条'预约'和'在住'的记录！\n");
        });

        // box_下拉框调动textField_nowRoom房间号的改变
        box_business.addActionListener(e -> {
            RoomOperation nowRoom2 = new RoomOperation();
            if (box_business.getSelectedItem() != null) {
                nowRoom1.setBusiness((Integer) box_business.getSelectedItem());
                RoomOperationDao roomOperationDao1 = new RoomOperationImpl();
                textField_nowRoom.setText(String.valueOf(roomOperationDao1.selcetRoomnoByBusiness(nowRoom1).get(0).getRoomno()));
            }
        });

        // 换房按钮
        button_changeRoom.addActionListener(e -> {
            SwingUtil.showMessage(jFrame, "如需换房，请先退房后再选房！");
        });

        // 退房按钮
        button_delete.addActionListener(e -> {
            if (textField_nowRoom.getText().equals("")) {
                SwingUtil.showMessage(jFrame, "无选中");
            } else {
                // 获取数据
                int no = Integer.parseInt(textField_nowRoom.getText());

                RoomInfoDao r1 = new RoomInfoDaoImpl();
                int n1 = r1.makeRoomEmpty(no);
                RoomOperationDao r2 = new RoomOperationImpl();
                int n2 = r2.outRoom(no, (Integer) box_business.getSelectedItem());

                // 校验
                if (n1 == 1 && n2 == 1) {
                    SwingUtil.showMessage(jFrame, "退房成功!");
                    int money = RoomUtil.changeMoney(box_business.getSelectedItem().toString());
                    SwingUtil.showMessage(jFrame, "账户余额" + money + "元");
                    jFrame.dispose();
                } else {
                    SwingUtil.showMessage(jFrame, "出现问题");
                    System.err.println(n1 + " " + n2);
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

    private static void chargeFrame(String phone) {
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

    // ================================================================================================================
    // ================================================================================================================
    // ================================================================================================================
    // ================================================================================================================
    // ================================================================================================================
    // ================================================================================================================

    /**
     * 找回密码页面
     */
    private static void findPasswordFrame() {
        /*
        窗口绘制
         */
        JFrame jFrame = SwingUtil.createNormalFrame("找回密码", 2);
        jFrame.setLayout(new GridLayout(2, 1));

        JPanel jPanel1 = new JPanel(new FlowLayout());
        jPanel1.setBorder(new EmptyBorder(20, 170, 1, 170));

        JPanel jPanel2 = new JPanel(new FlowLayout());
        jPanel2.setBorder(new EmptyBorder(100, 100, 1, 100));

        JLabel jLabel_phone = SwingUtil.createNormalLabel("手机号    ", 40);
        JLabel jLabel_key = SwingUtil.createNormalLabel(" 密钥      ", 40);
        JLabel jLabel_newPassword = SwingUtil.createNormalLabel("新密码    ", 40);
        JLabel jLabel_verifyPassword = SwingUtil.createNormalLabel("确认密码 ", 40);

        JTextField jTextField_phone = SwingUtil.createNormalTextField("", 38, 10);
        JTextField jTextField_key = SwingUtil.createNormalTextField("", 38, 10);
        JPasswordField jPasswordField1 = SwingUtil.createNormalPasswordField("", 38, 10);
        JPasswordField jPasswordField2 = SwingUtil.createNormalPasswordField("", 38, 10);

        JButton button_register = SwingUtil.createNormalButton("确认", 10, 30);

        // panel1
        jPanel1.add(jLabel_phone);
        jPanel1.add(jTextField_phone);
        jPanel1.add(jLabel_key);
        jPanel1.add(jTextField_key);
        jPanel1.add(jLabel_newPassword);
        jPanel1.add(jPasswordField1);
        jPanel1.add(jLabel_verifyPassword);
        jPanel1.add(jPasswordField2);

        // panel2
        jPanel2.add(button_register);

        jFrame.add(jPanel1);
        jFrame.add(jPanel2);

        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        /*
        监听事件
         */
        button_register.addActionListener(e -> {
            CustomerDao customerDao = new CustomerDaoImpl();
            List<Customer> list = customerDao.getAdminPassword();   // 获取key，key为admin的密码

            // 获取数据
            String phone = jTextField_phone.getText();
            String keyDao = list.get(0).getPassword();
            String key = jTextField_key.getText();
            String password = String.valueOf(jPasswordField1.getPassword());
            String verify = String.valueOf(jPasswordField2.getPassword());

            // 直接对比密码和key是否正确
            if (!password.equals(verify)) {
                SwingUtil.showMessage(jFrame, "确认密码错误！");
                jLabel_newPassword.setText("");
                jLabel_verifyPassword.setText("");
            } else if (!key.equals(keyDao)) {
                SwingUtil.showMessage(jFrame, "密钥错误！");
            } else {
                int n = customerDao.changePassword(phone, password);
                if (n == 1) {
                    SwingUtil.showMessage(jFrame, "修改成功，请妥善保存");
                    jFrame.dispose();
                }
            }

        });
    }
}
