import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Player here.
 * 
 * @author (your name) Jiayu
 * @version (a version number or a date) 05/21/25
 */
public class Player extends Actor
{
    private GreenfootImage baseImage;
    
    public Player() {
        baseImage = new GreenfootImage("images/knight.png");

        int targetWidth = 75;
        int targetHeight = (int)(baseImage.getHeight() * ((double) targetWidth / baseImage.getWidth()));
        baseImage.scale(targetWidth, targetHeight);

        setImage(new GreenfootImage(baseImage));
    }
    
    public void act()
    {
        // Add your action code here.
        
    }
}
