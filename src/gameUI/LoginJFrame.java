package gameUI;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

public class LoginJFrame extends JFrame implements MouseListener {
    //登录按钮
    private JButton loginBtn = new JButton();
    //注册按钮
    private JButton registerBtn = new JButton();
    //记录注册窗口地址
    private RegisterJFrame register = null;

    //创建账户名单
    private ArrayList<Account> accountList=null;
    //记录游戏窗口地址
    private GameJFrame game = null;

    //记录用户输入的账户
    private String tmpUsername;

    //保存用户输入的密码
    private String tmpPassword;

    //保存用户输入的验证码
    private String tmpCode;

    //保存验证码
    private String code;

    //用户名输入框
    JTextField usernameField = new JTextField();

    //密码输入框
    JPasswordField passwordField = new JPasswordField();

    //验证码输入框
    JTextField codeField = new JTextField();

    //验证码显示区域
    JLabel codeJLabel=new JLabel(code);

    //背景
    JLabel bg = new JLabel(new ImageIcon("JgsawPuzzleGame\\image\\login\\background.png"));

    public LoginJFrame(ArrayList<Account> accountList) {
        //记录账户
        this.accountList=accountList;

        //记下账户后再创建游戏界面
        game=new GameJFrame(accountList);

        //设置窗口
        initWindow();

        //设置界面
        initUI();

        //设置按钮
        initButton();

        //设置背景图
        bg.setBounds(0, 0, 470, 390);
        this.getContentPane().add(bg);

        //设置注册窗口
        register = new RegisterJFrame(accountList);
        register.getLogin(this);

        System.out.println("Login Window has been initialized");
    }

    private void initUI() {
        //用户名
        JLabel usernameText = new JLabel(new ImageIcon("JgsawPuzzleGame\\image\\login\\用户名.png"));
        usernameText.setBounds(96, 135, 47, 17);
        this.getContentPane().add(usernameText);

        //用户名输入框
        usernameField.setBounds(175, 134, 200, 30);
        this.getContentPane().add(usernameField);
        //usernameField.addMouseListener(this);

        //密码
        JLabel passwordText = new JLabel(new ImageIcon("JgsawPuzzleGame\\image\\login\\密码.png"));
        passwordText.setBounds(110, 195, 32, 16);
        this.getContentPane().add(passwordText);

        //密码输入框
        passwordField.setBounds(175, 195, 200, 30);
        this.getContentPane().add(passwordField);
        //passwordField.addMouseListener(this);

        //验证码
        JLabel codeText = new JLabel(new ImageIcon("JgsawPuzzleGame\\image\\login\\验证码.png"));
        codeText.setBounds(96, 256, 50, 30);
        this.getContentPane().add(codeText);

        //验证码输入框
        codeField.setBounds(175, 256, 100, 30);
        this.getContentPane().add(codeField);
        //codeField.addMouseListener(this);

        //验证码
        code=creatCode();
        codeJLabel=new JLabel(code);
        codeJLabel.setBounds(290, 256, 100, 30);
        this.getContentPane().add(codeJLabel);

    }

    private String creatCode() {
        /*
        * 验证码规则：
        * 1.共4位
        * 2.只包含字母和数字，其中只有一个数字
        * 3.顺序随机
        * 4.字母不要求大小写
        */

        char[] code=new char[4];

        char[] letters=new char[3];

        //记录数字的位置
        int numPosition;

        //记录字母添加的数量
        int count=0;

        Random rnd=new Random();

        //生成数字
        int num= rnd.nextInt(10);

        for (int i = 0; i < 3; i++) {
            if (rnd.nextBoolean()) {
                //生成小写字母
                letters[i] = (char) (rnd.nextInt(26) + 65);
            }else {
                //生成大写字母
                letters[i] = (char) (rnd.nextInt(26) + 97);
            }
        }

        numPosition= rnd.nextInt(4);

        for (int i = 0; i < code.length; i++) {
            if (i!=numPosition) {
                code[i] = letters[count];
                count++;
            }else {
                code[i]= (char) (num+48);
            }
        }
        String codeFinal = "";
        for (int i = 0; i < 4; i++) {
            codeFinal=codeFinal+code[i];
        }

        return codeFinal;
    }

