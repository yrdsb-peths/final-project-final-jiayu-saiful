import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Grass here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Grass extends Base
{
    private GreenfootImage baseImage;
    private static final int targetWidth = 75;
    
    public Grass() {
        baseImage = new GreenfootImage("images/grassTiles/00.png");

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
