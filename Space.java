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
    
    Corpse[] corpses;
    Ant[] ants;
    Corpse[][] cMap;
    Ant[][] aMap; // Map of agents in the space
    int xmax;
    int ymax;
    
    public Space( int x )
    {
        xmax = x-1;
        ymax = x-1;  
        cMap = new Corpse[x][x];
        aMap = new Ant[x][x];
    }
    
    public void initializeCorpses (int n)
    {
        //Refactor method to prevent use cMap instead of corpses. Or maybe it is the same thing?
        corpses = new Corpse[n];
        
        for (int i=0; i<n; i++)
        {
            corpses[i] = new Corpse();
            corpses[i].x = randInt(0, xmax);
            corpses[i].y = randInt(0, ymax);
            corpses[i].id = i;
            corpses[i].color = Color.GRAY;
            
            cMap[corpses[i].x][corpses[i].y] = corpses[i];
            
            //System.out.println("Corpse id: " + Integer.toString(corpses[i].id) + " x=" + Integer.toString(corpses[i].x) + " y=" + Integer.toString(corpses[i].y));
            //System.out.println("cMap   id: " + Integer.toString(cMap[corpses[i].x][corpses[i].y].id));
        } 
    }
    
    public void initializeAnts (int n) {
        
         // Create the ants and randomly add across environment
        ants = new Ant[n];
        
        for (int i = 0; i<n; i++) {
            ants[i] = new Ant(i);
            ants[i].x = randInt(0,xmax);
            ants[i].y = randInt(0,ymax);
            
            aMap[ants[i].x][ants[i].y] = ants[i];
            
        }
        
    }
    
    public void runAnts() {
        // Each ant does its thing
        for (Ant a : ants){
            a.behave(this);   
        }
    }
    
    public void addCorpse( int x, int y, int id )
    {
        corpses[id].x = x;
        corpses[id].y = y;
        corpses[id].color = Color.RED;
        cMap[x][y] = corpses[id];
    }

    public Corpse removeCorpse( int x, int y )
    {

        Corpse c = cMap[x][y];
        c.x = -1;
        c.y = -1;
        System.out.println("SPACE: Removed corpse " + Integer.toString(c.id)
                + " at x="+ Integer.toString(x) + ",y=" + Integer.toString(y));
        cMap[x][y] = null;
        return c;
    }
        
    public boolean checkCorpse( int x, int y )
    {
        boolean lret;
        if (cMap[x][y] != null) {
            lret = true;
        } else {
            lret = false;
        }
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
    
    public boolean[][] getAdjacentCorpses( int x, int y )
    {
        boolean[][] c = getAdjacentCells(x, y);
        
        for (int dx = -1; dx < 2; dx++){
            for(int dy = -1; dy < 2; dy++){
                if ( c[dx+1][dy+1] ) {
                    c[dx+1][dy+1] = checkCorpse( x+dx, y+dy );
                }
            }
        }
        
        return c;
    }
    
}
