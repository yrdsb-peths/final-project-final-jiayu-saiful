import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class NPC here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class NPC extends Base
{
    private GreenfootImage[] npcAnimation = new GreenfootImage[4];
    private int animationFrame = 0;
    private int animationTimer = 0;
    private final int ANIMATION_SPEED = 6;
    private final int TARGET_WIDTH = 80;
    
    public NPC(String directory, int npcSize) {
        for (int i = 0; i < 4; i++) {
            GreenfootImage img = new GreenfootImage("images/npc/0" + i + ".png");
            scaleImage(img, TARGET_WIDTH);
            npcAnimation[i] = img;
        }
        setImage(npcAnimation[0]);
    }
    
    public void act()
    {
        animationTimer++;
        if (animationTimer >= ANIMATION_SPEED) {
            animationTimer = 0;
            animationFrame = (animationFrame + 1) % npcAnimation.length;
            setImage(npcAnimation[animationFrame]);
        }
    }
    
    private void scaleImage(GreenfootImage img, int targetWidth) {
        int targetHeight = (int)(img.getHeight() * ((double) targetWidth / img.getWidth()));
        img.scale(targetWidth, targetHeight);
    }
}
