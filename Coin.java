import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Coin here.
 * 
 * @author Saiful Shaik 
 * @version May, 22, 2025.
 */

public class Coin extends Actor
{
    private GreenfootImage[] coinAnimation = new GreenfootImage[4];
    private int animationFrame = 0;
    private int animationTimer = 0;
    private final int ANIMATION_SPEED = 6;
    private final int TARGET_WIDTH = 25;

    public Coin() {
        for (int i = 0; i < 4; i++) {
            GreenfootImage img = new GreenfootImage("images/coin_an/0" + i + ".png");
            scaleImage(img, TARGET_WIDTH);
            coinAnimation[i] = img;
        }
        setImage(coinAnimation[0]);
    }
    
    public void act()
    {
        animationTimer++;
        if (animationTimer >= ANIMATION_SPEED) {
            animationTimer = 0;
            animationFrame = (animationFrame + 1) % coinAnimation.length;
            setImage(coinAnimation[animationFrame]);
        }
    }
    
    private void scaleImage(GreenfootImage img, int targetWidth) {
        int targetHeight = (int)(img.getHeight() * ((double) targetWidth / img.getWidth()));
        img.scale(targetWidth, targetHeight);
    }
}
