package gameUI;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class RegisterJFrame extends JFrame implements MouseListener {
    //登录按钮
    JButton resetBtn = new JButton();
    //注册按钮
    JButton registerBtn = new JButton();
    //记录登录窗口地址
    LoginJFrame login=null;

    //二次密码输入框
    JPasswordField password2=new JPasswordField();

    //用户名输入框
    JTextField username=new JTextField();

    //首次密码输入框
    JPasswordField password1=new JPasswordField();

    //记录账户名单
    private ArrayList<Account> accountList=null;

    public RegisterJFrame(ArrayList<Account> accountList) {
        //记录账户名单
        this.accountList=accountList;

        //设置窗口
        initWindow();

        //设置输入界面
        initUI();

        //设置按钮
        initButton();

        //背景图
        JLabel bg=new JLabel(new ImageIcon("JgsawPuzzleGame\\image\\register\\background.png"));
        bg.setBounds(0,0,470,390);
        this.getContentPane().add(bg);

        //可视化窗口
        //this.setVisible(true);

        System.out.println("Register has been initialized");
    }

    private void initUI() {
        //用户名
        JLabel usernameText=new JLabel(new ImageIcon("JgsawPuzzleGame\\image\\register\\注册用户名.png"));
        usernameText.setBounds(62,137,110,17);
        this.getContentPane().add(usernameText);

        //用户名输入框
        username.setBounds(175,134,200,30);
        this.getContentPane().add(username);

        //密码
        JLabel passwordText1=new JLabel(new ImageIcon("JgsawPuzzleGame\\image\\register\\注册密码.png"));
        passwordText1.setBounds(73,197,100,16);
        this.getContentPane().add(passwordText1);

        //密码输入框
        password1.setBounds(175,195,200,30);
        this.getContentPane().add(password1);

        //确认密码
        JLabel passwordText2=new JLabel(new ImageIcon("JgsawPuzzleGame\\image\\register\\再次输入密码.png"));
        passwordText2.setBounds(60,262,100,16);
        this.getContentPane().add(passwordText2);

        //确认密码输入框
        password2.setBounds(175,256,200,30);
        this.getContentPane().add(password2);
    }

    private void initButton() {
        //绑定按钮鼠标监听
        resetBtn.addMouseListener(this);
        registerBtn.addMouseListener(this);

        //按钮大小
        resetBtn.setBounds(123, 310, 128, 47);
        registerBtn.setBounds(256, 310, 128, 47);

        //按钮图标
        resetBtn.setIcon(new ImageIcon("JgsawPuzzleGame\\image\\register\\重置按钮.png"));
        resetBtn.setBorderPainted(false);
        resetBtn.setContentAreaFilled(false);
        registerBtn.setIcon(new ImageIcon("JgsawPuzzleGame\\image\\register\\注册按钮.png"));
        registerBtn.setBorderPainted(false);
        registerBtn.setContentAreaFilled(false);

        this.getContentPane().add(resetBtn);
        this.getContentPane().add(registerBtn);
    }

    private void initWindow() {
        //设置窗口大小
        this.setSize(488, 430);

        //设置窗口居中
        this.setLocationRelativeTo(null);

        //设置标题
        this.setTitle("拼图游戏1.0注册");

        //关闭默认中放置
        this.setLayout(null);

        //窗口置顶
        this.setAlwaysOnTop(true);

        //设置关闭操作
        this.setDefaultCloseOperation(3);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Object source = e.getSource();
        if (source == resetBtn) {
            System.out.println("reset");
            reset();
        } else if (source == registerBtn) {
            System.out.println("register");
            String tmpUsername=username.getText();
            String tmpPassword1=password1.getText();
            String tmpPassword2=password2.getText();
            //检测三个输入框不为空
            if (!tmpUsername.equals("") && !tmpPassword1.equals("") && !tmpPassword2.equals("")) {
                //判断两次输入是否一致
                if(tmpPassword1.equals(tmpPassword2)) {
                    this.setVisible(false);
                    login.setVisible(true);
                    Account newAccount=new Account(tmpUsername,tmpPassword1);
                    accountList.add(newAccount);
                    System.out.println("register is opened");
                }else {
                    //弹框内容
                    JLabel word=new JLabel("两次输入密码不一致，请重新输入");
                    //创建弹框
                    JDialog inputError=new JDialog();
                    //设置弹框位置大小
                    inputError.setBounds(getWidth()/2,getHeight()/2,200,100);
                    //添加文字
                    inputError.getContentPane().add(word);
                    //标题
                    inputError.setTitle("error");
                    //置顶
                    inputError.setAlwaysOnTop(true);
                    //优先处理
                    inputError.setModal(true);
                    //设置关闭模式
                    inputError.setDefaultCloseOperation(1);
                    //显示弹框
                    inputError.setVisible(true);
                }
            }else {
                //弹框内容
                JLabel word=new JLabel("输入有误请重新输入");
                //创建弹框
                JDialog inputError=new JDialog();
                //设置弹框位置大小
                inputError.setBounds(getWidth()/2,getHeight()/2,200,100);
                //添加文字
                inputError.getContentPane().add(word);
                //标题
                inputError.setTitle("error");
                //置顶
                inputError.setAlwaysOnTop(true);
                //优先处理
                inputError.setModal(true);
                //设置关闭模式
                inputError.setDefaultCloseOperation(2);
                //显示弹框
                inputError.setVisible(true);
            }
            return;
        }
    }

    private void reset() {
        username.setText("");
        password1.setText("");
        password2.setText("");
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void getLogin(LoginJFrame loginJFrame) {
        login=loginJFrame;
    }
}
