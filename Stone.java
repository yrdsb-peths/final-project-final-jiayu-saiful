import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Stone here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Stone extends Base {
    private GreenfootImage baseImage;
    private static int targetWidth;

    public Stone(int tWidth) {
        targetWidth = tWidth;
        baseImage = new GreenfootImage("images/stoneTiles2.png");

        int targetHeight = (int)(baseImage.getHeight() * ((double) targetWidth / baseImage.getWidth()));
        baseImage.scale(targetWidth, targetHeight);

        setImage(new GreenfootImage(baseImage));
    }

    public int getTargetWidth() {
        return targetWidth;
    }
}
