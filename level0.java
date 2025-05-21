import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class level0 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import greenfoot.*;

public class level0 extends World {
    public level0(int width, int height) {
        super(width, height, 1);
        
        Player hero = new Player();
        addObject(hero, 300, 300);
    }
}
