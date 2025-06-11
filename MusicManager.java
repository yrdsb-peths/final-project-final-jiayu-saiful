import greenfoot.*;

public class MusicManager {
    private static GreenfootSound bgMusic = new GreenfootSound("sounds/backgroundMusic.mp3");
    private static boolean isPlaying = false;
    private static int masterVolume = 100;
    private static int backgroundVolume = 50;
    private static int sfxVolume = 60;

    public static void playMusic() {
        if (!isPlaying) {
            int effectiveVolume = getEffectiveVolume(backgroundVolume);
            bgMusic.setVolume(effectiveVolume);
            bgMusic.playLoop();
            isPlaying = true;
        }
    }

    public static void stopMusic() {
        bgMusic.stop();
        isPlaying = false;
    }

    public static void pauseMusic() {
        bgMusic.pause();
        isPlaying = false;
    }

    public static void fadeOutAndStop() {
        new Thread(() -> {
            int vol = getEffectiveVolume(backgroundVolume);
            while (vol > 0) {
                vol -= 5;
                if (vol < 0) vol = 0;
                bgMusic.setVolume(vol);
                Greenfoot.delay(3);
            }
            bgMusic.stop();
            bgMusic.setVolume(getEffectiveVolume(backgroundVolume));
            isPlaying = false;
        }).start();
    }

    public static void setBackgroundVolume(int volume) {
        backgroundVolume = clamp(volume, 0, 100);
        if (isPlaying) {
            bgMusic.setVolume(getEffectiveVolume(backgroundVolume));
        }
    }

    public static void setSfxVolume(int volume) {
        sfxVolume = clamp(volume, 0, 100);
    }

    public static void setMasterVolume(int volume) {
        masterVolume = clamp(volume, 0, 100);
        if (isPlaying) {
            bgMusic.setVolume(getEffectiveVolume(backgroundVolume));
        }
    }

    public static boolean isPlaying() {
        return isPlaying;
    }

    private static int getEffectiveVolume(int volume) {
        return (volume * masterVolume) / 100;
    }

    private static int clamp(int value, int min, int max) {
        return Math.min(Math.max(value, min), max);
    }
}