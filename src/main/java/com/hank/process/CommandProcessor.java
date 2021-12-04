package com.hank.process;

import com.hank.process.jobs.VLCPlayer;
import com.hank.config.UserGlobalConfig;
import com.hank.recognizer.Player;
import com.hank.speechengine.recognizer.GoogleResponse;
import com.hank.speechengine.recognizer.Recognizer;
import com.hank.speechengine.synthesiser.SynthesiserV2;
import com.hank.utils.AudioUtils;
import com.hank.youtube.YoutubeSearch;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

public class CommandProcessor {
    SynthesiserV2 synthesiserV2 = new SynthesiserV2("AIzaSyDxKR2z_hP5MZrXBc-pucQ06_ly0ksS71E");
    Player player = new Player();
    UserGlobalConfig userGlobalConfig = new UserGlobalConfig();
    Recognizer recognizer = new Recognizer("en", "AIzaSyDxKR2z_hP5MZrXBc-pucQ06_ly0ksS71E");

    public void process(String cmd) throws IOException, UnsupportedAudioFileException {
        String command = cmd;
        System.out.println(Recognizer.Languages.HINDI.name());
        if (Recognizer.Languages.HINDI.equals(userGlobalConfig.getUserSetLanguage())) {
            InputStream mp3Data = synthesiserV2.getMP3Data(cmd);
            InputStream buffred = new BufferedInputStream(mp3Data);
            AudioUtils.mp3ToWav(buffred);

            GoogleResponse response = recognizer.getRecognizedDataForWave(new File("out.wav"), 10);
            if (response != null && response.getResponse() != null && !response.getResponse().isEmpty())
                command = response.getResponse().toLowerCase(Locale.ROOT);
            System.out.println("New Command: " + command);
            String videoID = YoutubeSearch.getID(command.replace("play", ""));
            System.out.println("VideoID: " + videoID);
            VLCPlayer.play("https://www.youtube.com/watch?v=" + videoID);

//            if (newCommand.contains("play") && (newCommand.contains("song") || newCommand.contains("video"))) {
//            }

        }


    }
}
