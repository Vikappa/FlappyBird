package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class BackgroundPanel extends JPanel {
    private BufferedImage image;
    public BackgroundPanel() throws IOException {
        URL imageUrl = getClass().getResource("/Preview_143.png");
        this.image = ImageIO.read(imageUrl);
    }

    private int x = 0;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        g.drawImage(image, x, 0, null);
        g.drawImage(image, x + imageWidth, 0, null);
        if (x <= -imageWidth) {
            x = 0;
        }
        x -= 1; //velocità di movimento dello sfondo, puoi modificare questo valore
        if (x <= -imageWidth) {
            g.drawImage(image, x + 2 * imageWidth, 0, null);
            x += imageWidth;
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        repaint();
    }
}