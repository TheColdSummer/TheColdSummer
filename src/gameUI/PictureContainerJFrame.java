package gameUI;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.util.Random;

public class PictureContainerJFrame extends JFrame {
    final int GIRL=0;
    final int ANIMAL=1;
    final int SPORT=2;
    GameJFrame gameJFrame = null;
    int[] pictureIndex = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
    int[][] data = new int[4][4];
    int x = 0;
    int y = 0;
    String path = "JgsawPuzzleGame\\image\\girl\\girl1\\";
    Boolean win = false;
    int stepCount = 0;

    //空参构造默认加载
    public PictureContainerJFrame(GameJFrame gameJFrame) {
        //保存链接的游戏本体
        this.gameJFrame = gameJFrame;
        //加载并打乱图片
        messPictures();
        loadPictures(this.gameJFrame);
    }

    //打乱图片
    public void messPictures() {
        Random rnd = new Random();
        //打乱图片下标
        for (int i = 0; i < pictureIndex.length; i++) {
            int rndIndext = rnd.nextInt(pictureIndex.length);
            int tmp = pictureIndex[rndIndext];
            pictureIndex[rndIndext] = pictureIndex[i];
            pictureIndex[i] = tmp;
        }
        for (int i = 0; i < pictureIndex.length; i++) {
            data[i / 4][i % 4] = pictureIndex[i];
        }
    }

