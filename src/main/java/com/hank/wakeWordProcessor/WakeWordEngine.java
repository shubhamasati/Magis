package com.hank.wakeWordProcessor;
/*
    Copyright 2018-2020 Picovoice Inc.

    You may not use this file except in compliance with the license. A copy of the license is
    located in the "LICENSE" file accompanying this source.

    Unless required by applicable law or agreed to in writing, software distributed under the
    License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
    express or implied. See the License for the specific language governing permissions and
    limitations under the License.
*/


import ai.picovoice.porcupine.Porcupine;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WakeWordEngine extends Thread {
    List<WakeWordListener> wakeWordListenerList;
    WakeWordResponse wakeWordResponse;

    public WakeWordEngine() {
        wakeWordListenerList = new ArrayList<>();
        wakeWordResponse = new WakeWordResponse();
    }

    private static void showAudioDevices() {

        // get available audio devices
        Mixer.Info[] allMixerInfo = AudioSystem.getMixerInfo();
        Line.Info captureLine = new Line.Info(TargetDataLine.class);

        for (int i = 0; i < allMixerInfo.length; i++) {

            // check if supports capture in the format we need
            Mixer mixer = AudioSystem.getMixer(allMixerInfo[i]);
            if (mixer.isLineSupported(captureLine)) {
                System.out.printf("Device %d: %s\n", i, allMixerInfo[i].getName());
            }
        }
    }

    private static TargetDataLine getDefaultCaptureDevice(DataLine.Info dataLineInfo) throws LineUnavailableException {

        if (!AudioSystem.isLineSupported(dataLineInfo)) {
            throw new LineUnavailableException("Default capture device does not support the audio " +
                    "format required by Picovoice (16kHz, 16-bit, linearly-encoded, single-channel PCM).");
        }

        return (TargetDataLine) AudioSystem.getLine(dataLineInfo);
    }

    private static TargetDataLine getAudioDevice(int deviceIndex, DataLine.Info dataLineInfo) throws LineUnavailableException {

        if (deviceIndex >= 0) {
            try {
                Mixer.Info mixerInfo = AudioSystem.getMixerInfo()[deviceIndex];
                Mixer mixer = AudioSystem.getMixer(mixerInfo);

                if (mixer.isLineSupported(dataLineInfo)) {
                    return (TargetDataLine) mixer.getLine(dataLineInfo);
                } else {
                    System.err.printf("Audio capture device at index %s does not support the audio format required by " +
                            "Picovoice. Using default capture device.", deviceIndex);
                }
            } catch (Exception e) {
                System.err.printf("No capture device found at index %s. Using default capture device.", deviceIndex);
            }
        }
        // use default capture device if we couldn't get the one requested
        return getDefaultCaptureDevice(dataLineInfo);
    }

    public void addListener(WakeWordListener wakeWordListener) {
        wakeWordListenerList.add(wakeWordListener);
    }

    public List<WakeWordListener> getWakeWordListenerList() {
        return wakeWordListenerList;
    }

    public void removeListener(WakeWordListener wakeWordListener) {
        wakeWordListenerList.remove(wakeWordListener);
    }

    public void runDemo(String libPath, String modelPath,
                        String[] keywordPaths, float[] sensitivities,
                        int audioDeviceIndex, String outputPath) {
        // create keywords from keyword_paths
        String[] keywords = new String[keywordPaths.length];
        for (int i = 0; i < keywordPaths.length; i++) {
            File keywordFile = new File(keywordPaths[i]);
            if (!keywordFile.exists())
                throw new IllegalArgumentException(String.format("Keyword file at '%s' " +
                        "does not exist", keywordPaths[i]));
            keywords[i] = keywordFile.getName().split("_")[0];
        }

        // for file output
        File outputFile = null;
        ByteArrayOutputStream outputStream = null;
        long totalBytesCaptured = 0;
        AudioFormat format = new AudioFormat(16000f, 16, 1, true, false);

        // get audio capture device
        DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, format);
        TargetDataLine micDataLine;
        try {
            micDataLine = getAudioDevice(audioDeviceIndex, dataLineInfo);
            micDataLine.open(format);
        } catch (LineUnavailableException e) {
            System.err.println("Failed to get a valid capture device. Use --show_audio_devices to " +
                    "show available capture devices and their indices");
            System.exit(1);
            return;
        }

        Porcupine porcupine = null;
        try {

            porcupine = new Porcupine.Builder()
                    .setLibraryPath(libPath)
                    .setModelPath(modelPath)
                    .setKeywordPaths(keywordPaths)
                    .setSensitivities(sensitivities)
                    .build();

            if (outputPath != null) {
                outputFile = new File(outputPath);
                outputStream = new ByteArrayOutputStream();
            }

            micDataLine.start();
            System.out.print("Listening for {");
            for (int i = 0; i < keywords.length; i++) {
                System.out.printf(" %s(%.02f)", keywords[i], sensitivities[i]);
            }
            System.out.print(" }\n");
            System.out.println("Press enter to stop recording...");

            // buffers for processing audio
            int frameLength = porcupine.getFrameLength();
            ByteBuffer captureBuffer = ByteBuffer.allocate(frameLength * 2);
            captureBuffer.order(ByteOrder.LITTLE_ENDIAN);
            short[] porcupineBuffer = new short[frameLength];

            int numBytesRead;
            while (System.in.available() == 0) {

                // read a buffer of audio
                numBytesRead = micDataLine.read(captureBuffer.array(), 0, captureBuffer.capacity());
                totalBytesCaptured += numBytesRead;

                // write to output if we're recording
                if (outputStream != null) {
                    outputStream.write(captureBuffer.array(), 0, numBytesRead);
                }

                // don't pass to porcupine if we don't have a full buffer
                if (numBytesRead != frameLength * 2) {
                    continue;
                }

                // copy into 16-bit buffer
                captureBuffer.asShortBuffer().get(porcupineBuffer);

                // process with porcupine
                int result = porcupine.process(porcupineBuffer);
                if (result >= 0) {
                    String detectedWord = keywords[result];
                    System.out.printf("[%s] Detected '%s'\n",
                            LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")), detectedWord);
                    wakeWordResponse.setWakeWord(detectedWord);
                    fireEvent(wakeWordResponse);
                }
            }
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            if (outputStream != null && outputFile != null) {

                // need to transfer to input stream to write
                ByteArrayInputStream writeArray = new ByteArrayInputStream(outputStream.toByteArray());
                AudioInputStream writeStream = new AudioInputStream(writeArray, format, totalBytesCaptured / format.getFrameSize());

                try {
                    AudioSystem.write(writeStream, AudioFileFormat.Type.WAVE, outputFile);
                } catch (IOException e) {
                    System.err.printf("Failed to write audio to '%s'.\n", outputFile.getPath());
                    e.printStackTrace();
                }
            }

            if (porcupine != null) {
                porcupine.delete();
            }
        }
    }

    private void fireEvent(WakeWordResponse wakeWordResponse) {
        for (WakeWordListener wwl : wakeWordListenerList) {
            wwl.onResponse(wakeWordResponse);
        }
    }

    public void run() {

        float[] sensitivities = null;
        String[] keywords = {"jarvis", "terminator"};
        String[] keywordPaths = null;

        if (keywordPaths == null || keywordPaths.length == 0) {
            if (keywords == null || keywords.length == 0) {
                throw new IllegalArgumentException("Either '--keywords' or '--keyword_paths' must be set.");
            }

            if (Porcupine.KEYWORDS.containsAll(Arrays.asList(keywords))) {
                keywordPaths = new String[keywords.length];
                for (int i = 0; i < keywords.length; i++) {
                    keywordPaths[i] = Porcupine.KEYWORD_PATHS.get(keywords[i]);
                }
            } else {
                throw new IllegalArgumentException("One or more keywords are not available by default. " +
                        "Available default keywords are:\n" + String.join(",", Porcupine.KEYWORDS));
            }
        }

        if (sensitivities == null) {
            sensitivities = new float[keywordPaths.length];
            Arrays.fill(sensitivities, 0.5f);
        }

        int audioDeviceIndex = -1;
        runDemo(Porcupine.LIBRARY_PATH, Porcupine.MODEL_PATH, keywordPaths, sensitivities, audioDeviceIndex, null);
    }
}