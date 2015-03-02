/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.robkinsey.abm;

import java.awt.Color;
import static org.robkinsey.abm.Utilities.randInt;

/**
 *
 * @author robert
 */
public class Space {
    
    Item[] items;
    Ant[] ants;
    Item[][] cMap;
    Ant[][] aMap;
    int xmax;
    int ymax;
    
    public Space( int x )
    {
        xmax = x-1;
        ymax = x-1;  
        cMap = new Item[x][x];
        aMap = new Ant[x][x];
    }
    
    public void initializeItems (int n)
    {
        //Refactor method to prevent use cMap instead of items. Or maybe it is the same thing?
        items = new Item[n];
        
        for (int i=0; i<n; i++)
        {
            items[i] = new Item();
            items[i].x = randInt(0, xmax);
            items[i].y = randInt(0, ymax);
            items[i].id = i;
            items[i].color = Color.GRAY;
            
            cMap[items[i].x][items[i].y] = items[i];
            
            //System.out.println("Item id: " + Integer.toString(items[i].id) + " x=" + Integer.toString(items[i].x) + " y=" + Integer.toString(items[i].y));
            //System.out.println("cMap   id: " + Integer.toString(cMap[items[i].x][items[i].y].id));
        } 
    }
    
    public void initializeAnts (int n) {
        
         // Create the ants and randomly add across environment
        ants = new Ant[n];
        
        for (int i = 0; i<n; i++) {
            ants[i] = new Ant(i);
            ants[i].x = randInt(0,xmax);
            ants[i].y = randInt(0,ymax);
        }
        
    }
    
    public void runAnts() {
        // Each ant does its thing
        for (Ant a : ants){
            a.behave(this);   
        }
    }
    
    public void addItem( int x, int y, int id )
    {
        items[id].x = x;
        items[id].y = y;
        items[id].color = Color.RED;
        cMap[x][y] = items[id];
    }

    public Item removeItem( int x, int y )
    {

        Item c = cMap[x][y];
        c.x = -1;
        c.y = -1;
        /*
        System.out.println("SPACE: Removed item " + Integer.toString(c.id)
                + " at x="+ Integer.toString(x) + ",y=" + Integer.toString(y));
        */
        cMap[x][y] = null;
        return c;
    }
        
    public boolean checkForItem( int x, int y )
    {
        boolean lret;
        lret = cMap[x][y] != null;
        return lret;
    }
    
    public boolean[][] getAdjacentCells(int x, int y)
    {
        boolean[][] c = { {true,true,true},
                          {true,false,true},
                          {true,true,true} };
        if (x == 0) {
            c[0][0] = false;
            c[0][1] = false;
            c[0][2] = false;
        } else if (x == xmax) {
            c[2][0] = false;
            c[2][1] = false;
            c[2][2] = false;
        }

        if (y == 0) {
            c[0][0] = false;
            c[1][0] = false;
            c[2][0] = false;
        } else if ( y == ymax ) {
            c[0][2] = false;
            c[1][2] = false;
            c[2][2] = false;
        }
        
        return c;
    }
    
    public boolean[][] getAdjacentItems( int x, int y )
    {
        boolean[][] c = getAdjacentCells(x, y);
        
        for (int dx = -1; dx < 2; dx++){
            for(int dy = -1; dy < 2; dy++){
                if ( c[dx+1][dy+1] ) {
                    c[dx+1][dy+1] = checkForItem( x+dx, y+dy );
                }
            }
        }
        
        return c;
    }
    
}
