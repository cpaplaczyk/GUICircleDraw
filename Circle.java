package Application;

//  Created by Clayton Paplaczyk
//  Description: The Circle class sets up the parameters to draw a circle
//  when called to WholePanel. Uses the MouseListener to
//	determine the x and y for the middle of the circle, also
//	uses MouseListener to determine how far the mouse was 
//	dragged to determine the diameter of the circle.

import java.awt.*;
import java.awt.Graphics;

public class Circle{ 
    private int x;
    private int y;
    private int diameter;
    private Color color;
   
    public Circle(int x, int y, int diameter, Color color){
         this.diameter = diameter; 
         this.x = x;
         this.y = y;
         this.color = color;
    }
    
    public void draw(Graphics page){
       	page.setColor(color);
    	page.fillOval(x, y, diameter, diameter);
    	
    }
}

