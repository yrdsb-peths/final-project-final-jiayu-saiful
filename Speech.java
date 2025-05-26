import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Speech here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Speech extends Base
{
    GreenfootImage baseImage;
    Label label;
    Level0 level0;
    World world;
    public Speech(String directory, int speechSize, String text, int textSize) {
        baseImage = new GreenfootImage("images/speech/" + directory);
        scaleImage(baseImage, speechSize);
        label = new Label(text, textSize);
        world.addObject(label, level0.npcX - 10, level0.npcY - 50);
        
        setImage(baseImage);
    }
    
    private void scaleImage(GreenfootImage img, int targetWidth) {
        int targetHeight = (int)(img.getHeight() * ((double) targetWidth / img.getWidth()));
        img.scale(targetWidth, targetHeight);
    }
}
