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
    int[] cLoc;                 // holds the location of a corpse in the local environment
    
    boolean hasCorpse = false;  // indicates if ant is carrying a corpse
    Corpse corpse;
    
    int cdCurr = 0;             // current corpse density in local environment
    int cdHist = 0;             // historic corpse density experienced by ant
    int mem = 20;              // counter for memory
    
    int memoryLength = 100;
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
        getPhysicalEnvironment(s);
        senseCorpses(s);
        updateCorpseDensity();
        
        if (hasCorpse) {
            depositCorpse(s);
        } else if (cdCurr > 0) {
            pickUpCorpse(s);
        } else {
            // There is no corpse to deposit or pickup!
        }
       
        move();
    
    }
    
    /* 
    Update internal representation of immediate environment
    */
    private void getPhysicalEnvironment(Space s)
    {
        env = s.getAdjacentCells(x, y);
    }
    
    /*
    Counts the number of corpses in the local environment and remembers the location of one of them
    */
    private void senseCorpses(Space s)
    {
        cdCurr = 0;
        
        for (int dx = 0; dx < 3; dx++){
            for(int dy = 0; dy < 3; dy++){
                if ( ( env[dx][dy] ) && (s.checkCorpse( (x+dx-1) , (y+dy-1)) ) ) {
                    cdCurr++;
                    cLoc[0] = x+dx-1;
                    cLoc[1] = y+dy-1;
                }
            }
        }
    }
    
    /* Update internal memory of corpse density */
    private void updateCorpseDensity()
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
    Pick up a corpse if there is one in the immediate environment
    */
    private void pickUpCorpse(Space s)
    {
        if ( (!hasCorpse) && (cdCurr > 0) && (cdCurr > cdHist) ){
            
            corpse = s.removeCorpse(cLoc[0],cLoc[1]);
            
            hasCorpse = true;
            color = carryColor;
            
            System.out.println("Ant " + Integer.toString(id)
                    + " picked up Corpse " + Integer.toString(corpse.id)
                    + " at x=" + Integer.toString(x) + ",y="+ Integer.toString(y)
                    + " cdHist=" + Integer.toString(cdHist));
        }
    }
    
    /*
    Deposit a corpse if there is sufficient corpse density in the local environment
    */
    private void depositCorpse(Space s)
    {
        if ( (hasCorpse) && (cdCurr > cdHist) ){
            s.addCorpse(x, y, corpse.id);
            hasCorpse = false;
            color = dropColor;
            
            System.out.println("Ant " + Integer.toString(id)
                    + " deposited Corpse " + Integer.toString(corpse.id)
                    + " at x=" + Integer.toString(x) + ",y="+ Integer.toString(y)
                    + " cdCurr=" + Integer.toString(cdCurr)
                    + " cdHist=" + Integer.toString(cdHist));
            
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
            tryLoc = env[rx][ry];
            tries++;
        }
        
        // If an empty cell was found, change the location to the empty cell
        if (tryLoc) {
            x = x + (rx-1);
            y = y + (ry-1);
        }
        
    }
 
    
}
