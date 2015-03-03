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
public class Ant {
    
    int x, y, id;
    Color color;
    boolean env[][];            // represents the local environment
    int[] cLoc;                 // holds the location of an item in the local environment
    
    boolean hasItem = false;    // indicates if ant is carrying an item
    Item item;
    
    int cdCurr = 0;             // current item density in local environment
    int cdHist = 0;             // historic item density experienced by ant
    int mem = 100;               // counter for memory
    
    int memoryLength = 20;
    Color startColor = Color.BLUE;
    Color carryColor = Color.GREEN;
    Color dropColor = Color.BLUE;
    
    public Ant(int i)
    {
        id = i;
        env = new boolean[2][2];
        cLoc = new int[2];
        color = startColor;
        cdHist = 0;
    }
    
    /*
    Public method which causes the ant to go through its behaviour routine
    */
    public void behave(Space s)
    {
        senseEnvironment(s);
        
        senseItems(s);
        
        updateItemDensity();
        
        depositItem(s);
        
        pickUpItem(s);
        
        move();
    
    }
    
    /* 
    Update internal representation of immediate environment
    */
    private void senseEnvironment(Space s)
    {
        env = s.getAdjacentCells(x, y);
    }
    
    /*
    Counts the number of corpses in the local environment and remembers the location of one of them
    */
    private void senseItems(Space s)
    {
        cdCurr = 0;
        
        for (int dx = 0; dx < 3; dx++){
            for(int dy = 0; dy < 3; dy++){
                if ( ( env[dx][dy] ) && (s.checkForItem( (x+dx-1) , (y+dy-1)) ) ) {
                    cdCurr++;
                    cLoc[0] = x+dx-1;
                    cLoc[1] = y+dy-1;
                }
            }
        }
    }
    
    /* Update internal memory of item density */
    private void updateItemDensity()
    {   
        if (cdCurr > cdHist) {
            cdHist = cdCurr;
            mem = memoryLength;
        } else {
            mem--;
            if (mem == 0) {
                cdHist = 0;
                mem = memoryLength;
            }
        }
    }
    
    /*
    Pick up a item if there is one in the immediate environment
    */
    private void pickUpItem(Space s)
    {
        if ( (!hasItem) && (cdCurr > 0) && (cdCurr > cdHist) ){
            
            item = s.removeItem(cLoc[0],cLoc[1]);
            
            hasItem = true;
            color = carryColor;
            
            /*
            System.out.println("ANT " + Integer.toString(id)
                    + " picked up Item " + Integer.toString(item.id)
                    + " at x=" + Integer.toString(x) + ",y="+ Integer.toString(y)
                    + " cdHist=" + Integer.toString(cdHist));
            */
        }
    }
    
    /*
    Deposit a item if there is sufficient item density in the local environment
    */
    private void depositItem(Space s)
    {
        if ( (hasItem) && (cdCurr > cdHist) ){
            s.addItem(x, y, item.id);
            hasItem = false;
            color = dropColor;
           
            /*
            System.out.println("ANT " + Integer.toString(id)
                    + " deposited Item " + Integer.toString(item.id)
                    + " at x=" + Integer.toString(x) + ",y="+ Integer.toString(y)
                    + " cdCurr=" + Integer.toString(cdCurr)
                    + " cdHist=" + Integer.toString(cdHist));
            */
            cdHist = 9;
            
        }
    }
    
    /*
    Find empty cell to move to in immediate environment
    */
    private void move()
    {
        // search for an empty cell at random       
        boolean tryLoc = false;
        int rx = 0, ry = 0, tries = 0;
        
        while ((tryLoc == false) || (tries < 20)) {
            
            rx = randInt(0,2);
            ry = randInt(0,2);
            
            if (tryLoc = env[rx][ry]) {
                x = x + (rx-1);
                y = y + (ry-1);
                break;   
            }
            tries++;
        }
        
        
    }
 
    
}
