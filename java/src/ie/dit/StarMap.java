package ie.dit;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.data.Table;
import processing.data.TableRow;

public class StarMap extends PApplet
{

    int selected1 = -1;
    int selected2 = -1;

    public void settings()
    {
        size(800, 800);
    }

    public void setup()
    {
        loadData();
        printStars();
        border = width * 0.05f;
    }

    public void loadData()
    {
        Table table = loadTable("HabHYG15ly.csv", "header");
        
        /*
        for(int i = 0 ; i < table.getRowCount() ; i ++)
        {
            TableRow row = table.getRow(i);
            System.out.println(row.getString("Display Name"));
            System.out.println(row.getString("Hab?"));            
        }
        */

        for(TableRow row:table.rows())
        {
            Star star = new Star(row);
            //new star
            stars.add(star);     
        }
    }

    public void printStars()
    {
        // EVERY ELEMENT IS ITERATED OVER
        for(Star star:stars)
        {
            System.out.println(star);
        }
    }

    float border = 50;

    public void drawGrid()
    {
        textAlign(CENTER, CENTER);
        for(int i = -5; i <= 5; i ++)
        {
            float x = map(i, -5, 5, border, width - border );
            stroke(0,0,255);
            line( x, border, x, height - border);
            fill(255);
            text(i, x, border /2 );
            fill(255);
            line(border, x, width - border, x);
            text(i, border /2 , x);

            
        }
    }


    public void drawStars()
    {
        textAlign(LEFT, CENTER);

        for(Star s:stars)
        {
            float x = map(s.getxG(), -5, 5, border, width - border);
            float y = map(s.getyG(), -5, 5, border, height - border);

            stroke(255, 255, 0);
            noFill();
            ellipse(x,y, s.getAbsMag(),s.getAbsMag());

            stroke(0, 255, 255);
            line(x, y -5, x, y + 5);
            line(x -5, y, x +5, y);
            fill(255);

            text(s.getDisplayName(), x + 20, y);
        }
    }

    // MINUS 1 TO SAY NOTHING HAS BEEN SELECTED
    int selected1 = -1;
    int selected2 = -1;

    public void mouseClicked()
    {
        for(int i = 0 ; i < stars.size() ; i ++)
        {
            Star s = stars.get(i);
            float x = map(s.getxG(), -5, 5, border, width - border);
            float y = map(s.getyG(), -5, 5, border, height - border);
            
            if (dist(mouseX, mouseY, x, y) < s.getAbsMag() / 2)
            {
            if (selected1 == -1) //nothing selected
            {
                selected1 = i;
                break;
            }
            else if (selected2 == -1)
            {
                selected2 = i;
            }
            else
            {
                selected1 = i;
                selected2 = -1;
            }           
            }
        }  
    }


    public void draw(){
        background(0);
        drawGrid();
        drawStars();

        // If I have selected one of the stars
  if (selected1 != -1 && selected2 == -1)
  {
    Star star1 = stars.get(selected1);
    stroke(255, 255, 0);
    line(star1.getxG(), star1.getyG(), mouseX, mouseY);
  }
  else if (selected1 != -1 && selected2 != -1)
  {
    Star star1 = stars.get(selected1);
    Star star2 = stars.get(selected2);
    stroke(255, 255, 0);
    line(star1.getxG(), star1.getyG(), star2.getxG(), star2.getyG());    
    fill(255);
    float dist = dist(star1.pos.x, star1.pos.y, star1.pos.z, star2.pos.x, star2.pos.y, star2.pos.z);
    text("Distance from " + star1.displayName + " to " + star2.displayName + " is " + dist + " parsecs", border, height - 25);
  }
  
  // If I have selected both of the stars
  
        


    }







    //ANGLE BRACKETS HOLD GENERIC TYPE
    private ArrayList<Star> stars = new ArrayList<Star>();

}