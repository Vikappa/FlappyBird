package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
public class Colonne extends JComponent {
    private static int upperCol;
    private static int loweCol;
    private BufferedImage concatenatedImage;
    private int altezza = 0;
    private int larghezza = 0;
    public int getAltezza(){
        return this.altezza;
    }
    public int getLarghezza(){
        return this.larghezza;
    }
    public Colonne(int numeroSegmenti) throws IOException {
        URL segmentUrl = getClass().getResource("/Mario_tube_segment_piccolo.png");
        URL edgeUrl = getClass().getResource("/Mario_tube_top_piccolo.png");
        BufferedImage segmentImg = ImageIO.read(segmentUrl);
        BufferedImage edgeImg = ImageIO.read(edgeUrl);
        concatenatedImage = new BufferedImage(edgeImg.getWidth(), edgeImg.getHeight() + (numeroSegmenti * segmentImg.getHeight()), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = concatenatedImage.createGraphics();

        g2d.drawImage(edgeImg, 0, 0, null); // disegna l'immagine del bordo in cima
        int y = edgeImg.getHeight();
        int x = edgeImg.getWidth();

        for (int i = 0; i < numeroSegmenti; i++) {
            g2d.drawImage(segmentImg, 0, y, null); // disegna il segmento
            y += segmentImg.getHeight(); // aggiorna la posizione y per il prossimo segmento
        }
        this.setPreferredSize(new Dimension(x,y));
        this.larghezza = x;
        this.altezza = y;
        g2d.dispose(); // libera le risorse
    }
    public Colonne(Colonne colonnaPrecedente) throws IOException {
        URL segmentUrl = getClass().getResource("/Mario_tube_segment_piccolo.png");
        URL edgeUrl = getClass().getResource("/Mario_tube_top_piccolo.png");
        BufferedImage segmentImg = ImageIO.read(segmentUrl);
        BufferedImage edgeImg = ImageIO.read(edgeUrl);

        int numeroSegmenti = Math.max((400 - colonnaPrecedente.getAltezza()) / segmentImg.getHeight(), 0);

        BufferedImage tmpImage = new BufferedImage(edgeImg.getWidth(), numeroSegmenti * segmentImg.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D tmpG2d = tmpImage.createGraphics();

        int y = 0;
        for (int i = 0; i < numeroSegmenti; i++) {
            tmpG2d.drawImage(segmentImg, 0, y, null); // disegna il segmento
            y += segmentImg.getHeight(); // aggiorna la posizione y per il prossimo segmento
        }
        tmpG2d.dispose(); // libera le risorse

        concatenatedImage = new BufferedImage(edgeImg.getWidth(), edgeImg.getHeight() + tmpImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = concatenatedImage.createGraphics();

        g2d.drawImage(tmpImage, 0, 0, null); // copia i segmenti nella nuova immagine
        g2d.drawImage(edgeImg, 0, tmpImage.getHeight(), null); // disegna l'immagine del bordo sopra i segmenti

        this.setPreferredSize(new Dimension(concatenatedImage.getWidth(), concatenatedImage.getHeight()));
        this.larghezza = concatenatedImage.getWidth();
        this.altezza = concatenatedImage.getHeight();
        g2d.dispose(); // libera le risorse
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.drawImage(concatenatedImage, 0, 0, this);
        g2d.dispose();
    }

    public void setLocation(int x, int y) {
        super.setLocation(x, y);
    }
}
