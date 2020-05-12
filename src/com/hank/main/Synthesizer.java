package com.hank.main;

import com.darkprograms.speech.synthesiser.Synthesiser;
import java.io.IOException;
import java.io.InputStream;
import javazoom.jl.decoder.JavaLayerException;
import com.hank.player.Player;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hank
 */
public class Synthesizer {
    
     static Synthesiser syn = new Synthesiser("en");
    
    public static void saytext(String text) throws IOException, JavaLayerException{
        
        InputStream data = syn.getMP3Data(text);
        Player p = new Player();
        p.play(data);
    }
}
