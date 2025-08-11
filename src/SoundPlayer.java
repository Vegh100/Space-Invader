import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundPlayer {
    private final String soundFilePath;
    private boolean isGameRunning = true;
    private Clip clip;

    public SoundPlayer(String soundFilePath){
        this.soundFilePath = soundFilePath;
    }
    public void playSound(){
        try {
            this.isGameRunning = true;
            File soundFile = new File(soundFilePath);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);

           clip = AudioSystem.getClip();
            try {
                clip.open(audioIn);
            } catch (LineUnavailableException | IOException e) {
                throw new RuntimeException(e);
            }

            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    public void playBackgroundMusic() {
        while (isGameRunning) {
            playSound();
            try {
                Thread.sleep(48000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopBackgroundMusic() {
        isGameRunning = false;
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}
