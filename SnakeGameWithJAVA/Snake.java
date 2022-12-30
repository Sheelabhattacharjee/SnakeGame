import javax.swing.JFrame;
import javax.swing.JPanel;
//import javax.swing.plaf.FontUIResource;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
//import java.awt.Graphics;
//import java.awt.event.ActionEvent;
//import java.awt.event.KeyAdapter;
//import java.awt.event.KeyEvent;
public class Snake {
    public static void main(String[] args) {
        new GFrame();
        } 
}
class GFrame extends JFrame{
    
    GFrame() {
        this.add(new GPan());
        this.setTitle("Snake Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
    
}
class GPan extends JPanel implements ActionListener{


    /**
     *
     */

    // private static final long serialVersionUID = 1L;
    static final int sWidth = 600;
    static final int sHeight = 600;
    static final int unit = 25;
    static final int gameunit = (sWidth*sHeight)/unit;
    static final int DELAY = 100;

    final int x[] = new int[gameunit];
    final int y[]  =new int[gameunit];

    int bodyParts = 6;
    int fooodEaten;
    int foodX;
    int foodY;
    char direction = 'R';
    boolean running = false;

    Timer timer;
    Random random;

    GPan(){
        random  = new Random();
        this.setPreferredSize(new Dimension(sWidth, sHeight));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    public void startGame() {
        newFood();
        running = true;
        timer =new Timer(DELAY, this);
        timer.start();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g) {
        if (running) {
            for(int i=0; i<sHeight/unit;i++){
                g.drawLine(i*unit, 0, i*unit, sHeight);
                g.drawLine(0, i*unit, sWidth, i*unit);
            }
            g.setColor(Color.ORANGE);
            g.fillOval(foodX, foodY, unit, unit);
    
            for(int i = 0; i<bodyParts; i++){
                if(i == 0){
                    g.setColor(Color.GREEN);
                    g.fillRect(x[i], y[i], unit, unit);
                }else{
                    g.setColor(new Color(45, 100, 0));
                    g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                    g.fillRect(x[i], y[i], unit, unit);
                }
            }
            g.setColor(Color.WHITE);
            g.setFont(new Font("Ink FREE", Font.BOLD, 30));
            FontMetrics mt  =getFontMetrics(g.getFont());
            g.drawString("Score: "+fooodEaten, (sWidth-mt.stringWidth("Score: "+fooodEaten))/2, g.getFont().getSize());
        }
        else{
            gameOver(g);
        }
    }
    public void move() {
        for(int i = bodyParts; i>0; i--){
            x[i] = x[i-1]; 
            y[i] = y[i-1]; 
        }
        switch(direction){
            case 'U':
                y[0] = y[0] - unit;
                break;
            case 'D':
                y[0] = y[0] + unit;
                break;
            case 'L':
                x[0] = x[0] - unit;
                break;
            case 'R':
                x[0] = x[0] + unit;
                break;
        }
    }
    public void newFood(){
        foodX =random.nextInt((int)(sWidth/unit))*unit;
        foodY =random.nextInt((int)(sHeight/unit))*unit;
    }
    public void checkFood(){
        if((x[0]==foodX)&&(y[0]==foodY)){
            bodyParts++;
            fooodEaten++;
            newFood();
        }
    }
    public void checkCollision() {
        //if head collides with body
        for(int i= bodyParts; i>0; i--){
            if((x[0] == x[i])&&y[0]==y[i]){
                running = false;
            }
        }
        
        if(x[0]<0){ //left
            running = false;
        }
    
        if(x[0]>sWidth){ //right
            running = false;
        }
        
        if(y[0]<0){ //top
            running =false;
        }
        
        if(y[0]>sHeight){ //bottom
            running =false;
        }
        if (!running) {
            timer.stop();
        }

    }
    public void gameOver(Graphics g) {
        g.setColor(Color.WHITE);
            g.setFont(new Font("Ink FREE", Font.BOLD, 30));
            FontMetrics mt1  =getFontMetrics(g.getFont());
            g.drawString("Score: "+fooodEaten, (sWidth-mt1.stringWidth("Score: "+fooodEaten))/2, g.getFont().getSize());
        g.setColor(Color.WHITE);
        g.setFont(new Font("Ink FREE", Font.BOLD, 75));
        FontMetrics mt  =getFontMetrics(g.getFont());
        g.drawString("GAME OVER !!", (sWidth-mt.stringWidth("GAME OVER !!"))/2, sHeight/2);
    }

    //OVERRIDEN METHOD
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            checkFood();
            checkCollision();
        }
        repaint();

    }
    public class MyKeyAdapter extends KeyAdapter{
        //OVERRIDDEN METHOD
        
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if(direction!='R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction!='L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction!='D'){
                        direction = 'U';
                    }
                case KeyEvent.VK_DOWN:
                    if(direction!='U'){
                        direction = 'D';
                    }
            
                default:
                    break;
            }
        }
    }
}