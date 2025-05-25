import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class House here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class House extends Base
{
    private GreenfootImage baseImage;
    
    public House(String directory, int houseSize) {
        baseImage = new GreenfootImage(directory);
        
        int targetWidth = houseSize;
        int targetHeight = (int)(baseImage.getHeight() * ((double) targetWidth / baseImage.getWidth()));
        baseImage.scale(targetWidth, targetHeight);

        setImage(new GreenfootImage(baseImage));
    }
    
    public void act()
    {
        // Add your action code here.
    }
}
