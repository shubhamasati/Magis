package com.hank.process.jobs;

import java.io.IOException;

public class VLCPlayer {
    private static final String path = "/Applications/VLC.app/Contents/MacOS/VLC";
    private static final Runtime r = Runtime.getRuntime();

    public static void play(String url) {
        String[] options = {"open", url};
        try {
            r.exec(options);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
