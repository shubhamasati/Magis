/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hank.recognizer;

import javazoom.jl.decoder.JavaLayerException;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hank
 */
public class Player {
    javazoom.jl.player.Player p;
    
    public boolean isPlaying()
    {
       return p.isComplete();
    }
    
    public void stop()
    {
      p.close();
    }
    public void play(InputStream is) throws JavaLayerException
    {
        BufferedInputStream bis = new BufferedInputStream(is);
        
        p = new javazoom.jl.player.Player(bis);
        
        new Thread()
        {
            @Override 
            public void run() 
            {
                
                try {
                    p.play();
                } catch (JavaLayerException ex) {
                    Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }.start();
       
        
    }        
            
}
