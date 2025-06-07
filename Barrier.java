import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Barrier here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

public class Barrier extends Actor {
    public Barrier(int width, int height) {
        GreenfootImage img = new GreenfootImage(width, height);
        img.setColor(new Color(255, 0, 0, 100));
        img.fill();
        setImage(img);
    }
}
