package com.hank.recognizer;

import com.hank.process.CommandProcessor;
import com.hank.speechengine.microphone.Microphone;
import com.hank.speechengine.recognizer.GSpeechDuplex;
import com.hank.speechengine.recognizer.GSpeechResponseListener;
import com.hank.speechengine.recognizer.GoogleResponse;
import com.hank.speechengine.synthesiser.SynthesiserV2;
import net.sourceforge.javaflacencoder.FLACFileWriter;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Locale;

public class Recognizer {

    static int count = 1;
    final Microphone mic = new Microphone(FLACFileWriter.FLAC);
    GSpeechDuplex duplex = new GSpeechDuplex("AIzaSyDxKR2z_hP5MZrXBc-pucQ06_ly0ksS71E");
    GSpeechResponseListener rl;
    SynthesiserV2 synthesiserV2 = new SynthesiserV2("AIzaSyDxKR2z_hP5MZrXBc-pucQ06_ly0ksS71E");
    Player player = new Player();
    TargetDataLine targetDataLine = mic.getTargetDataLine();
    CommandProcessor cmdProcessor = new CommandProcessor();
    private int DEFAULT_BUFFER_SIZE = 8192;

    public Recognizer() {
        duplex.setLanguage("hi");
        synthesiserV2.setLanguage("auto");
        rl = new GSpeechResponseListener() {
            final String old_text = "";
            final String temp = "";

            public void onResponse(GoogleResponse gr) {
                if (gr.isFinalResponse()) {
                    String output = gr.getResponse(); //return recognize words
                    System.out.println("You Said " + output);
                    try {
                        cmdProcessor.process(output.toLowerCase(Locale.ROOT));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (UnsupportedAudioFileException e) {
                        e.printStackTrace();
                    }
                    targetDataLine.close();
                }
//                if (false)
//                {
//                    this.old_text = temp;
//                    if(this.old_text.contains("(")) {
//                        this.old_text = this.old_text.substring(0, this.old_text.indexOf('('));
//                    }
//                    String search =this.old_text;
////                    try
////                    {
////                        decide.decide(search.toLowerCase());
////                    }
////                    catch (Exception ex) {}
//                    //System.out.println("Paragraph Line Added");
//                    this.old_text = (temp + "\n");
//                    this.old_text = this.old_text.replace(")", "").replace("( ", "");
//                    return;
//                }
//                if (output.contains("("))
//                    output = output.substring(0, output.indexOf('('));
//
//                if (!gr.getOtherPossibleResponses().isEmpty())
//                    output = output + " (" + (String)gr.getOtherPossibleResponses().get(0) + ")";
//
//                System.out.println(output);
//                temp =  "";
//                temp = temp +output;
            }
        };
    }

    public void recognise() throws IOException, LineUnavailableException, InterruptedException {
        duplex.addResponseListener(rl);
        duplex.recognize(targetDataLine, mic.getAudioFormat());
    }
}