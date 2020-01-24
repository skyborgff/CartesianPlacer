package com.indusstock.cartesian_placer;

import java.awt.*;

public class ColoredRectangle
{
    private Color background;
    private Color edge;
    private Color selectedEdge;
    private Color unselectedEdge;
    private Rectangle rectangle;
    private boolean selected = false;

    public ColoredRectangle(Rectangle rectangle)
    {
        this.background = Color.yellow;
        this.edge = Color.white;
        this.rectangle = rectangle;
        this.selectedEdge =  Color.black;
        this.unselectedEdge =  Color.white;
    }

    public Color getBackground()
    {
        return background;
    }

    public void setBackground(Color color)
    {
        this.background = color;
    }

    public Color getEdge()
    {
        return edge;
    }

    public void setEdge(Color color)
    {
        this.edge = color;
    }

    public Color getSelectedEdge()
    {
        return selectedEdge;
    }

    public void setSelectedEdge(Color color)
    {
        this.selectedEdge = color;
    }

    public Color getUnelectedEdge()
    {
        return unselectedEdge;
    }

    public void setUnelectedEdge(Color color)
    {
        this.unselectedEdge = color;
    }

    public void setPosition(int x, int y)
    {
        this.getRectangle().setLocation(x,y);
    }

    public void move(int x, int y)
    {
        int startx = ((int) this.getRectangle().getX());
        int starty = ((int) this.getRectangle().getY());

        this.getRectangle().setLocation(startx + x,starty + y);
    }

    public void selected(boolean select){
        this.selected = select;
        if (select){
            this.setEdge(selectedEdge);
        }
        else{
            this.setEdge(unselectedEdge);
        }
    }

    public Rectangle getRectangle()
    {
        return rectangle;
    }

    public void rotate(){
        int width = this.getRectangle().width;
        int height = this.getRectangle().height;
        this.getRectangle().setSize(height, width);
    }

}