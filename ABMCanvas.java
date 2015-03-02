/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.robkinsey.abm;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;

/**
 *
 * @author robert
 */
public class ABMCanvas extends JComponent {
    
    Ant[] ants;
    Item[] items;
    //Item[][] itemMap;
    int xmax;
    int ymax;
    
    public ABMCanvas() {
        setBackground(Color.WHITE);
        setForeground(Color.BLACK);
        setOpaque(true);
    }
    
    //int[] xx = new int[6];
    //int[] yy = new int[6];
    
    @Override
    public void paintComponent(Graphics gg) {

        Graphics2D g = (Graphics2D) gg;

        drawEnv(g);
        
        for (Ant a : ants) {
            drawAnt(a, g);
        }
        
        for (Item i : items) {
            drawItem(i, g);
        }
        
    }
    
    private void drawEnv(Graphics2D g) {
        
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(getForeground());
    
    }
    
    private void drawAnt(Ant a, Graphics2D g) {
        
        if (a != null) {            
            g.setColor(a.color);
            g.drawLine(a.x-1, a.y-1, a.x+1, a.y+1);
            g.drawLine(a.x+1, a.y-1, a.x-1, a.y+1);
        }
        
    }
    
    private void drawItem(Item c, Graphics2D g) {
        
        if (c != null) {
            g.setColor(c.color);
            g.drawOval(c.x, c.y, 2, 2);
            g.fillOval(c.x, c.y, 2, 2);
        }
    }
    
}
