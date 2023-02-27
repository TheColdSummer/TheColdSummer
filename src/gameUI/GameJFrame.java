package gameUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class GameJFrame extends JFrame implements KeyListener, ActionListener {
    final int GIRL = 0;
    final int ANIMAL = 1;
    final int SPORT = 2;
    //记录图片容器的量
    PictureContainerJFrame pictureContainerJFrame = null;
    //记录login界面地址
    LoginJFrame loginJFrame = null;

    //创建菜单元素
    JMenuBar menuBar = new JMenuBar();

    JMenu functionMenu = new JMenu("菜单");
    JMenu aboutMenu = new JMenu("关于作者");
    JMenu changeImage = new JMenu("更改图片");

    JMenuItem replayItem = new JMenuItem("重新游戏");
    JMenuItem reloginItem = new JMenuItem("重新登录");
    JMenuItem exitItem = new JMenuItem("退出游戏");

    JMenuItem girlItem = new JMenuItem("美女");
    JMenuItem animalItem = new JMenuItem("动物");
    JMenuItem sportItem = new JMenuItem("运动");

    JMenuItem accountItem = new JMenuItem("作者信息");

    //记录账户名单
    ArrayList<Account> accountList = null;

    //空参构造初始化
    public GameJFrame(ArrayList<Account> accountList) {
        //记录账户名单
        this.accountList = accountList;

        //设置窗口
        initWindow();

        //设置菜单
        initMenu();

        //设置图片
        initImage();

        //可视化窗口
        //this.setVisible(true);

        System.out.println("Game has been initialized");
    }

    private void initImage() {
        PictureContainerJFrame pictureContainerJFrame = new PictureContainerJFrame(this);
        this.pictureContainerJFrame = pictureContainerJFrame;
    }


    private void initWindow() {
        //设置窗口大小
        this.setSize(603, 680);

        //设置窗口居中
        this.setLocationRelativeTo(null);

        //设置标题
        this.setTitle("拼图游戏1.0");

        //关闭默认中放置
        this.setLayout(null);

        //窗口置顶
        this.setAlwaysOnTop(true);

        //设置关闭操作
        this.setDefaultCloseOperation(3);

        //添加键盘监听
        this.addKeyListener(this);
    }


    private void initMenu() {
        //拼接元素
        menuBar.add(functionMenu);
        menuBar.add(aboutMenu);

        functionMenu.add(replayItem);
        functionMenu.add(reloginItem);
        functionMenu.add(exitItem);
        functionMenu.add(changeImage);

        changeImage.add(girlItem);
        changeImage.add(animalItem);
        changeImage.add(sportItem);

        aboutMenu.add(accountItem);

        //显示菜单
        this.setJMenuBar(menuBar);

        //添加鼠标监听
        replayItem.addActionListener(this);
        reloginItem.addActionListener(this);
        exitItem.addActionListener(this);
        girlItem.addActionListener(this);
        animalItem.addActionListener(this);
        sportItem.addActionListener(this);
        accountItem.addActionListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 65) {
            //让图片容器展示完整图片
            pictureContainerJFrame.showCompleteImage();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //将按键传入图片容器
        pictureContainerJFrame.getKeyRleased(e.getKeyCode());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //判断点击的功能
        Object obj = e.getSource();
        if (obj == replayItem) {
            //重新游戏
            System.out.println("replay game");
            pictureContainerJFrame.setWin(false);
            pictureContainerJFrame.messPictures();
            pictureContainerJFrame.resetStep();
            pictureContainerJFrame.loadPictures(this);
        } else if (obj == girlItem) {
            pictureContainerJFrame.changeImage(GIRL);
        } else if (obj == animalItem) {
            pictureContainerJFrame.changeImage(ANIMAL);
        } else if (obj == sportItem) {
            pictureContainerJFrame.changeImage(SPORT);
        } else if (obj == accountItem) {
            JDialog authorImformation = new JDialog();
            JLabel author = new JLabel(new ImageIcon("JgsawPuzzleGame\\image\\about.jpg"));
            //图片大小
            author.setBounds(0, 0, 375, 500);
            //添加图片
            authorImformation.getContentPane().add(author);
            //弹框大小
            authorImformation.setSize(375, 540);
            //设置弹框置顶
            authorImformation.setAlwaysOnTop(true);
            //设置弹框优先级最高
            authorImformation.setModal(true);
            //居中弹出
            authorImformation.setLocationRelativeTo(null);
            //显示弹框
            authorImformation.setVisible(true);
        } else if (obj == reloginItem) {
            //退出登录
            System.out.println("relogin");
            //关闭游戏界面
            this.setVisible(false);
            loginJFrame = new LoginJFrame(accountList);
            loginJFrame.setVisible(true);
        } else if (obj == exitItem) {
            //退出虚拟机
            System.out.println("exit game");
            System.exit(0);
        }
    }
}
