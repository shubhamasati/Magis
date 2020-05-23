package com.hank.main;

import com.darkprograms.speech.microphone.Microphone;
import com.darkprograms.speech.recognizer.GSpeechDuplex;
import com.darkprograms.speech.recognizer.GSpeechResponseListener;
import com.darkprograms.speech.recognizer.GoogleResponse;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import net.sourceforge.javaflacencoder.FLACFileWriter;


public class Recognizer {
    
    //Google API class
    final Microphone mic = new Microphone(FLACFileWriter.FLAC); //FLAC format
     
    //google API
    GSpeechDuplex duplex = new GSpeechDuplex("AIzaSyCLNgQvvOtqwnPzZvvQLTilzNt1a_etaw4");
    
    //google API
    GSpeechResponseListener rl;
    
    //
    Decide decide; //Decide class 

  
    
    public Recognizer(){
        
         decide = new Decide();

         duplex.setLanguage("en");
         
         
        rl =   new GSpeechResponseListener()
       {
          String old_text = "";
          String temp = "";
      
      public void onResponse(GoogleResponse gr)
      {
          String output = "";
          output = gr.getResponse(); //return recognize words
          if (gr.getResponse() == null)
          {
            this.old_text = temp;
            if(this.old_text.contains("(")) {
            this.old_text = this.old_text.substring(0, this.old_text.indexOf('('));
          }
          String search =this.old_text;
          System.out.println("This is the string:"+search);
          try
          {               
              decide.decide(search.toLowerCase());
          } 
          catch (Exception ex) {}
          //System.out.println("Paragraph Line Added");
          this.old_text = (temp + "\n");
          this.old_text = this.old_text.replace(")", "").replace("( ", "");
          return;
        }
        if (output.contains("(")) 
          output = output.substring(0, output.indexOf('('));
        
        if (!gr.getOtherPossibleResponses().isEmpty()) 
          output = output + " (" + (String)gr.getOtherPossibleResponses().get(0) + ")";
        
        System.out.println(output);
        temp =  "";
        temp = temp +output;
      }
    };
           
    }
    
    public void recognise() throws IOException, LineUnavailableException{
       if(first)
       {
           System.err.println("yes first");
           duplex.recognize(mic.getTargetDataLine(), mic.getAudioFormat());
           duplex.addResponseListener(rl);
           first = false;
       }
       else
           duplex.addResponseListener(rl);
       
    }
    
    public void close()
    {
        duplex.removeResponseListener(rl);    
    }   
}