    //加载图片
    public void loadPictures(GameJFrame gameJFrame) {
        //清空已有图片
        gameJFrame.getContentPane().removeAll();
        if (win) {
            //左上角实时显示步数
            JLabel step = new JLabel("当前步数：" + (stepCount) + " 状态：游戏胜利");
            step.setBounds(50, 20, 300, 20);
            gameJFrame.getContentPane().add(step);
            //展示胜利图标
            JLabel win = new JLabel(new ImageIcon("JgsawPuzzleGame\\image\\win.png"));
            win.setBounds(203, 283, 197, 73);
            gameJFrame.getContentPane().add(win);
        }else {
            //左上角实时显示步数
            JLabel step = new JLabel("当前步数：" + (stepCount) + " 状态：正在游戏");
            step.setBounds(50, 20, 300, 20);
            gameJFrame.getContentPane().add(step);
        }

        //生成拼图部分
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (data[i][j] == 1) {
                    //设置图片为空
                    ImageIcon image = null;
                    JLabel imageLabel = new JLabel(image);
                    //设置边框
                    imageLabel.setBorder(new BevelBorder(BevelBorder.LOWERED));
                    //调整大小
                    imageLabel.setBounds(83 + j * 105, 134 + i * 105, 105, 105);
                    gameJFrame.getContentPane().add(imageLabel);
                    //获取空白图片位置
                    x = i;
                    y = j;
                } else {
                    //从硬盘获取图片
                    ImageIcon image = new ImageIcon(path + data[i][j] + ".jpg");
                    JLabel imageLabel = new JLabel(image);
                    //设置边框
                    imageLabel.setBorder(new BevelBorder(BevelBorder.LOWERED));
                    //调整大小
                    imageLabel.setBounds(83 + j * 105, 134 + i * 105, 105, 105);
                    gameJFrame.getContentPane().add(imageLabel);
                }
            }
        }
        //设置背景
        JLabel bg = new JLabel(new ImageIcon("JgsawPuzzleGame\\image\\background.png"));
        bg.setBounds(40, 40, 508, 560);
        gameJFrame.getContentPane().add(bg);
        //刷新界面
        gameJFrame.getContentPane().repaint();
    }

    public void getKeyRleased(int keyCode) {
        //若胜利则不能继续移动
        if (win) {
            return;
        }

        if (keyCode == 67) {
            //W键一键通关且不算步数
            cheat();
            System.out.println("cheat");
        } else {
            //非作弊计步并打印步数

            //左37 上38 右39 下40
            if (keyCode == 37) {
                if (y != 3) {
                    System.out.println("move left");
                    System.out.println("now step:" + (++stepCount));
                    data[x][y] = data[x][y + 1];
                    data[x][y + 1] = 0;
                    y++;
                    loadPictures(this.gameJFrame);
                } else {
                    System.out.println("fail to move left");
                }
            } else if (keyCode == 38) {
                if (x != 3) {
                    System.out.println("move up");
                    System.out.println("now step:" + (++stepCount));
                    data[x][y] = data[x + 1][y];
                    data[x + 1][y] = 0;
                    x++;
                    loadPictures(this.gameJFrame);
                } else {
                    System.out.println("fail to move up");
                }
            } else if (keyCode == 39) {
                if (y != 0) {
                    System.out.println("move right");
                    System.out.println("now step:" + (++stepCount));
                    data[x][y] = data[x][y - 1];
                    data[x][y - 1] = 0;
                    y--;
                    loadPictures(this.gameJFrame);
                } else {
                    System.out.println("fail to move right");
                }
            } else if (keyCode == 40) {
                if (x != 0) {
                    System.out.println("move down");
                    System.out.println("now step:" + (++stepCount));
                    data[x][y] = data[x - 1][y];
                    data[x - 1][y] = 0;
                    x--;
                    loadPictures(this.gameJFrame);
                } else {
                    System.out.println("fail to move down");
                }
            }
        }

        if (keyCode == 65) {
            //结束完整展示继续游戏
            loadPictures(this.gameJFrame);
            System.out.println("game continues");
        }

        //按键执行完后判断是否胜利
        if (win = checkWin()) {
            System.out.println("game win, total step:" + stepCount);
            loadPictures(gameJFrame);
        }
    }

    private Boolean checkWin() {
        for (int i = 0; i < 16; i++) {
            if (data[i / 4][i % 4] != (i + 1)) {
                return false;
            }
        }
        return true;
    }

    private void cheat() {
        //将图片调整为正确顺序
        for (int i = 0; i < 16; i++) {
            data[i / 4][i % 4] = i + 1;
        }
        //刷新界面
        loadPictures(gameJFrame);
    }


    public void showCompleteImage() {
        //若胜利则不能查看
        if (win) {
            return;
        }
        //清除
        gameJFrame.getContentPane().removeAll();
        //左上角实时显示步数
        JLabel step = new JLabel("当前步数：" + (stepCount) + " 状态：正在查看完整拼图");
        step.setBounds(50, 20, 300, 20);
        gameJFrame.getContentPane().add(step);
        //展示完整图片
        JLabel completeImage = new JLabel(new ImageIcon(path + "all.jpg"));
        completeImage.setBounds(83, 134, 420, 420);
        //设置背景
        JLabel bg = new JLabel(new ImageIcon("JgsawPuzzleGame\\image\\background.png"));
        bg.setBounds(40, 40, 508, 560);
        gameJFrame.getContentPane().add(completeImage);
        gameJFrame.getContentPane().add(bg);
        gameJFrame.getContentPane().repaint();
    }

    public void resetStep() {
        stepCount = 0;
    }

    public void changeImage(int image) {
        Random rnd=new Random();
        if (image==GIRL){
            System.out.println("change image to girl");
            path="JgsawPuzzleGame\\image\\girl\\girl"+(1+rnd.nextInt(13)+"\\");
        }else if (image==ANIMAL){
            System.out.println("change image to animal");
            path="JgsawPuzzleGame\\image\\animal\\animal"+(1+rnd.nextInt(8)+"\\");
        }else if(image==SPORT){
            System.out.println("change image to sport");
            path="JgsawPuzzleGame\\image\\sport\\sport"+(1+rnd.nextInt(10)+"\\");
        }
        resetStep();
        win=false;
        messPictures();
        loadPictures(gameJFrame);
    }

    public void setWin(boolean b) {
        win=b;
    }
}
