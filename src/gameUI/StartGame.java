package gameUI;

import java.util.ArrayList;

public class StartGame {
    public static void main(String[] args) {
        ArrayList<Account> accountList=new ArrayList<Account>();
        new JFrameController(accountList);
    }
}
