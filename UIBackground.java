import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class UIBackground here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class UIBackground extends UI
{
    private GreenfootImage baseImage;
    
    Level0 level0;
    
    public UIBackground(int fixedHeight) {
        baseImage = new GreenfootImage("images/UIbg.jpg");
        
        int targetWidth = level0.screenWidth;
        int targetHeight = fixedHeight; // Fixed
        baseImage.scale(targetWidth, targetHeight);

        setImage(new GreenfootImage(baseImage));
    }
    public void act()
    {
        // Add your action code here.
    }
}
