package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class Bird extends JComponent {
    private Image image;
    private int angle;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.rotate(Math.toRadians(angle), getWidth()/2, getHeight()/2);
        g2d.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(image.getWidth(this), image.getHeight(this));
    }

    public void setAngle(int angle) {
        this.angle = angle;
        repaint();
    }

    public Bird() throws IOException {
        super();
        URL imageUrl = getClass().getResource("/Flappy Bird.png");
        this.image = ImageIO.read(imageUrl);
        this.setPreferredSize(new Dimension(64,64));
        this.angle = 0;
    }
}
