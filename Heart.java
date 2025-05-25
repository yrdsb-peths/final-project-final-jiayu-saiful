import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Heart here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

public class Heart extends Actor {
    private GreenfootImage[] frames;
    private int currentFrame = 0;
    private int frameDelay = 4;
    private int delayCount = 0;

    public Heart() {
        frames = new GreenfootImage[12];
        for (int i = 0; i < 12; i++) {
            frames[i] = new GreenfootImage("images/healthBar/heartRotate/" + String.format("%02d.png", i));
            frames[i].scale(24, 24);
        }
        setImage(frames[0]);
    }

    public void act() {
        delayCount++;
        if (delayCount >= frameDelay) {
            currentFrame = (currentFrame + 1) % frames.length;
            setImage(frames[currentFrame]);
            delayCount = 0;
        }
    }
}
