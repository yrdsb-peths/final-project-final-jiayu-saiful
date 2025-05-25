import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class UIBackground here.
 * 
 * @author Saiful Shaik
 * @version May, 25, 2025
 */

public class UIBackground extends UI {
    public UIBackground(World world, int screenWidth, int fixedHeight) {
        super(world);
        GreenfootImage baseImage = new GreenfootImage("images/UIbg.jpg");
        baseImage.scale(screenWidth, fixedHeight);
        setImage(baseImage);
    }
}
