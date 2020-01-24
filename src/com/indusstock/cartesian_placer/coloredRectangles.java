package com.indusstock.cartesian_placer;

import org.w3c.dom.css.Rect;

import java.awt.*;
import java.util.ArrayList;

public class coloredRectangles extends ArrayList<ColoredRectangle>
{
    public boolean isSelected(Point point){
        for (ColoredRectangle rectangle : this) {
            if (rectangle.getRectangle().contains(point)){
                return true;
            }
        }
        return false;
    }

    public int closestInt(Point point){
        if (!isSelected(point)){
            // check that there we are hovering a box before
            throw new IllegalStateException();
        }
        int index = 0;
        int shortindex = 0;
        double distance = 99999;
        for (ColoredRectangle rectangle : this) {
            int x = Math.abs((int) rectangle.getRectangle().getCenterX() - point.x);
            int y = Math.abs((int) rectangle.getRectangle().getCenterY() - point.y);
            Double testdistance = Math.sqrt(x*x + y*y);
            if (testdistance < distance){
                distance = testdistance;
                shortindex = index;
            }
            index = index + 1;
        }
        return shortindex;
    }

    public void selectRectangle(int index){
        // Unselect all
        for (ColoredRectangle rectangle : this) {
            rectangle.selected(false);
        }
        // select only the correct one
        this.get(index).selected(true);
        // make it "pop"
        ColoredRectangle temp = this.get(index);
        this.remove(index);
        this.add(temp);
    }

    public void selectClosest(Point point){
        if (this.isSelected(point)){
            this.selectRectangle(this.closestInt(point));
        }
    }

    public boolean intersects(Rectangle testingrectangle){
        for (ColoredRectangle rectangle : this) {
            if (rectangle.getRectangle().intersects(testingrectangle)){
                return true;
            }
        }
        return false;
    }

}
