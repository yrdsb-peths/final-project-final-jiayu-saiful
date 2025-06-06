import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class BackButton here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BackButton extends Actor
{
    private GreenfootImage baseImage;
    
    public BackButton() {
        baseImage = new GreenfootImage("images/backButton.png");

        int targetWidth = 150;
        int targetHeight = (int)(baseImage.getHeight() * ((double) targetWidth / baseImage.getWidth()));
        baseImage.scale(targetWidth, targetHeight);

        setImage(new GreenfootImage(baseImage));
    }
    
    public void act()
    {
        if (Greenfoot.mouseClicked(this)) {
            // Go back to Titlescreen
            Titlescreen titlescreen = new Titlescreen();
            Greenfoot.setWorld(titlescreen);
        }
    }
}
