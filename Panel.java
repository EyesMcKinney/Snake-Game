package com.IsaacM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Panel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/ UNIT_SIZE;
    static final int DELAY = 100;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodySize = 5;
    int pointsObtained;
    int pointX;
    int pointY;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean isRunning = false;
    Timer timer;
    Random random;

    Panel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        gameStart();
    }

    public void gameStart(){
        Apple();
        isRunning = true;
        timer = new Timer(DELAY,this);
        timer.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Draw(g);
    }

    public void Draw(Graphics g){
        if (isRunning){
            for(int i=0; i<SCREEN_HEIGHT/UNIT_SIZE; i++){
                g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
            }
            g.setColor(Color.GREEN);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for(int i = 0; i< bodySize; i++){
                if(i == 0){
                    g.setColor(Color.RED);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
                else {
                    g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }

            }
            g.setColor(Color.BLUE);
            g.setFont(new Font("Times New Roman", Font.BOLD, 35));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("SCORE: " + pointsObtained, (SCREEN_WIDTH - metrics.stringWidth("SCORE: " + pointsObtained))/2, (g.getFont().getSize()));
        }
        else {
            gameOverScreen(g);
        }
    }

    public void Apple(){
        appleX = random.nextInt(SCREEN_WIDTH/UNIT_SIZE)*UNIT_SIZE;
        appleY = random.nextInt(SCREEN_HEIGHT/UNIT_SIZE)*UNIT_SIZE;
    }

    public void Move(){
        for(int i = bodySize; i>0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch (direction) {
            case 'U' -> y[0] = y[0] - UNIT_SIZE;
            case 'D' -> y[0] = y[0] + UNIT_SIZE;
            case 'L' -> x[0] = x[0] - UNIT_SIZE;
            case 'R' -> x[0] = x[0] + UNIT_SIZE;
        }
    }

    public void pointsCheck(){
        if ((x[0] == appleX) && (y[0] ==  appleY)){
            bodySize++;
            pointsObtained++;
            Apple();
        }
    }

    public void collisionDetection(){
        // checks if head and body collide
        for(int i = bodySize; i>0; i--){
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                isRunning = false;
            }
        }
        // checks if head hits the border
        if (x[0] < 0) {
            isRunning = false;
        }
        if (x[0] > SCREEN_WIDTH) {
            isRunning = false;
        }
        if (y[0] <  0) {
            isRunning = false;
        }
        if (y[0] > SCREEN_HEIGHT) {
            isRunning = false;
        }
        if(!isRunning){
            timer.stop();
        }
    }

    public void gameOverScreen(Graphics g){
        g.setColor(Color.BLUE);
        g.setFont(new Font("Times New Roman", Font.BOLD, 80));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("GAME OVER", (SCREEN_WIDTH - metrics.stringWidth("GAME OVER"))/2, (SCREEN_HEIGHT / 2));
        System.out.println("Final Score: " + pointsObtained);
    }

    public void actionPerformed(ActionEvent e){

        if(isRunning){
            Move();
            collisionDetection();
            pointsCheck();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if (direction!= 'R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction!= 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction!= 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction!= 'U'){
                        direction = 'D';
                    }
                    break;
            }
        }

    }
}
