package com.IsaacM;

import javax.swing.*;

public class MainFrame extends JFrame{

        MainFrame(){
            this.add(new Panel());
            this.setTitle("Snake Game");
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setResizable(false);
            this.pack();
            this.setVisible(true);
            this.setLocationRelativeTo(null);
        }
}
