package com.indusstock.cartesian_placer;

import javax.swing.*;
import java.awt.*;

class CustomPanel extends JPanel {

    private int squareX = 50;
    private int squareY = 50;
    private int squareW = 20;
    private int squareH = 20;

    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // do your superclass's painting routine first, and then paint on top of it.
        g.setColor(Color.RED);
        g.fillRect(squareX,squareY,squareW,squareH);
    }

}