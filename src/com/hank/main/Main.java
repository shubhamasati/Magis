package com.hank.main;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.sound.sampled.LineUnavailableException;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hank
 */


public class Main 
{   
	//Entry Point
    public static void main(String[] args) throws MalformedURLException 
    {
        Recognizer reco = new Recognizer(); //Object of recognizer class voice recognision
        try {
			reco.recognise(); //method
		
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}

    }
}
