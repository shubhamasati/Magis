package com.hank.main;

import com.darkprograms.speech.synthesiser.Synthesiser;
import com.hank.google_search.GoogleSearch;
import com.hank.information_extraction.Extractor;
import com.hank.youtube.Search;

import java.awt.Desktop;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URI;

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
public class Decide {
       
    String username = System.getProperty("user.name");
    
    //HomeAutomation homeAuto = new HomeAutomation();
    Player p = new Player();
    
    public void decide(String term) throws Exception
    {
        term=term.trim();
        if(term.equals("hello") || term.equalsIgnoreCase("hello jarvis"))
            Synthesizer.saytext("Hello" + username + "How may I help you?");
        
        else if(term.contains("search") && term.contains("google"))
            GoogleSearch.search(term.split("for")[1].trim());
        
        else if(term.equalsIgnoreCase("open facebook"))
            Desktop.getDesktop().browse(new URI("https://www.facebook.com"));
        
        else if(term.equalsIgnoreCase("open linkedin"))
            Desktop.getDesktop().browse(new URI("https://www.linkedin.com"));
        
        else if(term.equalsIgnoreCase("open gmail"))
            Desktop.getDesktop().browse(new URI("https://www.gmail.com"));
        
        else if(term.contains("about"))
        {
            String subject=term;
            subject=subject.trim();
            System.err.println(GoogleSearch.getSearchTerm(subject));
            
            
            String info=Extractor.getInfo(GoogleSearch.getSearchTerm(subject));
            info = info.replaceAll("[^\\w\\s.,:]","");
            PrintWriter writer = new PrintWriter(subject+".txt", "UTF-8");
            writer.println(info);
            writer.close();
            
            System.err.println(info);
            Synthesiser syn = new Synthesiser("en");
            InputStream data = syn.getMP3Data(info);
            p.play(data);
            
        }
        else if(term.contains("open") || term.contains("Open"))
        {
           String s = term.split("open")[1];
           s = s.toLowerCase();
           s = s.trim();
            switch(s)
            {
                case "notepad" :
                     Synthesizer.saytext("opening notepad");
                     Thread.sleep(2000);
                     AppsManager.open("notepad");
                     break;
                case "wordpad" :
                     Synthesizer.saytext("opening wordpad");
                     Thread.sleep(2000);
                     AppsManager.open("wordpad");
                     break;
                case "office word" :
                     Synthesizer.saytext("opening m. s. word");
                     Thread.sleep(2000);
                     AppsManager.open("office word");
                     break;
                case "excel":
                     Synthesizer.saytext("opening m. s. excel");
                     Thread.sleep(2000);
                     AppsManager.open("excel");
                     break;
                case "powerpoint" :
                     Synthesizer.saytext("opening m. s. powerpoint");
                     Thread.sleep(2000);
                     AppsManager.open("powerpoint");
                     break;
                case "chrome" :
                     Synthesizer.saytext("opening google chrome");
                     Thread.sleep(2000);
                     System.err.println("Inside chrome");
                     AppsManager.open("chrome");
                     break;
                case "calculator":
                     Synthesizer.saytext("opening calculator");
                     Thread.sleep(2000);
                     AppsManager.open("calc");
                     break;
                case "vlc":
                     Synthesizer.saytext("opening vlc player");
                     Thread.sleep(2000);
                     AppsManager.open("vlc");
                     break;
                case "media player" :
                     Synthesizer.saytext("opening window media player");
                     Thread.sleep(2000);
                     AppsManager.open("wmplayer");
                     break;
                case "task manager" :
                     Synthesizer.saytext("opening task manager");
                     Thread.sleep(2000);
                     AppsManager.open("task manager");
                     break;
                case "control panel" :
                     Synthesizer.saytext("opening Control panel");
                     Thread.sleep(2000);
                     AppsManager.open("control panel");
                     break;
                case "logout" :
                     AppsManager.open("logout");
                     break;
                case "desktop" :
                     AppsManager.open("desktop");
                     break;
            }
        }
        else if(term.contains("close") || term.contains("Close"))
        {
            System.err.println("Inside open");
           String s = term.split("close")[1];
           s = s.toLowerCase();
           s = s.trim();
            switch(s)
            {
                case "notepad" :
                     Synthesizer.saytext("closing notepad");
                     Thread.sleep(2000);
                     AppsManager.close("notepad");
                     break;
                case "wordpad" :
                     Synthesizer.saytext("closing wordpad");
                     Thread.sleep(2000); 
                     AppsManager.close("wordpad");
                     break;
                case "office word" :
                     Synthesizer.saytext("closing m. s. office");
                     Thread.sleep(2000);
                     AppsManager.close("office word");
                     break;
                case "excel":
                     Synthesizer.saytext("closing m. s. excel");
                     Thread.sleep(2000);
                     AppsManager.close("excel");
                     break;
                case "powerpoint" :
                     Synthesizer.saytext("closing m. s. powerpoint");
                     Thread.sleep(2000);
                     AppsManager.close("powerpoint");
                     break;
                case "chrome" :
                     Synthesizer.saytext("closing google chrome");
                     Thread.sleep(2000);
                     AppsManager.close("chrome");
                     break;
                case "calculator":
                     Synthesizer.saytext("closing calculator");
                     Thread.sleep(2000);
                     AppsManager.close("calc");
                     break;
                case "vlc":
                     Synthesizer.saytext("closing v l c player");
                     Thread.sleep(2000);
                     AppsManager.close("vlc");
                     break;
                case "media player" :
                     Synthesizer.saytext("closing window media player");
                     Thread.sleep(2000);
                     AppsManager.close("wmplayer");
                     break;
                case "task manager" :
                     Synthesizer.saytext("closing task manager");
                     Thread.sleep(2000);
                     AppsManager.close("task manager");
                     break;
                case "control panel" :
                     Synthesizer.saytext("closing control panel");
                     Thread.sleep(2000);
                     AppsManager.close("control panel");
                     break;
                
            }
        }
        //else if(term.equalsIgnoreCase("expand"))
          //  ;     
      //  else if(term.equalsIgnoreCase("shrink"))
       //     ;
       // else if(term.contains("turn on") || term.contains("Turn On"))
       //      homeAuto.decideTurnON(term.toLowerCase()); 
      //  else if(term.contains("turn off") || term.contains("Turn Off"))
       //      homeAuto.decideTurnOFF(term.toLowerCase()); 
        else if(term.contains("play") && term.endsWith("video"))
        {
           String ID =  Search.GetID(term.replace("play", ""));
           AppsManager.playVideo("https://www.youtube.com/watch?v="+ID);
        }
        
        
        
        
        
    }
    
}
