/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.robkinsey.abm;

import java.util.Random;

/**
 *
 * @author robert
 */
public class Utilities {
    
    public static int randInt(int imin, int imax) {
        Random rand = new Random();
        return rand.nextInt((imax - imin) + 1);
    }
    
}
