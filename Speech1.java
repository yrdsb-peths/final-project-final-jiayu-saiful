import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Speech here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Speech1 extends Base {
    private GreenfootImage speechImage = new GreenfootImage("images/speech/05.png");

    public Speech1() {
        loadSpeechImages();
        setImage(speechImage);
    }

    private void loadSpeechImages() {
        int targetWidth = 150;
        int targetHeight = (int)(speechImage.getHeight() * (targetWidth / (double)speechImage.getWidth()));
        speechImage.scale(targetWidth, targetHeight);
    }

    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            Greenfoot.setWorld(new Level2());
        }
    }
}