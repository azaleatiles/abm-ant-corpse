/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.robkinsey.abm;

import javax.swing.JLabel;
import javax.swing.JApplet;
import java.awt.BorderLayout;


/**
 *
 * @author robert
 */
public class ABMApplet extends JApplet implements Runnable {
    
    ABMCanvas canvas;
    Space space;
    JLabel lbl;
    Thread t= null;
    boolean threadSuspended;
    int envsize = 100;
    int nCorpses = 100;
    int nAnts = 100;
    int tMax = 100000;
    int waitTime = 10;
    
    /**
     * Initialization method that will be called after the applet is loaded into
     * the browser.
     */
    @Override
    public void init() {
        
        space = new Space(envsize);
        space.initializeCorpses(nCorpses);
        space.initializeAnts(nAnts);
        
        canvas = new ABMCanvas();
        canvas.antMap = space.aMap;
        canvas.corpseMap = space.cMap;
        canvas.xmax = envsize;
        canvas.ymax = envsize;
        
        this.add(canvas, BorderLayout.CENTER);
        canvas.repaint();
        
    }
    
    @Override
    public void start()
    {
         
        if (t == null)
        {
            t = new Thread(this);
            threadSuspended = false;
            t.start();
        } else {
            if (threadSuspended) {
                threadSuspended = false;
                synchronized(this) {
                    notify();
                }
            }
        }
    }
    
    @Override
    public void run()
    {
        for (int n = 0; tMax > n; n++) {
            space.runAnts();
            canvas.repaint();
            System.out.println(Integer.toString(n));
            try {
                t.sleep(waitTime);
            } catch (InterruptedException e) {
                
            }
        }  
    }
    
    @Override
    public void stop()
    {
        
    }
    
}
