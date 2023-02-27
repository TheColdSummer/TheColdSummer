package gameUI;

import java.util.ArrayList;

public class JFrameController {
    public JFrameController(ArrayList<Account> accountList) {
        LoginJFrame loginJFrame = new LoginJFrame(accountList);
        loginJFrame.setVisible(true);
    }

}