    private void initButton() {
        //绑定按钮鼠标监听
        loginBtn.addMouseListener(this);
        registerBtn.addMouseListener(this);

        //按钮大小
        loginBtn.setBounds(123, 310, 128, 47);
        registerBtn.setBounds(256, 310, 128, 47);

        //按钮图标
        loginBtn.setIcon(new ImageIcon("JgsawPuzzleGame\\image\\login\\登录按钮.png"));
        loginBtn.setBorderPainted(false);
        loginBtn.setContentAreaFilled(false);
        registerBtn.setIcon(new ImageIcon("JgsawPuzzleGame\\image\\login\\注册按钮.png"));
        registerBtn.setBorderPainted(false);
        registerBtn.setContentAreaFilled(false);

        //显示按钮
        this.getContentPane().add(loginBtn);
        this.getContentPane().add(registerBtn);
    }

    private void initWindow() {
        //设置窗口大小
        this.setSize(488, 430);

        //设置窗口居中
        this.setLocationRelativeTo(null);

        //设置标题
        this.setTitle("拼图游戏1.0登录");

        //关闭默认中放置
        this.setLayout(null);

        //窗口置顶
        this.setAlwaysOnTop(true);

        //设置关闭操作
        this.setDefaultCloseOperation(3);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        boolean flag=false;
        Object source = e.getSource();
        if (source == loginBtn) {
            System.out.println("login");
            tmpUsername=usernameField.getText();
            tmpPassword=passwordField.getText();
            tmpCode=codeField.getText();
            Account checkAccount=new Account(tmpUsername,tmpPassword);
            //先检查验证码
            if (checkCode(tmpCode)) {
                //检查账户
                flag=checkAccount(checkAccount);
                if (flag) {
                    //重置界面
                    reset();
                    //登陆成功
                    this.setVisible(false);
                    game.setVisible(true);
                    System.out.println("gamewindow is opened");
                }else{
                    //重置界面
                    reset();
                    //弹框内容
                    JLabel word=new JLabel("用户名或密码有误，请重新输入");
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
                    System.out.println("wrong accountname or password");
                }
            }else {
                System.out.println("wrong code");
                //重置验证码
                resetCode();
            }
        } else if (source == registerBtn) {
            //重置界面
            reset();

            System.out.println("register");
            this.setVisible(false);
            register.setVisible(true);
            System.out.println("register is opened");
        }
    }

    private void resetCode() {
        //输入框置空
        codeField.setText("");
        //重新创建验证码
        code=creatCode();
        this.getContentPane().remove(codeJLabel);
        this.getContentPane().remove(bg);
        codeJLabel=new JLabel(code);
        codeJLabel.setBounds(290, 256, 100, 30);
        this.getContentPane().add(codeJLabel);
        this.getContentPane().add(bg);
        this.getContentPane().repaint();
    }

    private void reset() {
        //输入框置空
        usernameField.setText("");
        passwordField.setText("");
        codeField.setText("");
        //重新创建验证码
        code=creatCode();
        this.getContentPane().remove(codeJLabel);
        this.getContentPane().remove(bg);
        codeJLabel=new JLabel(code);
        codeJLabel.setBounds(290, 256, 100, 30);
        this.getContentPane().add(codeJLabel);
        this.getContentPane().add(bg);
        this.getContentPane().repaint();
    }

    private boolean checkCode(String tmpCode) {
        if (code.equals(this.tmpCode)){
            return true;
        }
        //弹框内容
        JLabel word=new JLabel("验证码错误，请重新输入（区分大小写）");
        //创建弹框
        JDialog inputError=new JDialog();
        //设置弹框位置大小
        inputError.setBounds(getWidth()/2,getHeight()/2,250,100);
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
        return false;
    }

    private boolean checkAccount(Account checkAccount) {
        for (Account account : accountList) {
            if (account.equals(checkAccount)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        /*
        * 尝试按住按钮鼠标不松切换按钮图标失败代码
        */
        /*Object source = e.getSource();
        if (source == loginBtn) {
            loginBtn.setIcon(new ImageIcon("JgsawPuzzleGame\\image\\login\\登录按钮按下.png"));
        }
        if (source == registerBtn) {
            registerBtn.setIcon(new ImageIcon("JgsawPuzzleGame\\image\\login\\注册按钮按下.png"));
        }*/
        //this.getContentPane().repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        Object source = e.getSource();
        if (source == loginBtn) {

        } else if (source == registerBtn) {

        }
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
