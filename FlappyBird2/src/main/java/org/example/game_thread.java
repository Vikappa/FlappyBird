package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class game_thread extends Thread{

    ////////////////////////////////////////////////////
    int FALL_LIMIT = 10;

    ////////////////////////////////////////////////////
    BackgroundPanel sfondo = new BackgroundPanel();
    Bird bird = new Bird();
    ArrayList<Colonne> listaOstacoli = new ArrayList<Colonne>();
    Boolean gameOver = false;
    int gravity = 0;
    int gravity(){
        if(gravity<-FALL_LIMIT){
            return -FALL_LIMIT;
        }
        return gravity;
    }
    int score = 0;
    JLabel screenScore;
    KeyListener jumpListener = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            jump();
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    };
    public game_thread() throws IOException {
        JFrame c = new JFrame("Flappy Bird");
        sfondo.setFocusable(true);
        sfondo.setLayout(null);
        c.add(sfondo);
        c.getContentPane().setPreferredSize(new Dimension (800,600));
        c.setContentPane(sfondo);
        c.setSize(800, 600);
        c.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        c.setResizable(false);
        c.setVisible(true);
        screenScore = new JLabel("Score:");
        Font font = new Font("Arial", Font.BOLD, 24);
        screenScore.setFont(font);
        screenScore.setVisible(true);
        sfondo.add(screenScore);
        screenScore.setPreferredSize(new Dimension(200, 20));
        screenScore.setBounds(0,0,200, 20);

        sfondo.add(bird);
        sfondo.addKeyListener(jumpListener);
        bird.setBounds(0,250,64,64);

        while(!gameOver){
            updateRender(bird);
            checkCollisioni(bird);
            if(bird.getY() > c.getHeight()){
                gameOver = true;
            }
        }
        if(gameOver){
            c.remove(sfondo);
            System.out.println("GameOver");
            sfondo = new BackgroundPanel();
            c.setContentPane(sfondo);
            //((Aggiungi codice per sequenza gameover))
            inizia_sequenza_gameover();
        }
    }
    private void inizia_sequenza_gameover() {
        screenScore.setVisible(false);
        JLabel gameOverLabel = new JLabel("Hai perso. Punteggio: " + score);
        gameOverLabel.setForeground(Color.RED);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 48));
        sfondo.setLayout(new BorderLayout());
        sfondo.add(gameOverLabel, BorderLayout.CENTER);
        gameOverLabel.setBounds(0, 0, sfondo.getWidth(), sfondo.getHeight()); // imposto la posizione e la dimensione del label
        gameOverLabel.setHorizontalAlignment(JLabel.CENTER); // centra il testo orizzontalmente
        gameOverLabel.setVerticalAlignment(JLabel.CENTER); // centra il testo verticalmente
        sfondo.revalidate();
    }
    private void updateRender(Bird bird) throws IOException {
        aspettaunOttavoDiSecondo();
        screenScore.setBounds(10,10,screenScore.getWidth(), screenScore.getHeight());
        screenScore.setText("Score:" + score);
        if(bird.getX() < 100 ) {
            bird.setLocation(bird.getX() + 1, bird.getY() + gravity());
        }
        else{
            bird.setLocation(bird.getX(), bird.getY() + gravity());

        }
            bird.setAngle(gravity);
            gravity++;
            score++;
            if(score%90 == 0){
                aggiungi_ostacolo();
            }
            for(int i = 0; i < listaOstacoli.size(); i++){
                listaOstacoli.get(i).setLocation(listaOstacoli.get(i).getX()-3, listaOstacoli.get(i).getY());
            }
    }
    private void checkCollisioni(Bird bird) {
        Rectangle rect1 = bird.getBounds();
        for(int i = 0; i < listaOstacoli.size(); i++) {
            Rectangle rect2 = listaOstacoli.get(i).getBounds();
            if (rect1.intersects(rect2)) {
                gameOver = true;

            }
        }
    }
    private void aspettaunOttavoDiSecondo(){
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void jump(){
        gravity= -10;
        System.out.print("Jump");
    };
    private void aggiungi_ostacolo() throws IOException {
        Random rand = new Random();
        Colonne newColonna = new Colonne(rand.nextInt(50));
        Colonne upCol = new Colonne(newColonna);
        sfondo.add(newColonna);
        sfondo.add(upCol);
        listaOstacoli.add(newColonna);
        listaOstacoli.add(upCol);
        newColonna.setBounds(800, 600-newColonna.getAltezza(), newColonna.getLarghezza(), newColonna.getAltezza());
        upCol.setBounds(800,0, upCol.getLarghezza(), upCol.getAltezza());
    }
}
