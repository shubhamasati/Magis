package com.hank.application;

import com.hank.recognizer.Recognizer;
import com.hank.wakeWordProcessor.WakeWordEngine;
import com.hank.wakeWordProcessor.WakeWordListener;
import com.hank.wakeWordProcessor.WakeWordResponse;

import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws LineUnavailableException, IOException, InterruptedException {

        WakeWordEngine wakeWordEngine = new WakeWordEngine();
        WakeWordListener wwl = new WakeWordListener() {
            @Override
            public void onResponse(WakeWordResponse wakeWordResponse) {
                String wakeWord = wakeWordResponse.getWakeWord();
                if (wakeWord.equalsIgnoreCase("JARVIS")) {
                    Recognizer r = new Recognizer();
                    try {
                        r.recognise();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        wakeWordEngine.addListener(wwl);
        wakeWordEngine.start();
    }
}