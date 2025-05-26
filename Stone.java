import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Stone here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Stone extends Base
{
    private GreenfootImage baseImage;
    private static final int targetWidth = 30;
    
    public Stone() {
        baseImage = new GreenfootImage("images/stoneTiles.png");

        int targetHeight = (int)(baseImage.getHeight() * ((double) targetWidth / baseImage.getWidth()));
        baseImage.scale(targetWidth, targetHeight);

        setImage(new GreenfootImage(baseImage));
    }
    
    public void act()
    {
        // Add your action code here.
    }
    
    public int getTargetWidth() {
        return targetWidth;
    }
}
