package com.hank.utils;

import com.sun.media.codec.audio.mp3.JS_MP3FileReader;

import javax.sound.sampled.*;
import java.io.*;
import java.net.URL;

public class AudioUtils {

    public final static AudioFormat FORMAT_PCM_16KHZ = new AudioFormat(
            AudioFormat.Encoding.PCM_SIGNED, 16000,
            // Samplerate
            16, // quantization
            1, // mono
            2, 256000, false // byteorder: little endian
    );


    public static void mp3ToWav(File mp3file) throws UnsupportedAudioFileException, IOException {
        // open stream
        JS_MP3FileReader fileReader = new JS_MP3FileReader();
        AudioInputStream mp3Stream = fileReader.getAudioInputStream(mp3file);
        // write stream into a file with file format wav
        AudioInputStream converted = convertFromAudioInputStream(mp3Stream);
        AudioSystem.write(converted, AudioFileFormat.Type.WAVE, new File("out.wav"));
    }

    public static void mp3ToWav(InputStream is) throws UnsupportedAudioFileException, IOException {
        JS_MP3FileReader fileReader = new JS_MP3FileReader();
        AudioInputStream mp3Stream = fileReader.getAudioInputStream(is);
        // write stream into a file with file format wav
        AudioInputStream converted = convertFromAudioInputStream(mp3Stream);
        AudioSystem.write(converted, AudioFileFormat.Type.WAVE, new File("out.wav"));
//        byte[] bytes = getBytesFromInputStream(is);
//        writeAudioToWavFile(bytes, FORMAT_PCM_16KHZ, "outnew.wav");

    }

    public static void mp3ToWav(URL url) throws UnsupportedAudioFileException, IOException {
        JS_MP3FileReader fileReader = new JS_MP3FileReader();
        AudioInputStream mp3Stream = fileReader.getAudioInputStream(url);
        // write stream into a file with file format wav
        AudioInputStream converted = convertFromAudioInputStream(mp3Stream);
        AudioSystem.write(converted, AudioFileFormat.Type.WAVE, new File("out.wav"));
    }

    public static AudioInputStream convertFromAudioInputStream(AudioInputStream ais) {
        AudioFormat audioFormat = ais.getFormat();

        System.out.println("Channels: "+audioFormat.getChannels());
        System.out.println("Encoding: "+audioFormat.getEncoding());
        System.out.println("Frame Rate: "+audioFormat.getFrameRate());
        System.out.println("Frame Size: "+audioFormat.getFrameSize());
        System.out.println("Sample Rate: "+audioFormat.getSampleRate());
        System.out.println("Sample size (bits): "+audioFormat.getSampleSizeInBits());
        System.out.println("Big endian: "+audioFormat.isBigEndian());
        System.out.println("Audio Format String: "+audioFormat.toString());
        // create audio format object for the desired stream/audio format
        // this is *not* the same as the file format (wav)
        AudioFormat convertFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                audioFormat.getSampleRate(), 16,
                audioFormat.getChannels(),
                audioFormat.getChannels() * 2,
                audioFormat.getSampleRate(),
                false);
        // create stream that delivers the desired format
        AudioInputStream converted = AudioSystem.getAudioInputStream(convertFormat, ais);
        AudioInputStream finalCon = AudioSystem.getAudioInputStream(FORMAT_PCM_16KHZ, converted);
        return finalCon;
    }

    public static void writeAudioToWavFile(byte[] data, AudioFormat format,
                                           String fn) throws IOException {
        AudioInputStream ais = new AudioInputStream(new ByteArrayInputStream(
                data), format, data.length);
        AudioSystem.write(ais, AudioFileFormat.Type.WAVE, new File(fn));
    }

    public static byte[] getBytesFromInputStream(InputStream is)
            throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while (true) {
            byte[] buffer = new byte[100];
            int noOfBytes = is.read(buffer);
            if (noOfBytes == -1) {
                break;
            } else {
                bos.write(buffer, 0, noOfBytes);
            }
        }
        bos.flush();
        bos.close();
        return bos.toByteArray();
    }
}
