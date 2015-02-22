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
    
    Ant[][] antMap;
    Corpse[][] corpseMap;
    int xmax;
    int ymax;
    
    public ABMCanvas() {
        setBackground(Color.WHITE);
        setForeground(Color.BLACK);
        setOpaque(true);
    }
    
    int[] xx = new int[4];
    int[] yy = new int[4];
    
    @Override
    public void paintComponent(Graphics gg) {

        Graphics2D g = (Graphics2D) gg;
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(getForeground());
        
        for (int x=0; x<xmax; x++) {
            for (int y=0; y<ymax; y++) {
                drawAnt(antMap[x][y], g);
                drawCorpse(corpseMap[x][y], g);
            }
        }
        
    }
    
    private void drawAnt(Ant a, Graphics2D g) {
        
        if (a != null) {
            xx[0] = a.x - 2;
            xx[1] = a.x - 1;
            xx[2] = a.x + 1;
            xx[3] = a.x + 1;

            yy[0] = a.y - 1;
            yy[1] = a.y + 1;
            yy[2] = a.y + 1;
            yy[3] = a.y - 1;           


            g.setColor(a.color);
            g.fillPolygon(xx, yy, 4);
            g.drawPolygon(xx, yy, 4);
        }
        
    }
    
    private void drawCorpse(Corpse c, Graphics2D g) {
        
        if (c != null) {
            xx[0] = c.x;
            yy[0] = c.y;
            
            xx[1] = c.x - 1;
            yy[1] = c.y - 1;
            
            xx[2] = c.x + 1;
            yy[2] = c.y - 1;
            
            g.setColor(c.color);
            g.fillPolygon(xx,yy,3);
            g.drawPolygon(xx, yy, 3);
        }
    }
    
}
