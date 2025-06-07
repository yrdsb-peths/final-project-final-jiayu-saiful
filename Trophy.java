import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Trophy here.
 * 
 * @author (your name) Jiayu Chen
 * @version (a version number or a date) 6/6/25
 */
public class Trophy extends Actor
{
    private GreenfootImage[] trophyAnimation = new GreenfootImage[8];
    private GreenfootSound trophySound = new GreenfootSound("win.mp3");
    private int animationFrame = 0;
    private int animationTimer = 0;
    private final int ANIMATION_SPEED = 6;
    private final int TARGET_WIDTH = 130;
    
    private final int interDist = 35;

    public Trophy() {
        for (int i = 0; i < 8; i++) {
            GreenfootImage img = new GreenfootImage("images/trophy/t00" + i + ".png");
            scaleImage(img, TARGET_WIDTH);
            trophyAnimation[i] = img;
        }
        setImage(trophyAnimation[0]);
    }

    public void act() {
        animateTrophy();
        trophySound.play();
    }

    private void animateTrophy() {
        animationTimer++;
        if (animationTimer >= ANIMATION_SPEED) {
            animationTimer = 0;
            animationFrame = (animationFrame + 1) % trophyAnimation.length;
            setImage(trophyAnimation[animationFrame]);
        }
    }

    private void scaleImage(GreenfootImage img, int targetWidth) {
        int targetHeight = (int)(img.getHeight() * ((double) targetWidth / img.getWidth()));
        img.scale(targetWidth, targetHeight);
    }
}
