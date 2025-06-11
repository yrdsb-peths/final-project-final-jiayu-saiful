import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Speech here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Speech1 extends Base {
    private GreenfootImage[] speechImages;
    private int currentIndex = 0;

    public Speech1() {
        loadSpeechImages();
        setImage(speechImages[currentIndex]);
    }

    private void loadSpeechImages() {
        int numImages = 5;
        speechImages = new GreenfootImage[numImages];
        for (int i = 0; i < numImages; i++) {
            GreenfootImage img = new GreenfootImage("images/speech/0" + i + ".png");
            int targetWidth = 150;
            int targetHeight = (int)(img.getHeight() * (targetWidth / (double)img.getWidth()));
            img.scale(targetWidth, targetHeight);
            speechImages[i] = img;
        }
    }

    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            currentIndex++;
            if (currentIndex < speechImages.length) {
                setImage(speechImages[currentIndex]);
            } else {
                Greenfoot.setWorld(new Level1(Level0.screenWidth, Level0.screenHeight, UI.getGoldCount()));
            }
        }
    }
}