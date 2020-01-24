package com.indusstock.cartesian_placer;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 *  Note: Normally the ButtonPanel and DrawingArea would not be static classes.
 *  This was done for the convenience of posting the code in one class and to
 *  highlight the differences between the two approaches. All the differences
 *  are found in the DrawingArea class.
 *  http://www.camick.com/java/source/DrawOnComponent.java
 */
public class DrawOnComponent
{
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI()
    {
        DrawingArea drawingArea = new DrawingArea();
        ButtonPanel buttonPanel = new ButtonPanel( drawingArea );

        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Draw On Component");
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.getContentPane().add(drawingArea);
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        frame.setSize(600, 600);
        frame.setLocationRelativeTo( null );
        frame.setVisible(true);
    }

    static class ButtonPanel extends JPanel implements ActionListener
    {
        private DrawingArea drawingArea;

        public ButtonPanel(DrawingArea drawingArea)
        {
            this.drawingArea = drawingArea;

            add( createButton("Clear Drawing", null) );
        }

        private JButton createButton(String text, Color background)
        {
            JButton button = new JButton( text );
            button.setBackground( background );
            button.addActionListener( this );

            return button;
        }

        public void actionPerformed(ActionEvent e)
        {
            JButton button = (JButton)e.getSource();

            if ("Clear Drawing".equals(e.getActionCommand()))
                drawingArea.clear();
            else
                drawingArea.setForeground( button.getBackground() );
        }
    }

    static class DrawingArea extends JPanel
    {
        private final static int AREA_SIZE = 600;
        private coloredRectangles coloredRectangles = new coloredRectangles();
        private Rectangle shape;

        public DrawingArea()
        {
            setBackground(Color.WHITE);

            MyMouseListener ml = new MyMouseListener();
            addMouseListener(ml);
            addMouseMotionListener(ml);
        }

        @Override
        public Dimension getPreferredSize()
        {
            return isPreferredSizeSet() ?
                    super.getPreferredSize() : new Dimension(AREA_SIZE, AREA_SIZE);
        }

        @Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);

            //  Custom code to paint all the Rectangles from the List

            Color foreground = g.getColor();

            g.setColor( Color.BLACK );
            g.drawString("Add a rectangle by doing mouse press, drag and release!", 40, 15);

            for (ColoredRectangle cr : coloredRectangles)
            {
                g.setColor( cr.getBackground() );
                Rectangle r = cr.getRectangle();
                g.fillRect(r.x, r.y, r.width, r.height);
                g.setColor(cr.getEdge());
                g.drawRect(r.x, r.y, r.width, r.height);
                g.setColor(Color.black);
                g.drawOval(((int) r.getCenterX()), ((int) r.getCenterY()), 2,2 );

            }

            //  Paint the Rectangle as the mouse is being dragged

            if (shape != null)
            {
                Graphics2D g2d = (Graphics2D)g;
                g2d.setColor( foreground );
                g2d.draw( shape );
            }
        }

        public void addRectangle(Rectangle rectangle)
        {
            //  Add the Rectangle to the List so it can be repainted

            ColoredRectangle cr = new ColoredRectangle(rectangle);
            coloredRectangles.add( cr );
            repaint();
        }

        public void clear()
        {
            coloredRectangles.clear();
            repaint();
        }

        class MyMouseListener extends MouseInputAdapter
        {
            private Point startPoint;
            public int shortindex = 0;
            public int xDelta;
            public int yDelta;
            public int selectedrectangle;
            public boolean dragging;

            public void mouseClicked(MouseEvent e)
            {   // Right mouse adds rectangle
                // TODO: check if it will not overlap another one before placing
                if (SwingUtilities.isRightMouseButton(e)){
                    Point point = e.getPoint();
                    shape = new Rectangle();
                    // TODO: set other place for the sizes
                    shape.setLocation(((int) point.x) - 40, ((int) point.y)-60);
                    shape.setSize(80, 120);
                    if (!coloredRectangles.intersects(shape)){
                        addRectangle(shape);
                    }
                    else if (coloredRectangles.isSelected(point)){
                        coloredRectangles.get(coloredRectangles.closestInt(point)).rotate();
                    }
                    shape = null;
                }
                repaint();
                }
            public void mousePressed(MouseEvent e)
            {
                if (SwingUtilities.isLeftMouseButton(e)){
                    startPoint = e.getPoint();
                    selectedrectangle = coloredRectangles.closestInt(startPoint);
                    int startRectx = coloredRectangles.get(selectedrectangle).getRectangle().x;
                    int startRecty = coloredRectangles.get(selectedrectangle).getRectangle().y;
                    xDelta = startRectx - startPoint.x;
                    yDelta = startRecty - startPoint.y;
                }
                repaint();
            }

            public void mouseMoved(MouseEvent e)
            {
                Point currentPoint = e.getPoint();
                if (coloredRectangles.isSelected(currentPoint)){
                    coloredRectangles.selectClosest(currentPoint);
                }
                dragging = false;
                repaint();
            }


                public void mouseDragged(MouseEvent e)
                {
                    Point currentPoint = e.getPoint();
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        if (coloredRectangles.isSelected(currentPoint)) {
                            dragging = true;
                        }
                        if (dragging) {
                            coloredRectangles.get(selectedrectangle).setPosition(currentPoint.x + xDelta, currentPoint.y + yDelta);
                        }
                        repaint();
                    }

                }

//            public void mouseReleased(MouseEvent e)
//            {
//                if (shape.width != 0 || shape.height != 0)
//                {
//                    addRectangle(shape, e.getComponent().getForeground());
//                }
//
//                shape = null;
//            }
            }
    }
}