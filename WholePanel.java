package Application;

//  Created by Clayton Paplaczyk
//  Description: WholePanel creates GUI while organizing it for the user, it also
//	implements the listeners for the user choices of placement for the circle and
//	the circles color with listeners classes.

import java.awt.*;
import javax.swing.*;
import java.awt.event.*; // to use listener interfaces
import java.util.ArrayList;

public class WholePanel extends JPanel
{
   private ArrayList circleList, tempList;
   private Color currentColor;
   private Canvas canvas;
   private JPanel topPanel;
   private JButton undo, erase;
   private JComboBox jcb;
   private String [] colorList = {"black", "red", "blue", "green", "orange"};
   public int x1, x2, x3;
   public int y1, y2, y3;
   public boolean eraseFlag = false;
   public boolean undoFlag = false;
   
   public WholePanel()
    {
      //we use black to draw a circle
      currentColor = Color.black;
     
      //Creates topPanel to organize buttons
      topPanel = new JPanel();
      
      //Creates an arraylist to store all circles drawn in circleList
      circleList = new ArrayList<String>();
      tempList = new ArrayList<String>();  //tempList is copy of circleList
      
      //creates user buttons and JComboBox for user
      undo = new JButton("Undo");
      erase = new JButton("Erase");
      jcb = new JComboBox(colorList);
      
      //creates button listener for undo and erase
      undo.addActionListener(new ButtonListener());
      erase.addActionListener(new ButtonListener());
      
      //adds buttons and JComboBox to topPanel
      topPanel.add(undo);
      topPanel.add(erase);
      topPanel.add(jcb);
      
      //creates canvas to draw circles in
      canvas = new Canvas();
      
      //instantiates mouse listeners to be able to get x and y from mouse interface
      canvas.addMouseListener(new PointListener());
      canvas.addMouseMotionListener(new PointListener());

      //instantiates color listener to get color from jcb
      jcb.addActionListener(new ColorListener());
      
      //Creates SplitPane to organize GUI
      JSplitPane sp = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topPanel, canvas);

      setLayout(new BorderLayout());
      add(sp);

     }


    private class Canvas extends JPanel
     {
      //This method needs to be defined to draw in this panel
      public void paintComponent(Graphics page)
       {
         super.paintComponent(page);
        
         setBackground(Color.white); //sets canvas background color to white
         
         if(undoFlag){
        	 int expRadi = (int) Math.sqrt(Math.pow((double)(x2-x1),2)+Math.pow((double)(y2-y1),2));
        	 int expDiam = expRadi * 2;
        	 
        	 page.setColor(currentColor);  
        	 page.drawOval(x1 - 1, y1 - 1, 2, 2);  //Shows user center point of circle to be drawn
        	 page.drawOval(x1 - expRadi, y1 - expRadi, expDiam, expDiam);  //shows user outline of circle to be drawn
        	 //undoFlag = false; 
         }
        	 undoFlag = true;
         
        	 for(int i=0; i < circleList.size(); i++){  //paints the circle stored in circleList
            	 ((Circle)circleList.get(i)).draw(page); 
        	 }
       }
      } //end of Canvas class
    
    private class ButtonListener implements ActionListener
    {
       //implement the abstract method inside ActionListener
       public void actionPerformed (ActionEvent event)
       {
          if(event.getSource() == undo){ //if undo is clicked
        	  undoFlag = false;
        	  if(eraseFlag){  //if undo is clicked
        		  circleList.addAll(tempList);
        		  repaint();
        	  }
        	  else{
        		  circleList.remove(circleList.size()-1);  //removes the last circle drawn
        		  repaint();
        	  }
          }
          if(event.getSource() == erase){ //if erase is clicked
        	  tempList.addAll(circleList); //creates copy of circleList so you can undo erase
        	  circleList.clear();  //removes all of the circles drawn
    		  eraseFlag = true;
    		  undoFlag = false;
        	  repaint();
          }		  
       }
   }
    
    private class ColorListener implements ActionListener
    {
    	public void actionPerformed(ActionEvent event)
    	{
    		//Selecting the color from jcb
    		if(jcb.getSelectedIndex() == 0) 
    			currentColor = Color.black;
    			else if(jcb.getSelectedIndex() == 1)
    			currentColor = Color.red;
    			else if(jcb.getSelectedIndex() == 2)
    			currentColor = Color.blue;
    			else if(jcb.getSelectedIndex() == 3)
    			currentColor = Color.green;
    			else
    			currentColor = Color.orange;
       	}
    }

    private class PointListener implements MouseListener, MouseMotionListener
     {
                 public void mousePressed(MouseEvent event)
                  {
                	 x1 = event.getPoint().x;
                	 y1 = event.getPoint().y;
                  }
                 public void mouseReleased(MouseEvent event)
                  {
                	 x3 = event.getPoint().x;
                	 y3 = event.getPoint().y;
                	 repaint();
                	 
                	 int radius = (int) Math.sqrt(Math.pow((double)(x3-x1),2)+Math.pow((double)(y3-y1),2));
                	 int diameter = 2*radius; 
                	 
                	 //Creates an object for the new circle to be drawn called oneCircle
                	 Circle oneCircle = new Circle( x1 - radius, y1 - radius, diameter, currentColor);
                	 
                  	 circleList.add(oneCircle);  //Adds the object oneCircle to circleList.
                  }
                 public void mouseClicked(MouseEvent event) {}
                 public void mouseEntered(MouseEvent event) {}
                 public void mouseExited(MouseEvent event) {}
                 public void mouseDragged(MouseEvent event)
                   {
                	 x2 = event.getPoint().x;
                	 y2 = event.getPoint().y;
                	 repaint();
                   }
                 public void mouseMoved(MouseEvent event) {}

     } //end of PointListener

} //end of Whole Panel Class